package com.amy.amynavsdkdemo.manager;

import android.os.Bundle;

import com.amy.amynavsdkdemo.action.ActionCenterCode;
import com.amy.robot.actioncenter.common.bean.ActionResult;
import com.amy.robot.actioncenter.lib.ActionCenterManager;
import com.amy.robot.actioncenter.lib.ActionNotifyCallBack;

public class LocalActionCenterManager {
    private static volatile LocalActionCenterManager single;

    public static LocalActionCenterManager getInstance() {
        if (null == single) {
            synchronized (LocalActionCenterManager.class) {
                if (null == single) {
                    single = new LocalActionCenterManager();
                }
            }
        }

        return single;
    }

    private LocalActionCenterManager() {
    }

    public ActionResult executeAction(String uri, String actionName, int arg1, String arg2) {
        ActionResult actionResult = ActionCenterManager.getInstance().executeAction(uri, actionName, arg1, arg2, null);
        switch (actionResult.errorCode) {
            case ActionCenterCode.ACTION_ERROR_EXCEPTION:
//                VoiceManager.getInstance().speakStart("组件未安装");
                break;
        }
        return actionResult;
    }

    public int sendAsynAction(String uri, String actionName, int arg1, String arg2, Bundle argBundle, ActionNotifyCallBack callBack) {
        int code = ActionCenterManager.getInstance().sendAsynAction(uri, actionName, arg1, arg2, argBundle, callBack);
        switch (code) {
            case ActionCenterCode.ACTION_ERROR_PROVIDER_URI:
//                VoiceManager.getInstance().speakStart("组件未安装");
                break;
            case ActionCenterCode.ACTION_ERROR_EXCEPTION:
//                VoiceManager.getInstance().speakStart("出现异常");
                break;
        }
        return code;
    }
}
