package com.hy.common.flutter

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.hy.common.manager.ActivityManager
import com.hy.utils.LogUtil
import io.flutter.embedding.android.FlutterActivity
import io.flutter.plugin.common.BasicMessageChannel
import io.flutter.plugin.common.StandardMessageCodec
import io.flutter.plugin.common.StringCodec

/**
 * @auther:hanshengjian
 * @date:2022/1/10
 *
 */
public class MemFlutterActivity:FlutterActivity(){

    private var basicMessageChannel:BasicMessageChannel<Any>?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        basicMessageChannel = flutterEngine?.dartExecutor?.binaryMessenger?.let {
            BasicMessageChannel(
                it,
            "flutter_plugin_basic_message_channel",StandardMessageCodec.INSTANCE)
        }
        basicMessageChannel?.setMessageHandler { message, reply ->
            LogUtil.i("MemFlutterActivity",message.toString())
            reply.reply("Android 已收到")
        }
        basicMessageChannel?.send("nihao")
        ActivityManager.push(activity)
    }

    companion object{
        @JvmStatic
        fun toSettingPage(context: Context,router:String,engineId:String){
            val intent = Intent(context,MemFlutterActivity::class.java)
            intent.putExtra(MemFlutterConstants.EXTRA_CACHED_ENGINE_ID, engineId)
            intent.putExtra(MemFlutterConstants.EXTRA_ROUTE, router)
            // Activity 销毁时保留 FlutterEngine
            // Activity 销毁时保留 FlutterEngine
            intent.putExtra(MemFlutterConstants.EXTRA_DESTROY_ENGINE_WITH_ACTIVITY, false)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityManager.pop()
    }

}