import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:flutter/widgets.dart';
import 'package:my_flutter_module/channel/FlutterPlugin.dart';
import 'package:my_flutter_module/dicmanager_page.dart';
import 'package:my_flutter_module/setting.dart';

import 'channel/FlutterPlugin.dart';

class MainPage extends StatefulWidget {
  @override
  State<StatefulWidget> createState() {
    print("flutterMainPage");
    return _MainPageState();
  }
}

class _MainPageState extends State<MainPage> {
  late Widget _initRoute = DefaultHomePage();

  @override
  void initState() {
    super.initState();
    FlutterPlugin.methodchannel.setMethodCallHandler((call) async {
      print("methodChannel ${call.method}");
      switch (call.method) {
        case "setInitRoute":
          handleInitRouteMethodCall(call);
          break;
      }
    });
  }

  /// 处理来自 setInitRoute 的消息
  void handleInitRouteMethodCall(MethodCall call) async {
    print("handleInitRouteMethodCall ${call.arguments}");
    var paramMap = toParamsMap(call.arguments);
    handleRoutePage(call.arguments);
    if (mounted) {
      /// 更新界面
      setState(() {});
    }
  }

  void handleRoutePage(String showPage) {
    print("handleRoutePage ${showPage}");
    switch (showPage) {
      case "dicmanager_page":
        if (!(_initRoute is DicManagerMainPage)) {
          _initRoute = DicManagerMainPage();
        }
        break;
      case "setting_page":
        if (!(_initRoute is SettingPage)) {
          _initRoute = SettingPage();
        }
        break;
    }
  }

  Map<String, String>? toParamsMap(String router) {
    List<String>? params = router?.split("?");
    if (params != null && params.length > 0) {
      String realParams = params[params.length - 1];
      return Uri.splitQueryString(realParams);
    }
    return null;
  }

  @override
  Widget build(BuildContext context) {
    return _initRoute;
  }
}

/// 默认入口，空白即可
class DefaultHomePage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return Scaffold(
      backgroundColor: Colors.white,
      appBar: AppBar(
        title: Text("空"),
      ),
      body: Container(),
    );
  }
}
