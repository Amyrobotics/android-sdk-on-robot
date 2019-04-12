package com.yihui.hospitalguide.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.util.Log;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.yihui.hospitalguide.R;
import com.yihui.hospitalguide.interf.CheckJavaScriptInterface;
import com.yihui.hospitalguide.manager.VoiceManager;

public class MainActivity extends BaseActivity {

    private WebView mMainWv;
    private String mUrl = "http://218.75.108.154:8817/mobile-openPlatform/liaoningzhongyi/#/index";
    private String TAG = "YiHuiH5DemoActivity";
    private Dialog dialog;
    private boolean isLoadFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mMainWv = findViewById(R.id.wv_main);
        dialog = showProgressDialog(this, "", "正在加载...");

        initWebView();
    }

    private void initWebView() {
        mMainWv.loadUrl(mUrl);

        //支持javascript
        mMainWv.getSettings().setJavaScriptEnabled(true);
        mMainWv.addJavascriptInterface(new CheckJavaScriptInterface(this), "android");
        // 设置可以支持缩放
//        mMainWv.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
//        mMainWv.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mMainWv.getSettings().setUseWideViewPort(true);
        //设置默认为utf-8
        mMainWv.getSettings().setDefaultTextEncodingName("UTF-8");
        //自适应屏幕
        mMainWv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        mMainWv.getSettings().setLoadWithOverviewMode(true);

        mMainWv.getSettings().setJavaScriptEnabled(true);
        mMainWv.getSettings().setDomStorageEnabled(true);

        mMainWv.setWebChromeClient(new WebChromeClient() {

            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
            }

            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
            }

            public boolean onConsoleMessage(ConsoleMessage cm) {
                if (cm.messageLevel() == ConsoleMessage.MessageLevel.DEBUG) {
                    Log.d(TAG, String.format("%s -- From line %s of %s", cm.message(), cm.lineNumber(), cm.sourceId()));
                } else if (cm.messageLevel() == ConsoleMessage.MessageLevel.LOG || cm.messageLevel() == ConsoleMessage.MessageLevel.TIP) {
                    Log.i(TAG, String.format("%s -- From line %s of %s", cm.message(), cm.lineNumber(), cm.sourceId()));
                } else if (cm.messageLevel() == ConsoleMessage.MessageLevel.WARNING) {
                    Log.w(TAG, String.format("%s -- From line %s of %s", cm.message(), cm.lineNumber(), cm.sourceId()));
                } else if (cm.messageLevel() == ConsoleMessage.MessageLevel.ERROR) {
                    Log.e(TAG, String.format("%s -- From line %s of %s", cm.message(), cm.lineNumber(), cm.sourceId()));
                }
                return true;
            }

            @Deprecated
            public void onConsoleMessage(String message, int lineNumber, String sourceID) {
                // FIXME Auto-generated method stub
                super.onConsoleMessage(message, lineNumber, sourceID);
            }

            @Override
            public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
                return true;
            }
        });

        mMainWv.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
            }

            /**
             * @param view
             * @param url
             */
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                dialog.dismiss();
                isLoadFinish = true;
            }

            @Override
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                super.onReceivedSslError(view, handler, error);
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
                Log.d("BankaError", "errorCode=" + errorCode + ",description=" + description + ",failingUrl=" + failingUrl);
                if (errorCode == WebViewClient.ERROR_CONNECT || errorCode == WebViewClient.ERROR_TIMEOUT || errorCode == WebViewClient.ERROR_HOST_LOOKUP) {

                }
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (Uri.parse(url).getScheme().startsWith("file")) {
                    // 如果是本地加载的话，直接用当期浏览器加载
                    return false;
                }
                if (Uri.parse(url).getScheme().startsWith("http")) {
                    return false;
                }
                if ("about:blank".equals(url)) {
                    return false; // 不需要处理空白页
                }
                return true;
            }

        });
    }

    public static Dialog showProgressDialog(Context context, CharSequence title, CharSequence msg) {
        Dialog dialog = ProgressDialog.show(context, title, msg);
        return dialog;
    }

    @Override
    public synchronized void disposeVoice(final String voiceFont) {
//        super.disposeVoice(voiceFont);
        if (!isLoadFinish) {
            Toast.makeText(this, "网页未加载完成，请稍候", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (voiceFont) {
            default:
                mMainWv.post(new Runnable() {
                    @Override
                    public void run() {
                        String action = "javascript:voiceInput('" + voiceFont + "')";
                        mMainWv.loadUrl(action);
                        Log.e(TAG, action);
                    }
                });
                break;
        }
    }

    @Override
    protected void onDestroy() {
        VoiceManager.getInstance().speechRecognitionStop();
        super.onDestroy();
    }
}
