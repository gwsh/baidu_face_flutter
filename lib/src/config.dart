import 'enums.dart';

class Config {
  /// 活体动作
  final List<LivenessType> livenessTypeList;

  /// 动作是否随机
  final bool livenessRandom;

  /// 模糊度范围 0-1 推荐小于0.7
  final double blurnessValue;

  /// 光照范围 0-1 推荐大于40(?官方文档是这么写的)
  final double brightnessValue;

  /// 剪裁人脸大小
  final int cropFaceValue;

  /// 人脸 ya, pitch, row角度范围 -45-45 推荐 -15-15
  final int headPitchValue;
  final int headRollValue;
  final int headYawValue;

  /// 最小检测人脸 (在图片人脸能够被检测到最小值) 80-200 越小越耗性能, 推荐120-200
  final int minFaceSize;
  final double notFaceSize;

  /// 是否进行质量检测
  final bool checkFaceQuality;

  /// 人脸遮挡范围 0-1 推荐小于0.5
  final double occlusionValue;

  /// 是否开启提示音
  final bool sound;

  const Config({
    this.livenessTypeList,
    this.livenessRandom,
    this.blurnessValue,
    this.brightnessValue,
    this.cropFaceValue,
    this.headPitchValue,
    this.headRollValue,
    this.headYawValue,
    this.minFaceSize,
    this.notFaceSize,
    this.checkFaceQuality,
    this.occlusionValue,
    this.sound,
  });

  @override
  String toString() {
    return 'Config{livenessTypeList: $livenessTypeList, livenessRandom: $livenessRandom, blurnessValue: $blurnessValue, brightnessValue: $brightnessValue, cropFaceValue: $cropFaceValue, headPitchValue: $headPitchValue, headRollValue: $headRollValue, headYawValue: $headYawValue, minFaceSize: $minFaceSize, notFaceSize: $notFaceSize, checkFaceQuality: $checkFaceQuality, occlusionValue: $occlusionValue, sound: $sound}';
  }
}
