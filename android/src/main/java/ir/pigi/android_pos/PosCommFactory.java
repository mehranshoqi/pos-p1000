package ir.pigi.android_pos;

import android.content.res.Resources;

import ir.pigi.android_pos.p1000.PosCommP1000;

public class PosCommFactory {
    PosCommP1000 posCommP1000;
    public IPosComm getPosComm(){
        if(Utility.isPosP1000()){
            if(posCommP1000 == null) {
                posCommP1000 = new PosCommP1000();
            }

                return posCommP1000;
        }
        else
            throw new Resources.NotFoundException();
    }
}
