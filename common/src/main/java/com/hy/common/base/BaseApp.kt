package com.hy.common.base

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex

/**
 * @Author Lenovo
 */
open class BaseApp:Application() {

    override fun attachBaseContext(base: Context?) {
        super.attachBaseContext(base)
        MultiDex.install(this);
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object{
        lateinit var instance:Application
    }
}