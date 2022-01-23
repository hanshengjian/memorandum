package com.hy.datacompile

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.TypeElement
import javax.lang.model.element.VariableElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic


/**
 * @Author Lenovo
 * 利用的是kapt,可以处理java和kotlin的注解
 * 增量式注解
 */
@AutoService(Processor::class)
public class DataApiProcessor:AbstractProcessor(){
    //处理Element的工具类
    private lateinit var mElementUtils: Elements

    //日志信息的输出
    private var mMessager: Messager? = null

    private lateinit var mFiler: Filer

    override fun init(processingEnv: ProcessingEnvironment?) {
        System.out.println("DataApiProcessor init")
        super.init(processingEnv)
        if (processingEnv != null) {
            mFiler = processingEnv.filer
        }
        mElementUtils = processingEnv?.elementUtils!!
        mMessager = processingEnv?.messager
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }


    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types = LinkedHashSet<String>()
        types.add(DataApi::class.java.canonicalName)
        types.add(DataMethod::class.java.canonicalName)
        return types
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        System.out.println("DataApiProcessor process")
        if (annotations.isNullOrEmpty() || roundEnv == null) {
            mMessager?.printMessage(Diagnostic.Kind.NOTE, "has no annotation end")
            return false
        }
        processDataApi(roundEnv)
        return true
    }

    fun processDataApi(roundEnv: RoundEnvironment?){
        try {
            val classElements = roundEnv?.getElementsAnnotatedWith(DataApi::class.java)
            val methodElements = roundEnv?.getElementsAnnotatedWith(DataMethod::class.java)
            classElements?.forEach { classElement ->
                //生成类
                val packageElement = mElementUtils.getPackageOf(classElement)
                //包名
                val packageName = packageElement.qualifiedName.toString()
                val className = "${classElement.simpleName}Repository"
                val repositoryTypeSpecBuilder = TypeSpec.classBuilder(className)
                methodElements?.forEach { methodElement ->
                    //生成方法
                    val methodName = methodElement.simpleName.toString()
                    val enclosingName = methodElement.enclosingElement.simpleName
                    //方法注解的类和类注解如果一致，那么说明是同一个类
                    if (classElement.simpleName.equals(enclosingName)) {
                        val excutableElement: ExecutableElement = methodElement as ExecutableElement
                        //获取方法的参数列表
                        val variableElements = excutableElement.parameters

                        var parameterSpecs = mutableListOf<HyParameter>()
                        variableElements?.forEach { parameterElement ->
                            parameterSpecs.add(createParameterSpec(parameterElement))
                        }
                        repositoryTypeSpecBuilder.addFunction(
                            createFunSpec(
                                methodName,
                                excutableElement,
                                classElement,
                                parameterSpecs
                            )
                        )
                    }
                }
                val fileSpec = FileSpec.builder(packageName, className)
                    .addType(repositoryTypeSpecBuilder.build())
                    .build()
                fileSpec.writeTo(mFiler)
            }
        } catch (e: Exception) {
            System.out.println("DataApiProcessor exception : " + e.message)
        }

    }

    /**
     * 创建方法
     * 最多支持三个参数
     */
    fun createFunSpec(
        methodName: String,
        excutableElement: ExecutableElement,
        classElement: Element,
        parameterSpecs: List<HyParameter>
    ): FunSpec {
        val funSpecBuild = FunSpec.builder(methodName)
        val threadPoolManagerClass = ClassName(
            "com.hy.common.threadpool",
            "ThreadPoolManager"
        )
        //获取NoteLocalDataApi
        val datalocalClassStr = getClassFromAnnotation(classElement)!!
        val lastIndex = datalocalClassStr.lastIndexOf(".")

        val datalocalClassName = ClassName(
            datalocalClassStr?.substring(0, lastIndex),
            datalocalClassStr.substring(lastIndex, datalocalClassStr.length)
        )

        parameterSpecs.forEach {
            funSpecBuild.addParameter(it.parameter)
        }

        //List比较特殊，因为excutableElement.returnType获取List的类型是java类型，需要处理成Kotlin的List
        var reponseCallParameter: TypeName
        if (excutableElement.returnType.toString().contains("List")) {
            val startIndex = excutableElement.returnType.toString().indexOf("<")
            val lastIndex = excutableElement.returnType.toString().lastIndexOf(">")
            val modelTypeStr =
                excutableElement.returnType.toString().subSequence(startIndex+1, lastIndex)
            reponseCallParameter = ClassName("kotlin.collections", "List")
                .parameterizedBy(ClassName(modelTypeStr.substring(0,modelTypeStr.lastIndexOf(".")),
                    modelTypeStr.substring(modelTypeStr.lastIndexOf(".")+1)))

        } else {
            reponseCallParameter = excutableElement.returnType.asTypeName()
        }
        //构造reponseCall参数
        val reponseClassName = ClassName(
            "com.hy.common.repo",
            "ReponseCall"
        ).parameterizedBy(reponseCallParameter)
        val reponseParameter = ParameterSpec.builder("reponse", reponseClassName).build()
        funSpecBuild.addParameter(reponseParameter)
        funSpecBuild.addStatement("%T.threadPool.execute{", threadPoolManagerClass)
            .addStatement("try{")
        if (parameterSpecs.isEmpty()) {
            funSpecBuild.addStatement("val t = %T().%N()", datalocalClassName, methodName)
        }
        if (parameterSpecs.size == 1) {
            funSpecBuild.addStatement(
                "val t = %T().%N(%N)",
                datalocalClassName,
                methodName,
                parameterSpecs[0].name
            )
        }
        if (parameterSpecs.size == 2) {
            funSpecBuild.addStatement(
                "val t = %T().%N(%N,%N)",
                datalocalClassName,
                methodName,
                parameterSpecs[0].name,
                parameterSpecs[1].name
            )
        }
        funSpecBuild.addStatement("%T.%N.post{", threadPoolManagerClass, "mainHandler")
            .addStatement("reponse?.onResponse(t)")
            .addStatement("}")
            .addStatement("}catch(e:%T){", java.lang.Exception::class)
            .addStatement("%T.%N.post{", threadPoolManagerClass, "mainHandler")
            .addStatement("reponse?.onError(e)")
            .addStatement("}")
            .addStatement("}")
            .addStatement("}")
        return funSpecBuild.build()
    }

    /**
     * 构造参数
     * VariableElement：
     */
    fun createParameterSpec(parameterElement: VariableElement): HyParameter {
        var parameterSpec: ParameterSpec
        //参数类型
        parameterSpec = ParameterSpec.builder(
            parameterElement.simpleName.toString(),
            parameterElement.asType().asTypeName()
        ).build()
        return HyParameter(parameterSpec, parameterElement.simpleName.toString())
    }

    private fun getClassFromAnnotation(key: Element): String? {
        //获取注解的class,element.getAnnotation(annotation) 会报异常.用其他方式获取
        val annotationMirrors = key.annotationMirrors
        for (annotationMirror in annotationMirrors) {
            if (DataApi::class.java.getName() == annotationMirror.annotationType.toString()) {
                val keySet: Set<ExecutableElement> = annotationMirror.elementValues.keys
                for (executableElement in keySet) {
                    if (executableElement.simpleName.toString() == "local") {
                        return annotationMirror.elementValues[executableElement]!!.value.toString()
                    }
                }
            }
        }
        return null
    }
}