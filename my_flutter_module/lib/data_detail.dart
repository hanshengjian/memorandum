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
    return ExtendedImage.network(
      this.imageUrl,
      fit: BoxFit.contain,
      //enableLoadState: false,
      enableSlideOutPage: true,
      mode: ExtendedImageMode.gesture,
      initGestureConfigHandler: (state) {
        return GestureConfig(
            minScale: 0.9,
            animationMinScale: 0.7,
            maxScale: 1,
            animationMaxScale: 3.5,
            speed: 1.0,
            inertialSpeed: 100.0,
            initialScale: 1.0,
            inPageView: true);
      },
    );
  }
}
