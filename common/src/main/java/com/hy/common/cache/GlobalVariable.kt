package com.hy.common.cache

import com.tencent.mmkv.MMKV

/**
 * 饿汉式单例
 */
class GlobalVariable() {
    private var mmkv:MMKV = MMKV.defaultMMKV()

    companion object{
        @Volatile private var instance:GlobalVariable?=null
        fun getInstance() = instance?: synchronized(this){
            instance?:GlobalVariable().also { instance = it }
        }
        //全局常量区
        val NOTICATION_OPEN = "NOTICATION_OPEN"
    }


    @Synchronized
    fun set(key:String,value:String){
        mmkv.putString(key,value)
    }

    @Synchronized
    fun get(key:String):String?{
        return mmkv.getString(key,"")
    }
}