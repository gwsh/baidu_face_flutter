/*
 * Copyright (C) 2017 Baidu, Inc. All Rights Reserved.
 */
package com.fluttify.baidu_face_flutter;

import android.content.Intent;

import com.baidu.idl.face.platform.FaceStatusEnum;
import com.fluttify.baidu_face_flutter.platform.ui.FaceLivenessActivity;

import java.util.HashMap;

public class OfflineFaceLivenessActivity extends FaceLivenessActivity {

    @Override
    public void onLivenessCompletion(FaceStatusEnum status, String message, HashMap<String, String> base64ImageMap) {
        super.onLivenessCompletion(status, message, base64ImageMap);
        if (status == FaceStatusEnum.OK && mIsCompletion) {
            final Intent resultIntent = new Intent();
            resultIntent.putExtra("bestImage", base64ImageMap.get("bestImage0"));
            setResult(RESULT_OK, resultIntent);
            finish();
        } else if (status == FaceStatusEnum.Error_DetectTimeout ||
                status == FaceStatusEnum.Error_LivenessTimeout ||
                status == FaceStatusEnum.Error_Timeout) {
            finish();
        }
    }
}