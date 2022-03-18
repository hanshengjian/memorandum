import 'package:fluro/fluro.dart';
import 'package:my_flutter_module/router/router_handlers.dart';

class Routers {
  static const String MemoRandumAPP = "MemoRandumAPP";
  static const String setting = "setting_page";
  static const String dicmanager = "dicmanager_page";

  static void configureRoutes(FluroRouter router) {
    router.define(MemoRandumAPP, handler: splashPageHander);
    router.define(setting, handler: settingPageHandler);
    router.define(dicmanager, handler: dicmanagerPageHandler);
    router.notFoundHandler = enmptyPageHandler;
  }
}
