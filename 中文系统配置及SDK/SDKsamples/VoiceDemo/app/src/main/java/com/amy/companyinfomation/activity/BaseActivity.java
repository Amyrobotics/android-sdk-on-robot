package com.amy.companyinfomation.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.amy.companyinfomation.CompanyInfoApp;
import com.amy.companyinfomation.R;
import com.amy.companyinfomation.manager.VoiceManager;

/**
 * Created by Administrator on 2018/9/26.
 */

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onResume() {
        super.onResume();

//        View decorView = getWindow().getDecorView();
//        decorView.setSystemUiVisibility(
//                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
//                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
//                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION // hide nav bar
//                        | View.SYSTEM_UI_FLAG_FULLSCREEN // hide status bar
//                        | View.SYSTEM_UI_FLAG_IMMERSIVE);

//        CompanyInfoApp.getInstance().startVoiceService();
        VoiceManager.getInstance().setTopActivity(this);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CompanyInfoApp.getInstance().addActivity(this);
    }

    public synchronized void disposeVoice(String voiceFont) {
        switch (voiceFont) {
            case "后退":
            case "返回":
                this.finish();
                break;
        }
    }
}
