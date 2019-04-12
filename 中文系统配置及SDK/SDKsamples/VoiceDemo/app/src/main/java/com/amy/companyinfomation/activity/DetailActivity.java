package com.amy.companyinfomation.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.webkit.ConsoleMessage;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.amy.companyinfomation.BaseConstants;
import com.amy.companyinfomation.R;
import com.amy.companyinfomation.entity.IniEntity;
import com.amy.companyinfomation.manager.VoiceManager;
import com.amy.robot.actioncenter.lib.ActionCenterManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.File;
import java.io.IOException;

/**
 * Created by Administrator on 2018/9/25.
 */

public class DetailActivity extends BaseActivity implements View.OnClickListener {
    public static final String TAG = "DetailActivity";
    public static final String CODE_WEB_POSITION = "code_web_potition";
    public static final String CODE_CLASS_POSITION = "code_class_potition";

    private WebView mDetailWv;
    private ImageView mLastIv;
    private ImageView mNextIv;
    private Dialog dialog;

    private int mClassPosition;
    private int type = 1;
    private String mWebPath;
    private String mEducationText = "";
    private int mWebPosition;
    private String detail;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        initView();
    }

    private void initView() {
        mDetailWv = findViewById(R.id.wv_detail);
        mLastIv = findViewById(R.id.iv_last);
        mNextIv = findViewById(R.id.iv_next);

        mLastIv.setOnClickListener(this);
        mNextIv.setOnClickListener(this);

        initData();
        initWebView();
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent == null) {
            return;
        }
        mClassPosition = intent.getIntExtra(CODE_CLASS_POSITION, 0);
        mWebPosition = intent.getIntExtra(CODE_WEB_POSITION, 0);
        type = IniEntity.getInstance().getClass_entity().getClassX().get(mClassPosition).getClass_type();
