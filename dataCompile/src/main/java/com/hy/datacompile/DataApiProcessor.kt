package com.hy.datacompile

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.*
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.tools.Diagnostic


/**
 * @Author Lenovo
 */
@AutoService(Processor::class)
public class DataApiProcessor:AbstractProcessor(){
    //处理Element的工具类
    private var mElementUtils: Elements? = null

    //日志信息的输出
    private var mMessager: Messager? = null

    private lateinit var mFiler: Filer

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)
        if (processingEnv != null) {
            mFiler = processingEnv.filer
        }
        mElementUtils = processingEnv?.elementUtils
        mMessager = processingEnv?.messager
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latestSupported()
    }

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        val types = LinkedHashSet<String>()
        types.add(DataApi::class.java.canonicalName)
        return types
    }

    override fun process(
        annotations: MutableSet<out TypeElement>?,
        roundEnv: RoundEnvironment?
    ): Boolean {
        if (annotations.isNullOrEmpty() || roundEnv == null) {
            mMessager?.printMessage(Diagnostic.Kind.NOTE, "has no annotation end")
            return false
        }
        processDataApi(roundEnv)
        return true
    }

    fun processDataApi(roundEnv: RoundEnvironment?){
        val elementUtils = processingEnv.elementUtils
        val typeUtils = processingEnv.getTypeUtils()
        try {
            val elements = roundEnv?.getElementsAnnotatedWith(DataMethod::class.java)
            elements?.forEach {
                mMessager?.printMessage(Diagnostic.Kind.NOTE, "className:${it.simpleName}")
                //先收集信息
                


//                //生成
//                val classElement = it
//                    .enclosingElement;
//                val packageElement = elementUtils.getPackageOf(classElement)
//
//                val noteRepositoryFile = ClassName("com.hy.common.note.repo.note","NoteRepositoryG")
//                val interfaceType = ClassName(packageElement.qualifiedName.toString(),it.simpleName.toString())
//
//
//                //伴随对象
//            val companion = TypeSpec.companionObjectBuilder()
//                .addProperty(PropertySpec.builder("TAG",String::class).initializer("%S","NoteRepositoryG").build())
//                .addProperty(PropertySpec.builder("instance",noteRepositoryFile).initializer("%T()",noteRepositoryFile).build())
//                .build()
//
//                val addNotefuntion = FunSpec.builder("addNote").addStatement("println(%S)","hello").build()
//
//                val noteRepository = TypeSpec.classBuilder("NoteRepositoryG")
//                      .addSuperinterface(interfaceType)
//                      .addType(companion)
//                      .addFunction(addNotefuntion)
//                    .build()
//                val file = FileSpec.builder("com.hy.common.note.repo.note", "NoteRepositoryG")
//                    .addType(noteRepository)
//                    .build()
//
//                file.writeTo(mFiler)
            }
        }catch (e:Exception){
            System.out.println("DataApiProcessor exception" + e.message)
        }

    }

}