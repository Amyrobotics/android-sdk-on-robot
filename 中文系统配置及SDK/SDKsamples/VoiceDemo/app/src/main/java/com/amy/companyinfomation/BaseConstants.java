package com.amy.companyinfomation;

import android.os.Environment;

/**
 * Created by Administrator on 2018/9/20.
 */

public class BaseConstants {

    public static final String SDCARD = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String BASE_RESOUCE_PATH = SDCARD + "/Customize/CompanyInformation";
    public static final String INI_PATH = BASE_RESOUCE_PATH + "/ini.xml";
    public static final String IMG_PATH = BASE_RESOUCE_PATH + "/img/";
    public static final String WEB_PATH = BASE_RESOUCE_PATH + "/web/";

}
