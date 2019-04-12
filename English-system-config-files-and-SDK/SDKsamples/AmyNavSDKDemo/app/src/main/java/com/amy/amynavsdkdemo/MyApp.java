package com.amy.amynavsdkdemo;

import android.app.Application;
import android.content.Context;

import com.amy.amynavsdkdemo.action.ActionCenterLoader;
import com.blankj.utilcode.util.Utils;

/**
 * @author Async_wu
 * @date 2019-3-19 15:14:22
 * application
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        //注册utilcode
        Utils.init(this);

        ActionCenterLoader.init(this);



    }
}
