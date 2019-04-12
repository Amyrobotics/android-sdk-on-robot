package com.amyrobotics.action.test.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.amy.robot.actioncenter.api.system.SystemActionCenterApiImp;
import com.amy.robot.actioncenter.api.utils.SpeechRecognitionCallBack;
import com.amy.robot.actioncenter.common.bean.ActionRequest;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.amy.robot.actioncenter.lib.ActionResultCallback;
import com.amyrobotics.action.test.R;
import com.amyrobotics.action.test.utils.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoiceActivity extends BaseActivity {
    @BindView(R.id.btn_start_speak)
    Button btn_start_speak;
    @BindView(R.id.btn_stop_speak)
    Button btn_stop_speak;
    @BindView(R.id.btn_pause_speak)
    Button btn_pause_speak;
    @BindView(R.id.btn_resume_speak)
    Button btn_resume_speak;
    @BindView(R.id.btn_start_listen)
    Button btn_start_listen;
    @BindView(R.id.btn_stop_listen)
    Button btn_stop_listen;
    @BindView(R.id.tv_listen_text)
    TextView tv_listen_text;

    private SystemActionCenterApiImp systemActionCenterApiImp;
    private static final String TAG = "VoiceActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voice);
        ButterKnife.bind(this);
        initView();
    }

    private void initView() {
        systemActionCenterApiImp = new SystemActionCenterApiImp();
    }

    @OnClick({
            R.id.btn_start_speak,
            R.id.btn_stop_speak,
            R.id.btn_pause_speak,
            R.id.btn_resume_speak,
            R.id.btn_start_listen,
            R.id.btn_stop_listen,
    })
    public void onViewClick(View v) {
        switch (v.getId()) {
            case R.id.btn_start_speak:
                startSpeak();
                break;
            case R.id.btn_stop_speak:
                stopSpeak();
                break;
            case R.id.btn_pause_speak:
                pauseSpeak();
                break;
            case R.id.btn_resume_speak:
                resumeSpeak();
                break;
            case R.id.btn_start_listen:
                startListen();
                break;
            case R.id.btn_stop_listen:
                stopListen();
                break;
        }

    }

    private void stopListen() {
        systemActionCenterApiImp.speechRecognitionStop();
    }

    private void startListen() {
        //程序关闭时要关闭监听（when exit app, please invoke speechRecognitionStop function）
        systemActionCenterApiImp.speechRecognitionStart(new SpeechRecognitionCallBack() {
            @Override
            public void onSuccess(String s, final String s1) {
                LogUtils.i(TAG, "onSuccess: result： " + s1);
                switch (s1) {
                    case "返回"://根据实际情况进行处理(Processing according to the actual situation)
                    case "退出"://根据实际情况进行处理(Processing according to the actual situation)
                    case "停止"://根据实际情况进行处理(Processing according to the actual situation)
                        systemActionCenterApiImp.speechRecognitionStop();
                        finish();
                        break;
                }
                VoiceActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tv_listen_text.setText(getString(R.string.recog_text) + s1);
                    }
                });
            }

            @Override
            public void onFailed(String s, int i, String s1) {
                LogUtils.i(TAG, "onFailed: errorcode: " + i + " errorInfo: " + s1);
            }
        });
    }

    private void resumeSpeak() {
        systemActionCenterApiImp.speakResume();
    }

    private void pauseSpeak() {
        systemActionCenterApiImp.speakPause();
    }

    private void stopSpeak() {
        systemActionCenterApiImp.speakStop();
    }

    private void startSpeak() {
        systemActionCenterApiImp.speakStart(getString(R.string.test_text), 0, new ActionNotifyCallBack() {
            @Override
            public void onRecvActionNotify(ActionRequest notifyRequest,
                                           String srcURI, String notifyAction, final int notifyCode,
                                           final String notifyInfo, String notifyParams) {
                switch (notifyInfo) {
                    case "complete":
                        LogUtils.i(TAG, "onRecvActionNotify: speak done");
                        break;
                    case "timeout":
                        LogUtils.i(TAG, "onRecvActionNotify: init fail");
                        break;
                }


            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        //如果开启监听必须要关闭（when exit app, please invoke speechRecognitionStop function）
        systemActionCenterApiImp.speechRecognitionStop();
    }
}
