import 'dart:convert';
import 'dart:io';

import 'package:baidu_face_flutter/baidu_face_flutter.dart';
import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:permission_handler/permission_handler.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();
  // 人脸识别初始化
  final config = Config(livenessTypeList: [LivenessType.Eye]);
  if (Platform.isAndroid) {
    await BaiduFace.instance.init('gldcircle-face-android', config: config);
  } else if (Platform.isIOS) {
    await BaiduFace.instance.init('gldcircle-face-ios', config: config);
  }
  runApp(MyApp());
}

class MyApp extends StatefulWidget {
  @override
  _MyAppState createState() => _MyAppState();
}

class _MyAppState extends State<MyApp> {
  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      home: HomeScreen(),
    );
  }
}

class HomeScreen extends StatelessWidget {
  const HomeScreen({
    Key key,
  }) : super(key: key);

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text('Plugin example app'),
      ),
      body: Center(
        child: FlatButton(
          onPressed: () => _handleVerify(context),
          child: Text('开始验证'),
        ),
      ),
    );
  }

  void _handleVerify(BuildContext context) async {
    await Permission.camera.request();
    try {
      final base64Image = await BaiduFace.instance.liveDetect();
      showDialog(
        context: context,
        barrierDismissible: true,
        builder: (context) => Dialog(
          child: Image.memory(base64Decode(base64Image)),
        ),
      );
    } on PlatformException catch (e) {
      debugPrint(e.message);
    }
  }
}
