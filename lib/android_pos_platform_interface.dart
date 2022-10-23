import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'android_pos_method_channel.dart';
import 'models/pos_resp_p1000_model.dart';
typedef PaymentCallback(PosRespModel model);
abstract class AndroidPosPlatform extends PlatformInterface {
  /// Constructs a AndroidPosPlatform.
  AndroidPosPlatform() : super(token: _token);

  static final Object _token = Object();

  static AndroidPosPlatform _instance = MethodChannelAndroidPos();

  /// The default instance of [AndroidPosPlatform] to use.
  ///
  /// Defaults to [MethodChannelAndroidPos].
  static AndroidPosPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [AndroidPosPlatform] when
  /// they register themselves.
  static set instance(AndroidPosPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<String?> startPaymentTxn(String storeName,String amount,PaymentCallback callback){
    throw UnimplementedError('startPaymentTxn() has not been implemented.');
  }

  Future<String?> checkPaper(String storeName,PaymentCallback callback){
    throw UnimplementedError('checkPaper() has not been implemented.');
  }

  Future<String?> startCamera(String storeName,PaymentCallback callback){
    throw UnimplementedError('startCamera() has not been implemented.');
  }

  Future<String?> printBitmap(String bmp,String storeName){
    throw UnimplementedError('printBitmap() has not been implemented.');
  }
}