//        if (type == 1) {
//            mLastIv.setBackgroundColor(getResources().getColor(R.color.font_title_white));
//            mNextIv.setBackgroundColor(getResources().getColor(R.color.font_title_white));
//        } else if (type == 2) {
//            mLastIv.setBackgroundColor(getResources().getColor(R.color.bg_detail_blue));
//            mNextIv.setBackgroundColor(getResources().getColor(R.color.bg_detail_blue));
//        }
        mWebPath = "file://" + checkDetail();
        Log.e(TAG, mWebPath);
    }

    public String checkDetail() {
        detail = IniEntity.getInstance().getClass_entity()
                .getClassX().get(mClassPosition).getData().getItem().get(mWebPosition).getItem_detail();
        if (detail == null || detail.equals("")) {
            detail = BaseConstants.WEB_PATH + IniEntity.getInstance().getClass_entity()
                    .getClassX().get(mClassPosition).getData().getItem().get(mWebPosition).getItem_name() + ".html";
        } else {
            detail = BaseConstants.WEB_PATH + IniEntity.getInstance().getClass_entity()
                    .getClassX().get(mClassPosition).getData().getItem().get(mWebPosition).getItem_detail();
        }
        return detail;
    }

    private void initWebView() {
        if (mWebPath == null || mWebPath.equals("")) {
            return;
        }
        mDetailWv.loadUrl(mWebPath);
        dialog = showProgressDialog(this, "", "正在加载...");

        //支持javascript
        mDetailWv.getSettings().setJavaScriptEnabled(true);
//        mDetailWv.addJavascriptInterface(new CheckJavaScriptInterface(this), "android");
        // 设置可以支持缩放
//        mDetailWv.getSettings().setSupportZoom(true);
        // 设置出现缩放工具
//        mDetailWv.getSettings().setBuiltInZoomControls(true);
        //扩大比例的缩放
        mDetailWv.getSettings().setUseWideViewPort(true);
        //水平滚动条不显示
        mDetailWv.setHorizontalScrollBarEnabled(false);
        //垂直滚动条不显示
        mDetailWv.setVerticalScrollBarEnabled(false);
        //自适应屏幕
        mDetailWv.getSettings().setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        mDetailWv.getSettings().setLoadWithOverviewMode(true);

        mDetailWv.getSettings().setJavaScriptEnabled(true);
        mDetailWv.getSettings().setDomStorageEnabled(true);

        mDetailWv.setWebChromeClient(new WebChromeClient() {

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

        mDetailWv.setWebViewClient(new WebViewClient() {

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
                initHTML(detail, type);
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

    public void initHTML(String filePath, int type) {
        try {
            if (type == 1) {
                //解析获取主标题
                mEducationText = "";
                Document document = Jsoup.parse(new File(filePath), "UTF-8");
                Elements elements = document.select("div.name-font");
                mEducationText = mEducationText + elements.text() + ",";
                elements = document.select("div.phone-font");
                mEducationText = mEducationText + elements.text() + ",";
                elements = document.select("div.read-text");
                mEducationText = mEducationText + elements.text() + ",";
                elements = document.select("div.content-font");
                mEducationText = mEducationText + elements.text() + ",";
                elements = document.select("div.explain-title-font");
                mEducationText = mEducationText + elements.text() + ",";
                elements = document.select("div.content-font");
                mEducationText = mEducationText + elements.text() + ",";

                mEducationText = mEducationText.replace(" ", "");
                Log.e(TAG, mEducationText);
                VoiceManager.getInstance().speakStart(mEducationText);
            } else if (type == 2) {
                //解析获取主标题
                Document document = Jsoup.parse(new File(filePath), "UTF-8");
                Elements elements = document.select("div.title-warp");
                mEducationText = "";
                mEducationText = mEducationText + elements.text() + ",";

                //解析第一副标题与内容
                elements = document.select("div.content-warp");
                Elements sub = elements.select("div.content-title");
                Elements con = elements.select("div.education");
                if (sub.size() == con.size()) {
                    for (int i = 0; i < sub.size(); i++) {
                        mEducationText = mEducationText + sub.get(i).text() + ","
                                + con.get(i).text() + ",";
                    }
                }
                mEducationText = mEducationText.replace(" ", "");
                Log.e(TAG, mEducationText);
                VoiceManager.getInstance().speakStart(mEducationText);
            } else {
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Dialog showProgressDialog(Context context, CharSequence title, CharSequence msg) {
        Dialog dialog = ProgressDialog.show(context, title, msg);
        return dialog;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_last:
                VoiceManager.getInstance().speakStop();
                if (mWebPosition == 0) {
                    VoiceManager.getInstance().speakStart("当前已是首页");
                } else {
                    mWebPosition--;
                    mWebPath = "file://" + checkDetail();
                    mDetailWv.loadUrl(mWebPath);
                    Log.e(TAG, mWebPath);
                    dialog.show();
                }
                break;
            case R.id.iv_next:
                VoiceManager.getInstance().speakStop();
                if (mWebPosition < IniEntity.getInstance().getClass_entity()
                        .getClassX().get(mClassPosition).getData().getItem().size() - 1) {
                    mWebPosition++;
                    mWebPath = "file://" + checkDetail();
                    mDetailWv.loadUrl(mWebPath);
                    Log.e(TAG, mWebPath);
                    dialog.show();
                } else {
                    VoiceManager.getInstance().speakStart("当前已是末页");
                }
                break;
        }
    }

    @Override
    public synchronized void disposeVoice(String voiceFont) {
        super.disposeVoice(voiceFont);
        switch (voiceFont) {
            case "上一个":
            case "上一页":
                VoiceManager.getInstance().speakStop();
                if (mWebPosition == 0) {
                    VoiceManager.getInstance().speakStart("当前已是首页");
                    break;
                } else {
                    mWebPosition--;
                    mWebPath = "file://" + checkDetail();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDetailWv.loadUrl(mWebPath);
                            Log.e(TAG, mWebPath);
                            dialog.show();
                        }
                    });
                    break;
                }
            case "下一个":
            case "下一页":
                VoiceManager.getInstance().speakStop();
                if (mWebPosition < IniEntity.getInstance().getClass_entity()
                        .getClassX().get(mClassPosition).getData().getItem().size() - 1) {
                    mWebPosition++;
                    mWebPath = "file://" + checkDetail();
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mDetailWv.loadUrl(mWebPath);
                            Log.e(TAG, mWebPath);
                            dialog.show();
                        }
                    });
                    break;
                } else {
                    VoiceManager.getInstance().speakStart("当前已是末页");
                    break;
                }
        }
    }

    @Override
    protected void onDestroy() {
        VoiceManager.getInstance().speakStop();
        super.onDestroy();
    }
}
