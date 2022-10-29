import 'dart:convert';

import 'package:android_pos/models/pos_resp_p1000_model.dart';
import 'package:flutter/material.dart';
import 'dart:async';

import 'package:flutter/services.dart';
import 'package:android_pos/android_pos.dart';
import 'package:image_picker/image_picker.dart';

void main() { 
  runApp(const MyApp());
}

class MyApp extends StatefulWidget {
  const MyApp({super.key});

  @override
  State<MyApp> createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  String _platformVersion = 'Unknown';
  final _androidPosPlugin = AndroidPos();
  PosRespModel? _model;

  @override
  void initState() {
    super.initState();
    initPlatformState();
  }

  // Platform messages are asynchronous, so we initialize in an async method.
  Future<void> initPlatformState() async {
    String platformVersion;
    // Platform messages may fail, so we use a try/catch PlatformException.
    // We also handle the message potentially returning null.
    try {
      platformVersion = await _androidPosPlugin.getPlatformVersion() ??
          'Unknown platform version';
    } on PlatformException {
      platformVersion = 'Failed to get platform version.';
    }

    // If the widget was removed from the tree while the asynchronous platform
    // message was in flight, we want to discard the reply rather than calling
    // setState to update our non-existent appearance.
    if (!mounted) return;

    setState(() {
      _platformVersion = platformVersion;
    });
  }

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: Scaffold(
        appBar: AppBar(
          title: const Text('Plugin example app'),
        ),
        body: Center(
            child: Column(
          crossAxisAlignment: CrossAxisAlignment.start,
          children: [
            Text('Running on: $_platformVersion\n'),
            ElevatedButton(
                onPressed: () async => await _androidPosPlugin
                        .startPaymentTxn("test", "14000", (model) {
                      setState(() {
                        _model = model;
                      });
                    }),
                child: const Text("Pay 4")),
            ElevatedButton(
                onPressed: () async =>
                    await _androidPosPlugin.checkPaper("test", (model) {
                      setState(() {
                        _model = model;
                      });
                    }),
                child: const Text("Check paper ")),
            ElevatedButton(
                onPressed: () async =>
                    await _androidPosPlugin.startCamera("test", (model) {
                      setState(() {
                        _model = model;
                      });
                    }),
                child: const Text("Start Scanner ")),
            ElevatedButton(
                onPressed: () async {
                  final ImagePicker _picker = ImagePicker();
                  final XFile? image =
                      await _picker.pickImage(source: ImageSource.gallery);
                  var list = await image?.readAsBytes();
                  String base64Image = base64Encode(list!);
                  await _androidPosPlugin.printBitmap(base64Image, "test");
                },
                child: const Text("Print Image ")),
            const SizedBox(height: 25),
            Text("Message: ${_model?.Message}"),
            Text("status: ${_model?.status}"),
            Text("RRN: ${_model?.RRN}"),
            Text("Date: ${_model?.DateTime}"),
            Text("ResponseCode: ${_model?.ResponseCode}"),
            Text("CardNo: ${_model?.CardNo}"),
            Text("TerminalNo: ${_model?.TerminalNo}"),
          ],
        )),
      ),
    );
  }
}
