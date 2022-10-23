import 'package:permission_handler/permission_handler.dart';

import 'android_pos_platform_interface.dart';

class AndroidPos {
  Future<String?> getPlatformVersion() {
    return AndroidPosPlatform.instance.getPlatformVersion();
  }

  Future<String?> startPaymentTxn(String storeName,String amount,PaymentCallback callback){
    return AndroidPosPlatform.instance.startPaymentTxn(storeName,amount,callback);
  }

  Future<String?> checkPaper(String storeName,PaymentCallback callback){
    return AndroidPosPlatform.instance.checkPaper(storeName, callback);
  }

  Future<String?> startCamera(String storeName,PaymentCallback callback){
    return AndroidPosPlatform.instance.startCamera(storeName, callback);
  }

  Future<String?> printBitmap(String b64Bmp,String storeName) async{
    // Map<Permission, PermissionStatus> statuses = await [
    //   Permission.manageExternalStorage,
    // ].request();
    // if(statuses.values.first.isGranted)
      if(await Permission.storage.request().isGranted)
    return AndroidPosPlatform.instance.printBitmap(b64Bmp, storeName);
  }
}
