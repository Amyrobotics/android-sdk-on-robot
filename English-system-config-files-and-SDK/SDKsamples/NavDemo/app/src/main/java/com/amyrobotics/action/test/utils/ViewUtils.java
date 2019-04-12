package com.amyrobotics.action.test.utils;

import android.widget.TextView;

/**
 * Created by adward on 2018/6/21.
 */

public class ViewUtils {
    public static void setText(TextView textView, String text) {
        if(textView != null) {
            textView.setText(text != null ? text : "");
        }
    }

}
