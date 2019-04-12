package com.amy.amynavsdkdemo.activity;

import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amy.amynavsdkdemo.R;
import com.amy.amynavsdkdemo.base.BaseActivity;
import com.amy.amynavsdkdemo.manager.SystemManager;
import com.amy.robot.actioncenter.api.utils.SpeechRecognitionCallBack;
import com.amy.robot.actioncenter.common.bean.ActionRequest;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;
import com.blankj.utilcode.util.LogUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * @author Async_wu
 * @date 2019-3-22 14:10:35
 * 机器人语音播报、语音识别
 * Robot voice broadcast, voice recognition
 */
public class RobotActionActivity extends BaseActivity {
    ActionBar actionBar;
    @BindView(R.id.btn_speakStart)
    Button startSpeakButton;
    @BindView(R.id.btn_speakStop)
    Button stopSpeakButton;
    @BindView(R.id.ed_speakWords)
    EditText speakWordEditText;
    @BindView(R.id.btn_startRecogintion)
    Button startRecoButton;
    @BindView(R.id.btn_stopRecogintion)
    Button stopRecoButton;
    @BindView(R.id.tv_speechWords)
    TextView speechWordsTextView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_robotaction;
    }
    String url = "http://music.taihe.com/song/629954";

    @Override
    protected void initView() {
        actionBar = this.getSupportActionBar();
        actionBar.setTitle(R.string.btn_robotAction);
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void initEvent() {

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @OnClick({R.id.btn_speakStart,R.id.btn_speakStop})
    public void speakClick(View view){
        switch (view.getId()){
            case R.id.btn_speakStart:
                speakStart();
                break;
            case R.id.btn_speakStop:
                speakStop();
                break;
                default:
                    break;
        }
    }

    @OnClick({R.id.btn_startRecogintion,R.id.btn_stopRecogintion,R.id.btn_speakResume,R.id.btn_speakPause})
    public void speech(View view){
        switch (view.getId()){
            case R.id.btn_startRecogintion:
                startSpeechReco();
                break;
            case R.id.btn_stopRecogintion:
                stopSpeechReco();
                break;
            case R.id.btn_speakPause:
                speakPause();
                break;
            case R.id.btn_speakResume:
                speakResume();
                break;
                default:
                    break;
        }
    }

    /**
     * 开始语音识别
     * Start Speech Recognition
     */
    public void startSpeechReco(){
        SystemManager.getInstance().startRecognition(new SpeechRecognitionCallBack() {
            @Override
            public void onSuccess(String s, String s1) {
                speechWordsTextView.setText(s1);
            }

            @Override
            public void onFailed(String s, int i, String s1) {
                speechWordsTextView.setText(getResources().getString(R.string.desc_recoFail));
            }
        });
    }

    /**
     * 停止语音识别
     * Stop speech recognition
     */
    public void stopSpeechReco(){
        SystemManager.getInstance().stopRecognition();
    }

    /**
     * 开始播报
     * Start talking
     */
    public void speakStart(){
        String words = speakWordEditText.getText().toString();
        SystemManager.getInstance().startSpeak(words, 1, new ActionNotifyCallBack() {
            @Override
            public void onRecvActionNotify(ActionRequest actionRequest, String s, String s1, int i, String s2, String s3) {
                LogUtils.e("播报反馈 "+s+" s1 "+s1+" i "+i+" s2 "+s2+" s3 "+s3);
            }
        });
    }

    /**
     * 停止播报
     * Stop talking
     */
    public void speakStop(){
        SystemManager.getInstance().stopSpeak();
    }

    /**
     * 继续播报
     * Continue to talking
     */
    public void speakResume(){
        SystemManager.getInstance().resumeSpeak();
    }
    /**
     * 暂停播报
     * stop talking
     */
    public void speakPause(){
        SystemManager.getInstance().pasueSpeak();
    }

}
