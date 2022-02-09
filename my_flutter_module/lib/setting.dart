import 'package:flutter/material.dart';
import 'package:fluttertoast/fluttertoast.dart';
import 'package:my_flutter_module/channel/FlutterPlugin.dart';

class SettingPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        leading: IconButton(
          icon: Icon(Icons.arrow_back, color: Colors.white),
          //包装一下，回到FlutterActivity 关闭
          onPressed: () => finishFlutterPage(),
        ),
        title: Text("设置"),
      ),
      body: Container(
          padding: EdgeInsets.all(10),
          child: GestureDetector(
              behavior: HitTestBehavior.opaque,
              child: Row(
                children: <Widget>[
                  Text("通知",style: TextStyle(fontSize: 16)),
                  Flexible(fit: FlexFit.tight, child: SizedBox()),
                  Image(
                    image: AssetImage("images/arrow_right.png"),
                    width: 25,
                    height: 25
                  )
                ],
              ),
              onTap: () => printPlatformState("Android Native 你好"))),
    );
  }

  Future printPlatformState(value) async {
    await FlutterPlugin.toNotificationManager;
    // Fluttertoast.showToast(msg: "Android 版本" + platformVersion);
    //String reply = await FlutterPlugin.basicMessageChannel.send(value);
    //print("Android Native 回复" + reply);
  }

  Future finishFlutterPage() async{
    await FlutterPlugin.finishFlutterPage;
  }

  static Future _handlePlatformIncrement(String message) async {
    return "这个是一个demo";
  }
}
