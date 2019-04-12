package com.amyrobotics.action.test.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by adward on 2016/11/4.
 */
public class LoadingDialogUtils {
    public static final int DIALOG_DELAY_SHOW_TIME = 1000;

    public static ProgressDialog createLoadingDialog(Context context) {
        ProgressDialog dialog = new ProgressDialog(context);

        dialog.setCanceledOnTouchOutside(false); //设置点击屏幕Dialog不消失
        return dialog;
    }

    public static Dialog showLoadingDialog(Context context, int resId) {
        if((context instanceof Activity) && (!((Activity)context).isFinishing())) {
            String text = context.getString(resId);
            try {
                return showLoadingDialog(context, text);
            }catch (Exception e) {
                return null;
            }
        }
        return null;
    }

    public static Dialog showLoadingDialog(Context context, String text) {
        ProgressDialog dialog = createLoadingDialog(context);
        dialog.setMessage(text);
        dialog.show();
        return dialog;
    }

    public static void setLoadingDialogMessage(Dialog dialog, String message) {
        ((ProgressDialog)dialog).setMessage(message);

    }

    public static void hideDialog(Dialog dialog) {
        try {
            if (dialog != null && dialog.isShowing()) {
                dialog.dismiss();
            }
        }catch (Exception e) {
            //ignore
        }
    }

}
