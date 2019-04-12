package com.amyrobotics.action.test;

import android.app.Application;
import android.content.Context;

import com.amyrobotics.action.test.action.ActionCenterLoader;

/**
 * Created by adward on 2018/6/21.
 */

public class MainApplication extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);

        ActionCenterLoader.init(this);
    }
}
