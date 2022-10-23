// import 'package:flutter_test/flutter_test.dart';
// import 'package:android_pos/android_pos.dart';
// import 'package:android_pos/android_pos_platform_interface.dart';
// import 'package:android_pos/android_pos_method_channel.dart';
// import 'package:plugin_platform_interface/plugin_platform_interface.dart';

// class MockAndroidPosPlatform
//     with MockPlatformInterfaceMixin
//     implements AndroidPosPlatform {

//   @override
//   Future<String?> getPlatformVersion() => Future.value('42');
// }

// void main() {
//   final AndroidPosPlatform initialPlatform = AndroidPosPlatform.instance;

//   test('$MethodChannelAndroidPos is the default instance', () {
//     expect(initialPlatform, isInstanceOf<MethodChannelAndroidPos>());
//   });

//   test('getPlatformVersion', () async {
//     AndroidPos androidPosPlugin = AndroidPos();
//     MockAndroidPosPlatform fakePlatform = MockAndroidPosPlatform();
//     AndroidPosPlatform.instance = fakePlatform;

//     expect(await androidPosPlugin.getPlatformVersion(), '42');
//   });
// }
