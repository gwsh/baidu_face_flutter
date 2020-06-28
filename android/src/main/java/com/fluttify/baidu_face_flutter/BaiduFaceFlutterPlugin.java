package com.fluttify.baidu_face_flutter;

import android.app.Activity;
import android.content.Intent;

import com.baidu.idl.face.platform.FaceConfig;
import com.baidu.idl.face.platform.FaceSDKManager;
import com.baidu.idl.face.platform.LivenessTypeEnum;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import androidx.annotation.NonNull;
import io.flutter.embedding.engine.plugins.FlutterPlugin;
import io.flutter.embedding.engine.plugins.activity.ActivityAware;
import io.flutter.embedding.engine.plugins.activity.ActivityPluginBinding;
import io.flutter.plugin.common.MethodCall;
import io.flutter.plugin.common.MethodChannel;
import io.flutter.plugin.common.MethodChannel.MethodCallHandler;
import io.flutter.plugin.common.MethodChannel.Result;
import io.flutter.plugin.common.PluginRegistry;
import io.flutter.plugin.common.PluginRegistry.Registrar;

/**
 * BaiduFaceFlutterPlugin
 */
public class BaiduFaceFlutterPlugin implements FlutterPlugin, MethodCallHandler, ActivityAware {
    private static final int LIVENESS_REQ_CODE = 996;

    private MethodChannel channel;
    private Activity activity;

    private Result livenessResult;

    @Override
    public void onAttachedToEngine(@NonNull FlutterPluginBinding flutterPluginBinding) {
        channel = new MethodChannel(flutterPluginBinding.getBinaryMessenger(), "com.fluttify/baidu_face");
        channel.setMethodCallHandler(this);
    }

    public static void registerWith(Registrar registrar) {
        final MethodChannel channel = new MethodChannel(registrar.messenger(), "com.fluttify/baidu_face");
        channel.setMethodCallHandler(new BaiduFaceFlutterPlugin());
    }

    @Override
    public void onMethodCall(@NonNull MethodCall call, @NonNull Result result) {
        final Map<String, Object> args = (Map<String, Object>) call.arguments;
        switch (call.method) {
            case "init":
                final String licenseID = (String) args.get("licenseID");
                final List<Integer> livenessTypeList = (List<Integer>) args.get("livenessTypeList");
                final Boolean livenessRandom = (Boolean) args.get("livenessRandom");
                final Double blurnessValue = (Double) args.get("blurnessValue");
                final Double brightnessValue = (Double) args.get("brightnessValue");
                final Integer cropFaceValue = (Integer) args.get("cropFaceValue");
                final Integer headPitchValue = (Integer) args.get("headPitchValue");
                final Integer headRollValue = (Integer) args.get("headRollValue");
                final Integer headYawValue = (Integer) args.get("headYawValue");
                final Integer minFaceSize = (Integer) args.get("minFaceSize");
                final Double notFaceSize = (Double) args.get("notFaceSize");
                final Boolean checkFaceQuality = (Boolean) args.get("checkFaceQuality");
                final Double occlusionValue = (Double) args.get("occlusionValue");
                final Boolean sound = (Boolean) args.get("sound");
                // 第一个参数 应用上下文
                // 第二个参数 licenseID license申请界面查看
                // 第三个参数 assets目录下的License文件名
                FaceSDKManager.getInstance().initialize(activity, licenseID, "idl-license.face-android");

                FaceConfig config = FaceSDKManager.getInstance().getFaceConfig();

                if (livenessTypeList != null) {
                    List<LivenessTypeEnum> livenessTypeEnums = new ArrayList<>();
                    for (Integer item : livenessTypeList) {
                        if (item == 0) {
                            livenessTypeEnums.add(LivenessTypeEnum.Eye);
                        } else if (item == 1) {
                            livenessTypeEnums.add(LivenessTypeEnum.Mouth);
                        } else if (item == 2) {
                            livenessTypeEnums.add(LivenessTypeEnum.HeadRight);
                        } else if (item == 3) {
                            livenessTypeEnums.add(LivenessTypeEnum.HeadLeft);
                        } else if (item == 4) {
                            livenessTypeEnums.add(LivenessTypeEnum.HeadUp);
                        } else if (item == 5) {
                            livenessTypeEnums.add(LivenessTypeEnum.HeadDown);
                        } else if (item == 6) {
                            livenessTypeEnums.add(LivenessTypeEnum.HeadLeftOrRight);
                        }
                    }
                    config.setLivenessTypeList(livenessTypeEnums);
                }
                if (livenessRandom != null) config.setLivenessRandom(livenessRandom);
                if (blurnessValue != null) config.setBlurnessValue(blurnessValue.floatValue());
                if (brightnessValue != null)
                    config.setBrightnessValue(brightnessValue.floatValue());
                if (cropFaceValue != null) config.setCropFaceValue(cropFaceValue);
                if (headPitchValue != null) config.setHeadPitchValue(headPitchValue);
                if (headRollValue != null) config.setHeadRollValue(headRollValue);
                if (headYawValue != null) config.setHeadYawValue(headYawValue);
                if (minFaceSize != null) config.setMinFaceSize(minFaceSize);
                if (notFaceSize != null) config.setNotFaceValue(notFaceSize.floatValue());
                if (checkFaceQuality != null) config.setCheckFaceQuality(checkFaceQuality);
                if (occlusionValue != null) config.setOcclusionValue(occlusionValue.floatValue());
                if (sound != null) config.setSound(sound);

                FaceSDKManager.getInstance().setFaceConfig(config);

                result.success("success");
                break;
            case "liveDetect":
                livenessResult = result;
                Intent faceLivenessIntent = new Intent(activity, OfflineFaceLivenessActivity.class);
                activity.startActivityForResult(faceLivenessIntent, LIVENESS_REQ_CODE);
                break;
            default:
                result.notImplemented();
                break;
        }
    }

    @Override
    public void onDetachedFromEngine(@NonNull FlutterPluginBinding binding) {
        channel.setMethodCallHandler(null);
    }

    @Override
    public void onAttachedToActivity(@NonNull ActivityPluginBinding binding) {
        activity = binding.getActivity();
        binding.addActivityResultListener(new PluginRegistry.ActivityResultListener() {
            @Override
            public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
                if (requestCode == LIVENESS_REQ_CODE) {
                    if (livenessResult != null) {
                        if (resultCode == Activity.RESULT_OK) {
                            livenessResult.success(data.getStringExtra("bestImage"));
                        } else {
                            livenessResult.error("识别失败", "识别失败", "识别失败");
                        }
                        livenessResult = null;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void onDetachedFromActivityForConfigChanges() {
        activity = null;
    }

    @Override
    public void onReattachedToActivityForConfigChanges(@NonNull ActivityPluginBinding binding) {
        activity = binding.getActivity();
    }

    @Override
    public void onDetachedFromActivity() {
        activity = null;
    }
}
