import 'dart:ui';

import 'package:fluro/fluro.dart';
import 'package:flutter/material.dart';
import 'package:my_flutter_module/router/application.dart';
import 'package:my_flutter_module/router/routers.dart';

void main() async {
  print("flutter main() " + window.defaultRouteName);
  final router = FluroRouter();
  Routers.configureRoutes(router);
  Application.router = router;
  runApp(MemRandumApp());
}

class MemRandumApp extends StatelessWidget {
  // This widget is the root of your application.
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'MemRandumApp App',
      navigatorKey: Application.navigatorKey,
      onGenerateRoute: Application.router.generator,
      theme: ThemeData(
        primarySwatch: Colors.blue,
      ),
    );
  }
}

