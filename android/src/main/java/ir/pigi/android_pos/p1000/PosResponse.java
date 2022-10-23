package ir.pigi.android_pos.p1000;

import java.util.Arrays;

public class PosResponse {

    public static final String CompanyName               = "CompanyName";
    public static final String TransactionType           = "TransactionType";
    public static final String ResponseCode              = "ResponseCode";
    public static final String CardNumber                = "CardNumber";
    public static final String RRN                       = "RRN";
    public static final String SerialNumber              = "SerialNumber";
    public static final String TerminalNumber            = "TerminalNumber";
    public static final String DateTime                  = "DateTime";
    public static final String Amount                    = "Amount";
    public static final String PrintStatus               = "PrintStatus";
    public static final String CheckPaper                = "CheckPaper";
    public static final String BarCodeStatus             = "BarCodeStatus";
    //public static final String barCode                   = "barCode";
    public static final String MifareResult              = "MifareResult";
    public static final String SwipeCard                 = "SwipeCard";
    public static final String TimeOut                   = "TimeOut";
    public static final String GetInfo                   = "GetInfo";
    public static final String GetLastSaleSuccessfulTrx  = "getLastSaleSuccessfulTrx";
    public static final String GetLastSaleFailedTrx      = "getLastSaleFailedTrx";
    public static final String Error                     = "Error";

    public String compName = "";
    public String trxType  = "";
    public String RS       = "";
    public String CN       = "";
    public String _RRN     = "";
    public String SN       = "";
    public String TN       = "";
    public String DT       = "";
    public String AM       = "";
    public String PS       = "";
    public String CP       = "";
    public String BR       = "";
    public String MI           ;
    public String[] GI         ;
    public String SC       = "";
    public String TS       = "";
    public String TF       = "";
    public String TO       = "";
    public String ER           ;

    @Override
    public String toString() {
        return "Response{" +
                "compName='" + compName + '\'' +
                ", trxType='" + trxType + '\'' +
                ", RS='" + RS + '\'' +
                ", CN='" + CN + '\'' +
                ", _RRN='" + _RRN + '\'' +
                ", SN='" + SN + '\'' +
                ", TN='" + TN + '\'' +
                ", DT='" + DT + '\'' +
                ", AM='" + AM + '\'' +
                ", PS='" + PS + '\'' +
                ", CP='" + CP + '\'' +
                ", BR='" + BR + '\'' +
                ", MI='" + MI + '\'' +
                ", GI=" + Arrays.toString(GI) +
                ", SC='" + SC + '\'' +
                ", TS='" + TS + '\'' +
                ", TF='" + TF + '\'' +
                ", TO='" + TO + '\'' +
                ", ER='" + ER + '\'' +
                '}';
    }
}

