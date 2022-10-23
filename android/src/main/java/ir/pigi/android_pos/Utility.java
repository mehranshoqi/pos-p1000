package ir.pigi.android_pos;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.util.Base64;

public class Utility {
    public static boolean isPosP1000()
    {
        return Build.MODEL.equals(Values.PosP1000ModelName);
    }

    public static boolean isPosPaxA80()
    {
        return Build.MODEL.equals(Values.PosA80ModelName);
    }

    public static Bitmap Base64ImgToBmp(String b64Img){
        byte[] decodedString = Base64.decode(b64Img, Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }
}
