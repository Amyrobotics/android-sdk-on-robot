package com.amy.amynavsdkdemo.action;

import android.content.Context;


import com.amy.robot.actioncenter.lib.ActionCenterManager;
import com.blankj.utilcode.util.LogUtils;


/**
 * @author Async_wu
 * @date 2019-3-20 15:59:43
 * 注册
 * register
 */
public class ActionCenterLoader {
    private static final String TAG = ActionCenterLoader.class.getSimpleName();
    //注意：com.amy.amynavsdkdemo.provider 需要与 AndroidManifest 中 provider的authorities值保持一致
    //Note: com. Amy. Amynavsdkdemo. Providers need to consistent with the authorities of the provider in the AndroidManifest value
    private static final String APP_SRC_URI = "content://com.amy.amynavsdkdemo.provider";

    public static void init(Context context) {
        ActionCenterManager.getInstance().init(context,APP_SRC_URI);
        //开启 debug
        //Open the debug
        ActionCenterManager.getInstance().setDebug(true);
    }

}
