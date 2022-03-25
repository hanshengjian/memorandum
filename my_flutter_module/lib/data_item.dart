import 'package:flutter/material.dart';

import 'native_image_provider.dart';

class DataItem extends StatelessWidget {
  const DataItem({this.title, this.image});

  final String? title;
  final String? image;

  @override
  Widget build(BuildContext context) {
    return Row(
      children: [
        Text(this.title!, style: TextStyle(fontSize: 14)),
        Image(image: NativeImageProvider(image!), width: 100, height: 100)
      ],
    );
  }
}
