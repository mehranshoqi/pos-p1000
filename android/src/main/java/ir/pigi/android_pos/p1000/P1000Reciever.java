package ir.pigi.android_pos.p1000;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import androidx.databinding.ObservableField;

import ir.pigi.android_pos.PosResponseVM;

public class P1000Reciever extends BroadcastReceiver {

    private static ObservableField<PosResponseVM> _responseVm;
    @Override
    public void onReceive(Context context, Intent intent) {
        PosResponse response = new PosResponse();
        response.compName = intent.getStringExtra(PosResponse.CompanyName  );
        response.ER       = intent.getStringExtra(PosResponse.Error        );
        response.TO       = intent.getStringExtra(PosResponse.TimeOut      );
        response.SC       = intent.getStringExtra(PosResponse.SwipeCard    );
        response.BR       = intent.getStringExtra(PosResponse.BarCodeStatus);
        response.CP       = intent.getStringExtra(PosResponse.CheckPaper   );
        response.PS       = intent.getStringExtra(PosResponse.PrintStatus  );
        response.MI       = intent.getStringExtra(PosResponse.MifareResult );
        response.RS       = intent.getStringExtra(PosResponse.ResponseCode );
        response.GI       = intent.getStringArrayExtra(PosResponse.GetInfo );
        response.TS       = intent.getStringExtra(PosResponse.GetLastSaleSuccessfulTrx);
        response.TF       = intent.getStringExtra(PosResponse.GetLastSaleFailedTrx);

        //If the company Name is same with your company Name then pars other Value

        StringBuilder str = new StringBuilder("Hi I got your message : \n");
        str.append("compName : ").append(response.compName).append("\n");
        boolean showDigitalResult = true;
        if(response.ER != null) {
            PosResponseVM vm = new PosResponseVM();
            vm.status = 10010;
            vm.Message = response.ER;
            get_responseVm().set(vm);
        }
        else if(response.CP != null) {
            PosResponseVM vm = new PosResponseVM();
            if(!response.CP.contains("not")){

                vm.status = 10000;
                vm.Message = "Paper ok";
                get_responseVm().set(vm);
            }
            else
            {
                vm.status = 10001;
                vm.Message = "کاغذ چاپگر را چک نمایید";
                get_responseVm().set(vm);
            }
        }
        else if(response.TO != null) str.append("TimeOut : ").append(response.TO);
        else if(response.BR != null) {
            PosResponseVM vm = new PosResponseVM();
            vm.status = 10003;
            vm.Message = response.BR;
            get_responseVm().set(vm);
        }
        else if(response.CP != null) str.append("CheckPaper : ").append(response.CP);
        else if(response.PS != null) str.append("PrintStatus : ").append(response.PS);
        else if(response.GI != null)   {
            str     .append("MerchantInfo : \n")
                    .append("merchantName :").append(response.GI[0]).append("\n")
                    .append("merchantTell :").append(response.GI[1]).append("\n")
                    .append("terminalID :")  .append(response.GI[2]).append("\n")
                    .append("serialNumber :").append(response.GI[3]).append("\n")
                    .append("modelName :")   .append(response.GI[4]);
        }
        else if(response.MI != null) {

        }
        else if(response.RS != null) {
            str.append("ResponseCode : ").append(response.RS).append("\n");
            if(response.RS.equals("05")) {
//                String qrResult = intent.getStringExtra("QR_Result");
//                if(qrResult != null) {
//                    if(qrResult.equals("success")) {
//                        int qrAM = intent.getIntExtra("QR_AM", 0);
//                        response._RRN = intent.getStringExtra(PosResponse.RRN);
//                        response.SN = intent.getStringExtra(PosResponse.SerialNumber);
//                        response.DT = intent.getStringExtra(PosResponse.DateTime);
//                        if(qrAM == iTotalPay) {
//                            str.append("پرداخت QR موفق بوده است.").append("\n").append("شماره مرجع : ").append(response._RRN).append("\n").append("شماره سریال : ").append(response.SN).append("\n").append("تاریخ-زمان : ").append(response.DT).append("\n");
//                        }
//                        else str.append("عدم تطابق مبلغ پرداختی و مبلغ استعلام شده");
//                    }
//                    else if(qrResult.equals("failed")) str.append("پرداخت QR ناموفق بوده است.");
//                }
//                else str.append("Transaction Cancelled");
                PosResponseVM vm = new PosResponseVM();
                vm.status = 10020;
                vm.Message = "عملیات پرداخت کنسل شد";
                get_responseVm().set(vm);
            }
            else {
                response.trxType = intent.getStringExtra(PosResponse.TransactionType);
                response.CN      = intent.getStringExtra(PosResponse.CardNumber     );
                response._RRN    = intent.getStringExtra(PosResponse.RRN            );
                response.SN      = intent.getStringExtra(PosResponse.SerialNumber   );
                response.DT      = intent.getStringExtra(PosResponse.DateTime       );
                response.TN      = intent.getStringExtra(PosResponse.TerminalNumber );
                switch (response.trxType) {
                    case "Sale":
                    case "BillPayment":
                        response.AM      = intent.getStringExtra(PosResponse.Amount);
                        //MainActivity.RRN = response._RRN;
                        //MainActivity.RS  = response.RS;
                        break;
                }
                //str.append(response.toString());
                PosResponseVM vm = new PosResponseVM();
                vm.status = response.RS.equals("00")?0:10002;
                vm.Message = "پرداخت ناموفق";
                switch (response.RS){
                    case "00":
                        vm.status = 0;
                        vm.Message = "پرداخت موفق";
                        break;
                    default:
                        break;
                }
                vm.CardNo = response.CN;
                vm.RRN = response._RRN;
                vm.SerialNo = response.SN;
                vm.DateTime = response.DT;
                vm.TerminalNo = response.TN;
                get_responseVm().set(vm);
            }
        }
        else if(response.TS != null) {
//            if(response.TS.equals("SUCCESS")) {
//                response.CN   = intent.getStringExtra(PosResponse.CardNumber);
//                response._RRN = intent.getStringExtra(PosResponse.RRN);
//                response.AM   = intent.getStringExtra(PosResponse.Amount);
//                response.SN   = intent.getStringExtra(PosResponse.SerialNumber);
//                response.DT   = intent.getStringExtra(PosResponse.DateTime);
//                response.TN   = intent.getStringExtra(PosResponse.TerminalNumber);
//                MainActivity.RRN = response._RRN;
//                MainActivity.RS  = "00";
//                str.append(response.toString());
//            }
        }
        else if(response.TF != null) {
//            if(response.TF.equals("SUCCESS")) {
//                response.CN   = intent.getStringExtra(Response.CardNumber);
//                response._RRN = intent.getStringExtra(Response.RRN);
//                response.AM   = intent.getStringExtra(Response.Amount);
//                response.SN   = intent.getStringExtra(Response.SerialNumber);
//                response.DT   = intent.getStringExtra(Response.DateTime);
//                response.TN   = intent.getStringExtra(Response.TerminalNumber);
//                MainActivity.RRN = response._RRN;
//                MainActivity.RS  = "99";
//                str.append(response.toString());
//            }
        }
        Log.e("AAAAAAAA", "******** response : " + response.toString());
        if(showDigitalResult) {
            //MainActivity.sc.setVisibility(View.GONE);
            //MainActivity.txt.setText(str.toString());
            //MainActivity.scTxt.setVisibility(View.VISIBLE);
        }
    }

    public static ObservableField<PosResponseVM> get_responseVm(){
        if(_responseVm == null)
            _responseVm = new ObservableField<>();
        return _responseVm;
    }
}