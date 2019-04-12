package com.amyrobotics.action.test.action;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import com.amy.robot.actioncenter.lib.ActionCenterManager;
import com.amyrobotics.action.test.utils.LogUtils;


/**
 * Created by adward on 2017/11/23.
 */

public class ActionCenterLoader {
    private static final String TAG = ActionCenterLoader.class.getSimpleName();

    private static final String APP_SRC_URI = "content://com.amyrobotics.action.test.provider";

    public static void init(Context context) {
        LogUtils.d(TAG, "init ActionCenter begin");

        ActionCenterManager.getInstance().init(context, APP_SRC_URI);

        //开启 debug
        //open debug to output log
        ActionCenterManager.getInstance().setDebug(false);

        LogUtils.d(TAG, "init ActionCenter ok");
    }
}
