import 'dart:convert';

class PosRespModel{
  int? status;
  String? Message;
  String? ResponseCode;
  String? RRN;
  String? CardNo;
  String? DateTime;
  String? SerialNo;
  String? TerminalNo;

  PosRespModel({this.status,this.Message,this.ResponseCode,this.RRN,this.CardNo,this.DateTime,this.SerialNo,this.TerminalNo});
  factory PosRespModel.fromJson(String data){
    Map<String, dynamic> parsed = json.decode(data);
  print(parsed);
    return PosRespModel(
      CardNo:  parsed['CardNo'],
      DateTime: parsed['DateTime'],
      Message: parsed['Message'],
      ResponseCode: parsed['ResponseCode'],
      RRN: parsed['RRN'],
      SerialNo: parsed['SerialNo'],
      status: parsed['status'],
      TerminalNo: parsed['TerminalNo']
    );
  }
}