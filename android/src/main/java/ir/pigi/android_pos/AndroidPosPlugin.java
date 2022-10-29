package ir.pigi.android_pos;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.Observable;

import com.google.gson.Gson;
import com.kishcore.sdk.hybrid.api.HostApp;
import com.kishcore.sdk.hybrid.api.SDKManager;

import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;

/** AndroidPosPlugin */
public class AndroidPosPlugin implements FlutterPlugin, MethodCallHandler {
  /// The MethodChannel that will the communication between Flutter and native Android
  ///
  /// This local reference serves to register the plugin with the Flutter Engine and unregister it
  /// when the Flutter Engine is detached from the Activity
  private MethodChannel channel;
  Context context;
  PosCommFactory factory;

  @Override
  public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
    channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "android_pos");
    channel.setMethodCallHandler(this);

    context = flutterPluginBinding.getApplicationContext();
    factory = new PosCommFactory();
    IPosComm pos = factory.getPosComm();
    pos.registerPayCallback(context,res->{
      Gson gson = new Gson();
      String json = gson.toJson(res);
      if(res.IsPaperCheck){
        res.IsPaperCheck = false;
        channel.invokeMethod("paperCheckResult", json);
      }
      else if(res.IsCameraEvent){
        res.IsCameraEvent = false;
        channel.invokeMethod("cameraResult", json);
      }
      else
      channel.invokeMethod("paymentResult", json);
    });
//    SDKManager.purchase(context, HostApp.UNKNOWN,1200,1,0,false,null,null,null,());
  }

  @Override
  public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
    IPosComm pos = factory.getPosComm();
    if (call.method.equals("getPlatformVersion")) {
      result.success("Android " + android.os.Build.VERSION.RELEASE);
    }
    else if(call.method.equals("startPaymentTxn")){
      String storeName = call.argument("storeName");
      String amount = call.argument("amount");
      Log.d("pay amount :",amount);

      pos.startPayTxn(context,storeName,amount);
    }
    else if(call.method.equals("checkPaper")){
      String storeName = call.argument("storeName");
      pos.checkPaper(context,storeName);
    }
    else if(call.method.equals("startCamera")){
      String storeName = call.argument("storeName");
      pos.startCamera(context,storeName);
    }
    else if(call.method.equals("printBmp")){
      //Log.d("argument:",call.argument);
      String bmp = call.argument("bmp");
      String storeName = call.argument("storeName");

      Log.d("argument:",bmp);

      pos.print(context,bmp,storeName);

    }
    else {
      result.notImplemented();
    }
  }

  @Override
  public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
    channel.setMethodCallHandler(null);
    IPosComm pos = factory.getPosComm();
    pos.dispose(context);
  }


}
