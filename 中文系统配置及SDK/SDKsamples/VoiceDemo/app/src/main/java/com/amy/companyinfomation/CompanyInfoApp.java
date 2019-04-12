package com.amy.companyinfomation;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import com.amy.companyinfomation.ac.ActionCenterLoader;
import com.amy.companyinfomation.activity.BaseActivity;
import com.amy.companyinfomation.manager.VoiceManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/9/20.
 */

public class CompanyInfoApp extends Application {
    private static CompanyInfoApp mInstance;

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

//    public void startVoiceService() {
//        mInstance.startService(new Intent(mInstance, VoiceService.class));
//    }
//
//    public void stopVoiceService() {
//        mInstance.stopService(new Intent(mInstance, VoiceService.class));
//    }

    public static CompanyInfoApp getInstance() {
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
