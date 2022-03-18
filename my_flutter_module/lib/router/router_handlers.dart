import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:my_flutter_module/dicmanager_page.dart';
import 'package:my_flutter_module/main_page.dart';
import 'package:my_flutter_module/not_find_page.dart';
import 'package:my_flutter_module/setting.dart';

//欢迎页
var enmptyPageHandler = Handler(
    handlerFunc: (BuildContext? context, Map<String, List<String>> parameters) {
  return NotFindPage();
});

var splashPageHander = Handler(
    handlerFunc: (BuildContext? context, Map<String, List<String>> parameters) {
  return MainPage();
});

//设置页
var settingPageHandler = Handler(
    handlerFunc: (BuildContext? context, Map<String, List<String>> parameters) {
  return SettingPage();
});

//分类管理页面
var dicmanagerPageHandler = Handler(
    handlerFunc: (BuildContext? context, Map<String, List<String>> parameters) {
  return DicManagerPage();
});
