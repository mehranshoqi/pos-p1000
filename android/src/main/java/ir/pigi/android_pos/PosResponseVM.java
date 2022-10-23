package ir.pigi.android_pos;

import androidx.annotation.Keep;

@Keep
public class PosResponseVM {
    public int status;
    public String Message;
    public String ResponseCode;
    public String RRN;
    public String CardNo;
    public String DateTime;
    public String SerialNo;
    public String TerminalNo;
    public boolean IsPaperCheck;
    public boolean IsCameraEvent;
}
