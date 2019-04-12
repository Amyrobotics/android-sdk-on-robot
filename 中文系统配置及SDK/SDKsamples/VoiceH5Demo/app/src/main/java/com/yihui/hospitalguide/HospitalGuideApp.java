package com.yihui.hospitalguide;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.yihui.hospitalguide.ac.ActionCenterLoader;
import com.yihui.hospitalguide.activity.BaseActivity;
import com.yihui.hospitalguide.manager.VoiceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/11/6.
 */

public class HospitalGuideApp extends Application {

    private static HospitalGuideApp mInstance;

    private List<BaseActivity> activities = new ArrayList<>();

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        ActionCenterLoader.init(this);
    }

    public static HospitalGuideApp getInstance() {
        return mInstance;
    }

    public void exitApp() {
        for (BaseActivity activity : activities) {
            if (activity != null) {
                activity.finish();
            }
        }
        //释放内存，退出程序
        VoiceManager.getInstance().speechRecognitionStop();
        //杀死本进程（实测在A1上将导致语音桌面程序被回收，暂时注释）
//        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void addActivity(@NonNull BaseActivity activity) {
        activities.add(activity);
    }

}
