package com.yihui.hospitalguide.ac;

import android.content.Context;
import android.util.Log;

import com.amy.robot.actioncenter.lib.ActionCenterManager;
import com.yihui.hospitalguide.BuildConfig;


/**
 * Created by adward on 2017/11/23.
 */

public class ActionCenterLoader {
    private static final String TAG = ActionCenterLoader.class.getSimpleName();
    public static final String APP_SRC_URI = "content://com.amy.robot.hospitalguide.provider";

    public static void init(Context context) {
        ActionCenterManager.getInstance().init(context, APP_SRC_URI);

        //开启 debug
        ActionCenterManager.getInstance().setDebug(BuildConfig.DEBUG);
    }
}
