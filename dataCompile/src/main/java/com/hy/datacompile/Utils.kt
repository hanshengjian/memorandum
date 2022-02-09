package com.hy.datacompile

import com.squareup.kotlinpoet.ClassName

object Utils {
    fun createClassName(classPath:String):ClassName{
        val lastIndex = classPath.lastIndexOf(".")
        val packageName = classPath.substring(0,lastIndex)
        val className = classPath.substring(lastIndex,classPath.length)
        val obj = ClassName(packageName,className)
        return obj
    }

}