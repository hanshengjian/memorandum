package com.hy.datacompile

import com.squareup.kotlinpoet.*
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import javax.lang.model.element.Element
import javax.lang.model.element.ElementKind
import javax.lang.model.element.ExecutableElement
import javax.lang.model.element.VariableElement

/**
 * @auther:hanshengjian
 * @date:2022/1/17
 *
 */
class HyRepositoryTypeSpec(
    val packageName: String,
    val className: String,
    val classElement: Element
) {
    var repositoryTypeSpecBuilder: TypeSpec.Builder

    init {
        repositoryTypeSpecBuilder = TypeSpec.classBuilder(className)
    }

    val THREADPOOL_MANAGER = ClassName(
        "com.hy.common.threadpool",
        "ThreadPoolManager"
    )
    val REPONSECALL = ClassName(
        "com.hy.common.repo",
        "ReponseCall"
    )
    val PARAMETER_REPONSE: String = "reponse"
    val MAINHANDLER: String = "mainHandler"

    fun parse() {
        System.out.println("DataApiProcessor parse")
        val encloseElements = classElement.enclosedElements
        encloseElements.forEach {
            if (it.kind == ElementKind.METHOD) {
                if (it.getAnnotation(DataMethod::class.java) != null) {
                    val excutableElement: ExecutableElement = it as ExecutableElement
                    //获取方法的参数列表
                    val variableElements = excutableElement.parameters

                    var parameterSpecs = mutableListOf<HyParameter>()
                    variableElements?.forEach { parameterElement ->
                        parameterSpecs.add(createParameterSpec(parameterElement))
                    }
                    val methodName = excutableElement.simpleName.toString()
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
        }
    }

    fun createTypeSpec(): TypeSpec {
        return repositoryTypeSpecBuilder.build()
    }


    /**
     * 创建方法
     * 最多支持三个参数
     */
    private fun createFunSpec(
        methodName: String,
        excutableElement: ExecutableElement,
        classElement: Element,
        parameterSpecs: List<HyParameter>
    ): FunSpec {
        val funSpecBuild = FunSpec.builder(methodName)
        //获取NoteLocalDataApi
        val datalocalClassStr = getClassFromAnnotation(classElement)!!
        val lastIndex = datalocalClassStr.lastIndexOf(".")

        val datalocalClassName = Utils.createClassName(datalocalClassStr)
        parameterSpecs.forEach {
            funSpecBuild.addParameter(it.parameter)
        }

        //List比较特殊，因为excutableElement.returnType获取List的类型是java类型，需要处理成Kotlin的List
        var reponseCallParameter: TypeName
        if (excutableElement.returnType.toString().contains("List")) {
            val startIndex = excutableElement.returnType.toString().indexOf("<")
            val lastIndex = excutableElement.returnType.toString().lastIndexOf(">")
            val modelTypeStr =
                excutableElement.returnType.toString().subSequence(startIndex + 1, lastIndex)

            reponseCallParameter = LIST
                .parameterizedBy(Utils.createClassName(modelTypeStr.toString()))
        } else {
            reponseCallParameter = excutableElement.returnType.asTypeName()
        }
        //构造reponseCall参数
        val reponseClassName = REPONSECALL.parameterizedBy(reponseCallParameter)
        val reponseParameter = ParameterSpec.builder(PARAMETER_REPONSE, reponseClassName).build()
        funSpecBuild.addParameter(reponseParameter)

        funSpecBuild.addStatement("%T.threadPool.execute{", THREADPOOL_MANAGER)
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
        funSpecBuild.addStatement("%T.%N.post{", THREADPOOL_MANAGER, MAINHANDLER)
            .addStatement("%N?.onResponse(t)", PARAMETER_REPONSE)
            .addStatement("}")
            .addStatement("}catch(e:%T){", java.lang.Exception::class)
            .addStatement("%T.%N.post{", THREADPOOL_MANAGER, MAINHANDLER)
            .addStatement("%N?.onError(e)", PARAMETER_REPONSE)
            .addStatement("}")
            .addStatement("}")
            .addStatement("}")
        return funSpecBuild.build()
    }


    /**
     * 构造参数
     * VariableElement：
     */
    private fun createParameterSpec(parameterElement: VariableElement): HyParameter {
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