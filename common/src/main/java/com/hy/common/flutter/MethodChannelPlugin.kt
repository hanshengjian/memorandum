package com.hy.common.flutter

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import com.hy.common.extend.getApplication
import com.hy.common.manager.ActivityManager
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.EventChannel
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel

class MethodChannelPlugin:FlutterPlugin,MethodChannel.MethodCallHandler,EventChannel.StreamHandler {
    var channel:MethodChannel?=null
    var eventChannel:EventChannel?=null

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(binding.binaryMessenger,"flutter_plugin_method_channel")
        channel?.setMethodCallHandler(this)

        eventChannel = EventChannel(binding.binaryMessenger,"flutter_plugin_event_channel")
        eventChannel?.setStreamHandler(this)
    }


    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel?.setMethodCallHandler(null)
    }

    override fun onMethodCall(call: MethodCall, result: MethodChannel.Result) {
        if(call.method.equals("toNotificationManager")){
            val intent = Intent()
            intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS;
            intent.setData(Uri.fromParts("package", getApplication().getPackageName(), null));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            ActivityManager.peek().startActivity(intent)
            result.success("Android " + Build.VERSION.RELEASE)
        }else if(call.method.equals("finishFlutterPage")){
            if(ActivityManager.peek() is MemFlutterActivity){
                ActivityManager.peek().finish()
            }
            result.success(null)
        }else{
            result.notImplemented()
        }
    }

    override fun onListen(arguments: Any?, events: EventChannel.EventSink?) {
        TODO("Not yet implemented")
    }

    override fun onCancel(arguments: Any?) {
        TODO("Not yet implemented")
    }
}