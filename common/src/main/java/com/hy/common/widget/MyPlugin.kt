package com.hy.common.widget

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.BinaryMessenger

/**
 * @auther:hanshengjian
 * @date:2022/3/24
 *
 */
public class MyPlugin : FlutterPlugin {

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        val messenger: BinaryMessenger = binding.binaryMessenger
        binding
            .platformViewRegistry
            .registerViewFactory(
                "com.emerandum/custom_platform_view", MyViewFactory(messenger)
            )
    }

//    companion object {
//        @JvmStatic
//        fun registerWith(registrar: PluginRegistry.Registrar) {
//            registrar
//                .platformViewRegistry()
//                .registerViewFactory(
//                    "plugins.flutter.io/custom_platform_view",
//                    MyViewFactory(registrar.messenger()))
//        }
//    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {

    }
}
