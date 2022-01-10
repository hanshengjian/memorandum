package com.hy.memorandum


import cody.bus.ElegantBus
import com.alibaba.android.arouter.launcher.ARouter
import com.hy.common.base.BaseApp
import com.hy.common.flutter.MemFlutterConstants
import com.tencent.mmkv.MMKV
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor
import io.flutter.embedding.engine.plugins.shim.ShimPluginRegistry


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

        try {
            val flutterEngine = FlutterEngine(this)
            flutterEngine.getNavigationChannel()
                .setInitialRoute("main")
            flutterEngine.getDartExecutor()
                .executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
            FlutterEngineCache.getInstance()
                .put(MemFlutterConstants.FLUTTER_ENGINE_ID_VERTICAL, flutterEngine)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }
}