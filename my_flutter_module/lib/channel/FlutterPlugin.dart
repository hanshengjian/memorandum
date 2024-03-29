
import 'package:flutter/services.dart';

class FlutterPlugin{
  static const MethodChannel methodchannel =
      const MethodChannel("flutter_plugin_method_channel");
  static const BasicMessageChannel basicMessageChannel =
      const BasicMessageChannel(
          'flutter_plugin_basic_message_channel', StandardMessageCodec());
  static const MethodChannel nativeMsgMethodchannel =
      const MethodChannel("com.memorandum/native_image");

  static Future<String> get toNotificationManager async {
    final String version =
        await methodchannel.invokeMethod("toNotificationManager");
    return version;
  }

  static Future<void> get finishFlutterPage async {
    await methodchannel.invokeMethod("finishFlutterPage");
  }

  static Future<String> getImageUrl(String url) async {
    final String localUrl =
        await nativeMsgMethodchannel.invokeMethod("getNativeImage", url);
    return localUrl;
  }
}