package com.amyrobotics.action.test.activity;

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.AppCompatActivity;

import com.amyrobotics.action.test.utils.LoadingDialogUtils;
import com.amyrobotics.action.test.utils.ToastUtil;

/**
 * Created by adward on 2018/6/21.
 */

public class BaseActivity extends AppCompatActivity {
    Dialog loadingDialog;
    Handler mHandler = new Handler();

    public Activity getActivity() {
        return this;
    }

    public void showLoading(String text) {
        if(loadingDialog != null && loadingDialog.isShowing()) {
            LoadingDialogUtils.setLoadingDialogMessage(loadingDialog, text);
        } else {
            loadingDialog = LoadingDialogUtils.showLoadingDialog(getActivity(), text);
        }
    }

    public void hideLoading() {
        if(loadingDialog != null) {
            LoadingDialogUtils.hideDialog(loadingDialog);
        }
    }

    public void showToast(final String text) {
        if(Looper.getMainLooper() != Looper.myLooper()) {
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    showToast(text);
                }
            });
        }

        if (isFinishing()) {
            return;
        }

        ToastUtil.showShort(getActivity(), text);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
    }

}
