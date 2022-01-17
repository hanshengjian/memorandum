package com.hy.datacompile

import com.google.auto.service.AutoService
import com.squareup.kotlinpoet.FileSpec
import java.util.*
import javax.annotation.processing.*
import javax.lang.model.SourceVersion
import javax.lang.model.element.TypeElement
import javax.lang.model.util.Elements
import javax.lang.model.util.Types
import javax.tools.Diagnostic


/**
 * @Author Lenovo
 */
@AutoService(Processor::class)
public class DataApiProcessor:AbstractProcessor(){
    //处理Element的工具类
    private var mElementUtils: Elements? = null
    private var mTypeUtils: Types? = null

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
        mTypeUtils = processingEnv?.getTypeUtils()
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
        try {
            val classElements = roundEnv?.getElementsAnnotatedWith(DataApi::class.java)
            classElements?.forEach { classElement ->
                //生成类
                val packageElement = mElementUtils!!.getPackageOf(classElement)
                val repositoryTypeSpec = HyRepositoryTypeSpec(
                    packageElement.qualifiedName.toString(),
                    "${classElement.simpleName}Repository", classElement
                )
                repositoryTypeSpec.parse()

                val fileSpec =
                    FileSpec.builder(repositoryTypeSpec.packageName, repositoryTypeSpec.className)
                        .addType(repositoryTypeSpec.createTypeSpec())
                        .build()
                fileSpec.writeTo(mFiler)
            }
        } catch (e: Exception) {
            System.out.println("DataApiProcessor exception : " + e.message)
        }

    }
}