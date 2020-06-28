import 'package:baidu_face_flutter/src/config.dart';
import 'package:flutter/services.dart';

class BaiduFace {
  static BaiduFace instance = BaiduFace._();

  BaiduFace._();

  final _channel = MethodChannel('com.fluttify/baidu_face');

  /// 初始化
  ///
  /// 关于license请参考 iOS: https://ai.baidu.com/ai-doc/FACE/ok37c1ped Android: https://ai.baidu.com/ai-doc/FACE/sk37c1p6e
  /// [config] 人脸识别参数
  Future<void> init(String licenseID, {Config config = const Config()}) async {
    _channel.invokeMethod(
      'init',
      {
        'licenseID': licenseID,
        'livenessTypeList':
            config.livenessTypeList?.map((e) => e.index)?.toList(),
        'livenessRandom': config.livenessRandom,
        'blurnessValue': config.blurnessValue,
        'brightnessValue': config.brightnessValue,
        'cropFaceValue': config.cropFaceValue,
        'headPitchValue': config.headPitchValue,
        'headRollValue': config.headRollValue,
        'headYawValue': config.headYawValue,
        'minFaceSize': config.minFaceSize,
        'notFaceSize': config.notFaceSize,
        'checkFaceQuality': config.checkFaceQuality,
        'occlusionValue': config.occlusionValue,
        'sound': config.sound,
      },
    );
  }

  /// 开始活体检测
  ///
  /// 以base64字符串形式返回人脸数据
  Future<String> liveDetect() async {
    final String base64Image = await _channel.invokeMethod('liveDetect');
    return base64Image.replaceAll(new RegExp('\\s*|\t|\r|\n'), "").trim();
  }
}
