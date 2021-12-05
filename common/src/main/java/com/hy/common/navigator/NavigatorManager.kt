package com.hy.common.navigator

import android.os.Build
import android.os.Parcelable
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.launcher.ARouter
import com.hy.common.arouter.Param
import com.hy.common.arouter.Route
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Parameter
import java.lang.reflect.Proxy
import kotlin.Int as Int

/**
 * @Author Lenovo
 */
object NavigatorManager {
    fun <T> getNavigator(classObj: Class<T>): T? {
        val t = Proxy.newProxyInstance(NavigatorManager.javaClass.classLoader,
            arrayOf(classObj),
            { proxy, method, args ->
                //解析路由协议
                //route,param
                arouter(parseNavigatorMethod(method, args))
            })

        return t as T
    }

    fun parseNavigatorMethod(method: Method, objects: Array<Any>?): NavigatorMethod {
        val navigatorMethod: NavigatorMethod = NavigatorMethod()

        if (method.isAnnotationPresent(Route::class.java)) {
            val annotation = method.getAnnotation(Route::class.java)
            navigatorMethod.path = annotation.path
            val parmametersAnnotation = method.parameterAnnotations;
            val parameterTypes = method.parameterTypes
            for (index in 0..parmametersAnnotation.size-1){
                val annotation = parmametersAnnotation[index][0]
                val natigatorParam = NatigatorParam()
                if(annotation is Param){
                    natigatorParam.name = annotation.name
                    natigatorParam.value = objects?.get(index)
                    natigatorParam.type = parameterTypes[index].name
                    natigatorParam.typeClass = parameterTypes[index]
                    navigatorMethod.params.add(natigatorParam)
                }
            }
        }
        return navigatorMethod
    }

    fun arouter(navigatorMethod: NavigatorMethod) {
        val postcard = ARouter.getInstance().build(navigatorMethod.path)
        navigatorMethod.params.forEach {
            if (it.type.equals("int")) {
                postcard.withInt(it.name, it.value as Int)
            } else if (it.type.equals("String")) {
                postcard.withString(it.name, it.value as String)
            } else if (it.type.equals("long")) {
                postcard.withLong(it.name, it.value as Long)
            } else if (it.type.equals("int[]")) {
                postcard.withIntegerArrayList(
                    it.name,
                    ArrayList<Int>((it.value as IntArray).toList())
                )
            }else if(it.type!!.contains("List")){
                postcard.withParcelableArray(it.name, it.value as Array<Parcelable>)
            }else {
                postcard.withParcelable(it.name,it.value as Parcelable)
            }
        }
        postcard.navigation()
    }
}