package com.amy.companyinfomation.service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;

import com.amy.companyinfomation.CompanyInfoApp;
import com.amy.companyinfomation.manager.VoiceManager;
import com.amy.robot.actioncenter.api.utils.SpeechRecognitionCallBack;
import com.amy.robot.actioncenter.common.bean.ActionRequest;
import com.amy.robot.actioncenter.common.constant.ActionCode;
import com.amy.robot.actioncenter.lib.ActionCenterManager;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;

/**
 * Created by Administrator on 2018/9/25.
 */

public class VoiceService extends Service {
    public static final String TAG = "VoiceService";

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        Log.e(TAG, "CompanyInformatin VoiceService Create");
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.e(TAG, "CompanyInformatin VoiceService Start");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "CompanyInformatin VoiceService onStartCommand");
        VoiceManager.getInstance().speechRecognitionStart(new SpeechRecognitionCallBack() {
            @Override
            public void onSuccess(String recognizerType, String result) {
                Log.e(TAG, result);
                switch (result) {
                    case "关闭":
                        CompanyInfoApp.getInstance().exitApp();
                        break;
                    case "别说了":
                    case "停止播报":
                    case "闭嘴":
                        VoiceManager.getInstance().speakStop();
                        break;
                }
            }

            @Override
            public void onFailed(String s, int i, String s1) {

            }
        });
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        VoiceManager.getInstance().speechRecognitionStop();
        super.onDestroy();
        Log.e(TAG, "CompanyInformatin VoiceService Destroy");
    }
}
