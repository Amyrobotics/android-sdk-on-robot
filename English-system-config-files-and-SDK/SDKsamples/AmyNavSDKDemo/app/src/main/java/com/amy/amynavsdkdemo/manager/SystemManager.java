package com.amy.amynavsdkdemo.manager;

import com.amy.robot.actioncenter.api.system.SystemActionCenterApiImp;
import com.amy.robot.actioncenter.api.utils.SpeechRecognitionCallBack;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;

/**
 * @author Async_wu
 * @date 2019-3-20 20:13:06
 * 系统API
 * system api
 */
public class SystemManager {
    private static SystemManager instance;
    private SystemManager(){}
    private static SystemActionCenterApiImp systemActionCenterApiImp;
    public static SystemManager getInstance() {
        if(instance==null){
            synchronized (SystemManager.class){
                if(instance==null){
                    instance = new SystemManager();
                    systemActionCenterApiImp = new SystemActionCenterApiImp();
                }
            }
        }
        return instance;
    }

    /**
     * 开始播放语音
     * Play voice
     * @param url 音乐地址
     *              Music address
     * @param callBack 回调
     */
    public void playMusic(String url, ActionNotifyCallBack callBack){
        systemActionCenterApiImp.playMusic(url,callBack);
    }

    /**
     * 停止播放音乐
     * Music Stop
     */
    public void stopMusic(){
        systemActionCenterApiImp.stopMusic();
    }

    /**
     * 重新开始播放音乐
     * Start playing music again
     */
    public void resumeMusic(){
        systemActionCenterApiImp.resumeMusic();
    }

    /**
     * 暂停播放音乐
     * pause music
     */
    public void pasueMusic(){
        systemActionCenterApiImp.pauseMusic();
    }

    /**
     * 开始播报
     * start talking
     * @param words  播报内容
     *               talking content
     * @param speakMode 播报类型
     *                  Broadcast type
     * @param callBack 回调
     */
    public void startSpeak(String words,int speakMode,ActionNotifyCallBack callBack){
        systemActionCenterApiImp.speakStart(words,speakMode,callBack);
    }

    /**
     * 停止播报
     * End to talk
     */
    public void stopSpeak(){
        systemActionCenterApiImp.speakStop();
    }

    /**
     * 继续播报
     * Continue to talking
     */
    public void resumeSpeak(){
        systemActionCenterApiImp.speakResume();
    }

    /**
     * 暂停播报
     * stop talking
     */
    public void pasueSpeak(){
        systemActionCenterApiImp.speakPause();
    }

    /**
     * 开始语音识别
     * Start Speech Recognition
     * @param speechRecognitionCallBack 回调
     */
    public void startRecognition(SpeechRecognitionCallBack speechRecognitionCallBack){
        systemActionCenterApiImp.speechRecognitionStart(speechRecognitionCallBack);
    }

    /**
     * 结束语音识别
     * End speech recognition
     */
    public void stopRecognition(){
        systemActionCenterApiImp.speechRecognitionStop();
    }



}
