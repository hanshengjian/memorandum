package com.hy.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

object JsonUtil {
    val gson = Gson()

    fun <T> fromJson(content:String, cls:Class<T>):T{
        return gson.fromJson<T>(content,cls)
    }

    fun <T> fromJsonToList(content:String,type: Type):T{
        return gson.fromJson(content,type)
    }
}