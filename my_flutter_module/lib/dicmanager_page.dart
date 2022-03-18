import 'package:flutter/material.dart';
import 'package:flutter/widgets.dart';
import 'package:my_flutter_module/channel/FlutterPlugin.dart';

import 'MemTab.dart';
import 'datalist_one.dart';

class DicManagerMainPage extends StatelessWidget {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: DicManagerPage(),
    );
  }
}

class DicManagerPage extends StatefulWidget {
  @override
  _DicManagerPageState createState() {
    return _DicManagerPageState();
  }
}

class _DicManagerPageState extends State<DicManagerPage> {
  final List<MemTab> myTabs = <MemTab>[
    MemTab(
      '选项卡一',
    ),
    MemTab(
      '选项卡二',
    ),
    MemTab(
      '选项卡三',
    ),
  ];

  @override
  Widget build(BuildContext context) {
    return DefaultTabController(
      //选项卡长度
      length: myTabs.length,
      child: Scaffold(
          appBar: AppBar(
            leading: IconButton(
              icon: Icon(Icons.arrow_back, color: Colors.white),
              onPressed: () => FlutterPlugin.finishFlutterPage,
            ),
            title: Text("编辑"),
            //添加选项卡按钮，注意此bottom表示在AppBar的底部，在整个页面上看还是处于顶部区域
            bottom: TabBar(
              tabs: myTabs,
            ),
          ),
          body: TabBarView(children: [
            DataOne(),
            DataOne(),
            DataOne(),
          ])),
    );
  }
}
