package com.hy.memorandum

import android.app.Application
import android.content.Context
import androidx.multidex.MultiDex
import cody.bus.ElegantBus
import com.alibaba.android.arouter.launcher.ARouter
import com.hy.common.base.BaseApp
import com.tencent.mmkv.MMKV




/**
 * @Author Lenovo
 */
class NoteApplication:BaseApp() {
    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()
        ARouter.init(this)
        ElegantBus.setDebug(true)
        val rootDir = MMKV.initialize(this)
        println("mmkv root: $rootDir")
    }
}