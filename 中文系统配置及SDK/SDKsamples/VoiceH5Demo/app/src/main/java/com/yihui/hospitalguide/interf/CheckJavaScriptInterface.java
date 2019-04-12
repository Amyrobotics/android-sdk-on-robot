package com.yihui.hospitalguide.interf;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.webkit.JavascriptInterface;
import com.yihui.hospitalguide.manager.VoiceManager;

/**
 * Created by Administrator on 2018/11/6.
 */

public class CheckJavaScriptInterface {

    private static String TAG = "CheckJavaScriptInterface";

    private Context mContext;

    public CheckJavaScriptInterface(Context context) {
        this.mContext = context;
    }

    /**
     * 关闭页面
     */
    @JavascriptInterface
    public void dismiss() {
        Log.e(TAG, "js dismiss");
        Activity activity = (Activity) mContext;
        activity.finish();
    }

    /**
     * 开始语音播报
     */
    @JavascriptInterface
    public void TTS(String say) {
        Log.e(TAG, "js TTS");
        VoiceManager.getInstance().speakStart(say);
    }

    /**
     * 停止语音播报
     */
    @JavascriptInterface
    public void shutUp() {
        Log.e(TAG, "js shutUp");
        VoiceManager.getInstance().speakStop();
    }
}
