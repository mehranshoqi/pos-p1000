package ir.pigi.android_pos;

import android.content.Context;

public interface IPosComm {

    void checkPaper(Context context,String storeName);

    void startCamera(Context context,String storeName);

    void startPayTxn(Context context,String storeName, String amount);

    void registerPayCallback(Context context,IPosRespCallback callback);

    void print(Context context,String b64Img,String storeName);

    void dispose(Context context);
}
