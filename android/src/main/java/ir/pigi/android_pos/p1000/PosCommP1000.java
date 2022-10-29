package ir.pigi.android_pos.p1000;

import static android.content.Intent.FLAG_ACTIVITY_NEW_TASK;

import android.Manifest;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.databinding.Observable;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import ir.pigi.android_pos.IPosComm;
import ir.pigi.android_pos.IPosRespCallback;
import ir.pigi.android_pos.PosResponseVM;
import ir.pigi.android_pos.Utility;
import ir.pigi.android_pos.p1000.P1000Reciever;

public class PosCommP1000 implements IPosComm {
    IPosRespCallback _callback;
    P1000Reciever p1000Reciever;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void checkPaper(Context context,String storeName) {
        Intent intent = new Intent();
        ComponentName cName = new ComponentName("com.pec.smartpos", "com.pec.smartpos.cpsdk.PecService");
        intent.setComponent(cName);
        intent.putExtra(PosTags.CompanyName, storeName);
        intent.putExtra(PosTags.checkPaper, "checkPaper");
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startForegroundService(intent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void startCamera(Context context,String storeName) {
        Intent intent = new Intent();
        ComponentName cName = new ComponentName("com.pec.smartpos", "com.pec.smartpos.cpsdk.PecService");
        intent.setComponent(cName);
        intent.putExtra(PosTags.CompanyName, storeName);
        intent.putExtra(PosTags.barCode, "barCode");
        context.startForegroundService(intent);
    }

    @Override
    public void startPayTxn(Context context,String storeName, String amount) {
        Intent intent = new Intent(PosTags.Action);
        intent.putExtra(PosTransactionType.transactionType, PosTransactionType.Sale);
        intent.putExtra(PosTags.CompanyName, storeName);
        intent.putExtra(PosTags.AM, amount);
        intent.putExtra("paymentType", "CARD");
        intent.setFlags(FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }

    @Override
    public void registerPayCallback(Context context,IPosRespCallback callback) {
        _callback = callback;
        p1000Reciever = new P1000Reciever();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("com.pec.ThirdCompany");
        context.registerReceiver(p1000Reciever,intentFilter);
        P1000Reciever.get_responseVm().addOnPropertyChangedCallback(posP1000StatusCallback);
    }

    @Override
    public void print(Context context, String b64Img,String storeName) {

//        ActivityCompat.requestPermissions((Activity)context, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE},
//                23);
        final Bitmap[] bm = {null};
        new Handler().postDelayed(new Runnable() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void run() {
                bm[0] = Utility.Base64ImgToBmp(b64Img);
                ArrayList<Bitmap> bitmapList = new ArrayList<>();
                bitmapList.add(bm[0]);
                saveFile(bitmapList);
                Intent intent = new Intent();
                ComponentName cName = new ComponentName("com.pec.smartpos", "com.pec.smartpos.cpsdk.PecService");
                intent.setComponent(cName);
                intent.putExtra("CompanyName", storeName);
                intent.putExtra("printType", "receiptBitMap");
                intent.putExtra("NumOfBitMap", bitmapList.size());
                Log.d("size:",String.valueOf(bitmapList.size()));
                context.startForegroundService(intent);
            }
        }, 100);
    }

    @Override
    public void dispose(Context context){
        context.unregisterReceiver(p1000Reciever);
    }

    private static void saveFile(List<Bitmap> bitmapList) {
        try {
            assert bitmapList != null;
            if(bitmapList.size() == 0) return;
            int i = 0;
            // Don't change this path name
            String savePath = Environment.getExternalStorageDirectory().getPath();
            File file = new File(savePath);
            if(!file.exists()) file.mkdirs();
            for(Bitmap bitmap : bitmapList) {
                String filename = savePath + "/pic" + i + ".bmp";
                file = new File(filename);
                if (!file.exists()) {
                    if(file.createNewFile()) {
                        FileOutputStream fileos = new FileOutputStream(filename);
                        bitmap.compress(Bitmap.CompressFormat.PNG, 100, fileos);
                        fileos.flush();
                        fileos.close();
                    }
                }
                i++;
            }
        }
        catch (Exception e) { Log.e("Exception : ", e.getMessage()); }
    }

    Observable.OnPropertyChangedCallback posP1000StatusCallback = new Observable.OnPropertyChangedCallback() {
        @Override
        public void onPropertyChanged(Observable observable, int i) {
            final Handler handler = new Handler(Looper.getMainLooper());
            handler.post(new Runnable() {
                @Override
                public void run() {
                    //Do something after 100ms
                    PosResponseVM responseVM = P1000Reciever.get_responseVm().get();
                    if(responseVM.status == 10000 || responseVM.status == 10001){
                        responseVM.IsPaperCheck = true;
                    }
                    else if(responseVM.status == 10003){
                        responseVM.IsCameraEvent = true;
                    }
                    Log.e("Resp",responseVM.toString());
                    _callback.onResult(responseVM);

                }
            });
        }
    };
}
