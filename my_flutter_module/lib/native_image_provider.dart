import 'dart:io';
import 'dart:typed_data';
import 'dart:ui' as ui show Codec;

import 'package:flutter/foundation.dart';
import 'package:flutter/material.dart';

import 'channel/FlutterPlugin.dart';

class NativeImageProvider extends ImageProvider<NativeImageProvider> {
  /// 需要从原生中去加载的图片名格式如： icon_back.png / icon_new.jpg
  final String imageName;

  /// Scale of the image
  final double scale;

  const NativeImageProvider(this.imageName, {this.scale: 1.0});

  Future<ui.Codec> _loadAsync(NativeImageProvider key) async {
    String path = await FlutterPlugin.getImageUrl(imageName);
    var file = File(path);
    return await _loadAsyncFromFile(key, file);
  }

  Future<ui.Codec> _loadAsyncFromFile(
      NativeImageProvider key, File file) async {
    assert(key == this);

    final Uint8List bytes = await file.readAsBytes();

    if (bytes.lengthInBytes == 0) {
      throw new Exception("File was empty");
    }
    return await PaintingBinding.instance!.instantiateImageCodec(bytes);
  }

  @override
  Future<NativeImageProvider> obtainKey(ImageConfiguration configuration) {
    return new SynchronousFuture<NativeImageProvider>(this);
  }

  bool operator ==(Object other) {
    if (other.runtimeType != runtimeType) {
      return false;
    }
    return other is NativeImageProvider && other.imageName == imageName;
  }

  @override
  ImageStreamCompleter load(NativeImageProvider key, DecoderCallback decode) {
    return new MultiFrameImageStreamCompleter(
        codec: _loadAsync(key), scale: key.scale);
  }

  @override
  int get hashCode => imageName.hashCode;
}
