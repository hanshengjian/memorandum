package com.hy.common.flutter

import android.content.Context
import android.content.Intent
import io.flutter.embedding.android.FlutterActivity

/**
 * @auther:hanshengjian
 * @date:2022/1/10
 *
 */
public class MemFlutterActivity:FlutterActivity(){

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

}