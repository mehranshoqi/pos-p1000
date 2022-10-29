import 'package:android_pos/models/pos_resp_p1000_model.dart';
import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'android_pos_platform_interface.dart';

/// An implementation of [AndroidPosPlatform] that uses method channels.
class MethodChannelAndroidPos extends AndroidPosPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('android_pos');

  PaymentCallback? _callback;
  PaymentCallback? _callbackPaper;
  PaymentCallback? _callbackCamera;

  MethodChannelAndroidPos() {
    methodChannel.setMethodCallHandler((call) async {
      switch (call.method) {
        case "paymentResult":
          print(call.arguments.toString());
          var res = PosRespModel.fromJson(call.arguments);
          _callback!(res);
          break;
        case "paperCheckResult":
          var res = PosRespModel.fromJson(call.arguments);
          _callbackPaper!(res);
          break;
        case "cameraResult":
          var res = PosRespModel.fromJson(call.arguments);
          _callbackCamera!(res);
          break;
      }
    });
  }

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<String?> startPaymentTxn(
      String storeName, String amount, PaymentCallback callback) async {
    _callback = callback;
    await methodChannel.invokeMethod<String>(
        'startPaymentTxn', {"storeName": storeName, "amount": amount});
    return null;
  }

  @override
  Future<String?> checkPaper(String storeName, PaymentCallback callback) async {
    _callbackPaper = callback;
    await methodChannel
        .invokeMethod<String>('checkPaper', {"storeName": storeName});
    return null;
  }

  @override
  Future<String?> startCamera(
      String storeName, PaymentCallback callback) async {
    _callbackCamera = callback;
    await methodChannel
        .invokeMethod<String>('startCamera', {"storeName": storeName});
    return null;
  }

  @override
  Future<String?> printBitmap(String bmp, String storeName) async {
    await methodChannel
        .invokeMethod<String>('printBmp', {"bmp": bmp, "storeName": storeName});
    return null;
  }
}
