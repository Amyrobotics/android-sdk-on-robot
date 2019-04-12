package com.amy.companyinfomation.ac;

import android.content.Context;

import com.amy.companyinfomation.BuildConfig;
import com.amy.robot.actioncenter.lib.ActionCenterManager;

public class ActionCenterLoader {
    private static final String TAG = ActionCenterLoader.class.getSimpleName();
    private static final String APP_SRC_URI = "content://com.amy.robot.companyinformation.provider";

    public static void init(Context context) {
        ActionCenterManager.getInstance().init(context, APP_SRC_URI);
        //开启 debug
        ActionCenterManager.getInstance().setDebug(BuildConfig.DEBUG);
    }
}
