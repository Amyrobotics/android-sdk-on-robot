package com.yihui.hospitalguide.manager;

import android.util.Log;

import com.amy.robot.actioncenter.api.system.SystemActionCenterApiImp;
import com.amy.robot.actioncenter.api.system.SystemVoiceActionNames;
import com.amy.robot.actioncenter.api.utils.SpeechRecognitionCallBack;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.yihui.hospitalguide.HospitalGuideApp;
import com.yihui.hospitalguide.activity.BaseActivity;

public class VoiceManager {
    private static final String TAG = "VoiceManager";

    private BaseActivity topActivity;

    private static VoiceManager mVoiceManager = null;
    private static Object lock = new Object();
    private static SystemActionCenterApiImp mSystemActionCenterApi;

    private VoiceManager() {
        mSystemActionCenterApi = new SystemActionCenterApiImp();
    }

    public static VoiceManager getInstance() {
        synchronized (lock) {
            if (mVoiceManager == null) {
                mVoiceManager = new VoiceManager();
            }
        }

        mSystemActionCenterApi.speechRecognitionStart(new SpeechRecognitionCallBack() {
            @Override
            public void onSuccess(String recognizerType, String result) {
                Log.e(TAG, result);
                switch (result) {
                    case "关闭":
                        HospitalGuideApp.getInstance().exitApp();
                        break;
                    case "别说了":
                    case "停止播报":
                    case "停止":
                    case "闭嘴":
                        mSystemActionCenterApi.speakStop();
                        break;
                    default:
                        VoiceManager.getInstance().getTopActivity().disposeVoice(result);
                        break;
                }
            }

            @Override
            public void onFailed(String s, int i, String s1) {

            }
        });
        Log.e(TAG, "startVoiceRecognition");

        return mVoiceManager;
    }

    public BaseActivity getTopActivity() {
        return topActivity;
    }

    public void setTopActivity(BaseActivity topActivity) {
        this.topActivity = topActivity;
    }

    //开始语音播报
    public void speakStart(String content) {
        speakStart(content, null);
    }

    //开始语音播报，带结束回调
    public void speakStart(String content, ActionNotifyCallBack callBack) {
        int mode = SystemVoiceActionNames.SpeakMode.MODE_HIDE_FACE;
        mSystemActionCenterApi.speakStart(content, mode, callBack);
    }

    //停止语音播报
    public void speakStop() {
        mSystemActionCenterApi.speakStop();
    }

    //暂停播报
    public void speakPause() {
        mSystemActionCenterApi.speakPause();
    }

    //恢复播报
    public void speakResume() {
        mSystemActionCenterApi.speakResume();
    }

    //语音识别
    public void speechRecognitionStart(SpeechRecognitionCallBack speechCallBack) {
        mSystemActionCenterApi.speechRecognitionStart(speechCallBack);
    }

    //结束语音识别
    public void speechRecognitionStop() {
        mSystemActionCenterApi.speechRecognitionStop();
    }
}
