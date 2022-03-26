package com.hy.common.widget

import android.content.Context
import com.bumptech.glide.Glide
import com.hy.common.manager.ActivityManager.Companion.peek
import com.hy.common.threadpool.ThreadPoolManager.mainHandler
import com.hy.common.threadpool.ThreadPoolManager.threadPool
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.embedding.engine.plugins.FlutterPlugin.FlutterPluginBinding
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import java.util.concurrent.ExecutionException

/**
 * @auther:hanshengjian
 * @date:2022/3/25
 */
class FlutterNativeImageChannel : MethodCallHandler, FlutterPlugin {
    private val mContext: Context? = null
    private var channel: MethodChannel? = null
    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if (call.method == "getNativeImage") {
            //todo width,height
            threadPool.submit { //获取图片
                val url = call.arguments as String
                //Glide 加载url，获取sd卡地址
                //获取当前的FlutterActivity
                try {
                    val mfile = Glide.with(peek()).asFile().load(url).submit().get()
                    mainHandler.post { result.success(mfile.absolutePath) }
                } catch (e: ExecutionException) {
                    e.printStackTrace()
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                } catch (e: Exception) {
                    e.printStackTrace()
                }
            }
        }
    }

    override fun onAttachedToEngine(binding: FlutterPluginBinding) {
        channel = MethodChannel(binding.binaryMessenger, CHANNEL_ROUTER)
        channel!!.setMethodCallHandler(this)
    }

    override fun onDetachedFromEngine(binding: FlutterPluginBinding) {}

    companion object {
        private const val TAG = "FlutterNativeImageChannel"
        private const val CHANNEL_ROUTER = "com.memorandum/native_image"
    }
}