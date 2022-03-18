package com.hy.memorandum


import cody.bus.ElegantBus
import com.alibaba.android.arouter.launcher.ARouter
import com.hy.common.base.BaseApp
import com.hy.common.flutter.MemFlutterConstants
import com.hy.common.flutter.MethodChannelPlugin
import com.hy.utils.LogUtil
import com.tencent.mmkv.MMKV
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.embedding.engine.FlutterEngineCache
import io.flutter.embedding.engine.dart.DartExecutor


/**
 * @Author Lenovo
 */
class NoteApplication:BaseApp() {

    companion object {
        const val TAG = "NoteApplication"
    }

    override fun onCreate() {
        super.onCreate()
        LogUtil.i(TAG, "ARouter init start")
        ARouter.openLog()
        ARouter.init(this)
        LogUtil.i(TAG, "ARouter init end")
        ElegantBus.setDebug(true)
        LogUtil.i(TAG, "MMKV init start")
        val rootDir = MMKV.initialize(this)
        LogUtil.i(TAG, "MMKV init end")

        LogUtil.i(TAG, "Flutter init start")
        try {
            val flutterEngine = FlutterEngine(this)
            flutterEngine.getNavigationChannel()
                .setInitialRoute("MemoRandumAPP")
            flutterEngine.getDartExecutor()
                .executeDartEntrypoint(DartExecutor.DartEntrypoint.createDefault())
            flutterEngine.plugins.add(MethodChannelPlugin())
            FlutterEngineCache.getInstance()
                .put(MemFlutterConstants.FLUTTER_ENGINE_ID_VERTICAL, flutterEngine)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        LogUtil.i(TAG, "Flutter init end")
    }
}