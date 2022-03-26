import 'package:extended_image/extended_image.dart';
import 'package:flutter/cupertino.dart';
import 'package:flutter/material.dart';
import 'package:my_flutter_module/native_image_provider.dart';

class DataDetail extends StatefulWidget {
  const DataDetail({Key? key, required this.imageUrl}) : super(key: key);
  final String imageUrl;

  @override
  State<StatefulWidget> createState() {
    return DataState(this.imageUrl);
  }
}

class DataState extends State<DataDetail> {
  DataState(this.imageUrl) {
    this.imageUrl = imageUrl;
  }

  String imageUrl = "";

  @override
  Widget build(BuildContext context) {
    return Container(
        height: 400.0,
        width: 400,
        child: ExtendedImage.network(
          this.imageUrl,
          width: ScreenUtil.instance.setWidth(400),
          height: ScreenUtil.instance.setWidth(400),
          fit: BoxFit.fill,
          cache: true,
          border: Border.all(color: Colors.red, width: 1.0),
          shape: boxShape,
          borderRadius: BorderRadius.all(Radius.circular(30.0)),
          //cancelToken: cancellationToken,
        ));
  }
}
