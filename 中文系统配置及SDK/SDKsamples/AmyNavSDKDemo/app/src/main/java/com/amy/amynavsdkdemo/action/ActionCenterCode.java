package com.amy.amynavsdkdemo.action;

/**
 * Created by adward on 2018/3/20.
 */

public class ActionCenterCode {

    //ok
    public static final int CODE_SUCCESS = 0;

    //错误
    //error
    public static final int CODE_ERROR = 1000;

    //无效参数
    //Invalid parameter
    public static final int CODE_INVALID_PARAMS = 1001;

    //开启导航失败，一般是工控失败
    // open navigation failure, usually industrial control failure
    public static final int CODE_ERROR_START_NAV_FAILED = 2000;

    //无效默认地图id错误，用户没有设置默认导航地图导致
    //Invalid default map id error caused by user not setting default navigation map
    public static final int CODE_ERROR_INVALID_DEFAULT_MAP_ID = 2001;

    //该地图没有标记点，无法导航
    // the map is unmarked and unnavigable
    public static final int CODE_ERROR_MAP_MARK_POINTS_IS_EMPTY = 2002;

    //标记列表中没有该标记点，无法导航
    // the tag is not in the tag list and cannot be navigated
    public static final int CODE_ERROR_MAP_MARK_POINT_NOT_EXIST = 2003;

    //导航失败
    // navigation failed
    public static final int CODE_ERROR_NAV_FAILED = 2004;
    //导航放弃
    // navigation abort
    public static final int CODE_ERROR_NAV_GIVEUP = 2005;
    //导航丢失
    // lost navigation
    public static final int CODE_ERROR_NAV_LOST = 2006;
    //导航超时
    // navigation timeout
    public static final int CODE_ERROR_NAV_TIME_OUT = 2007;
    //导航取消
    // cancel navigation
    public static final int CODE_ERROR_NAV_CANCEL = 2008;
    //导航停止
    // navigation stops
    public static final int CODE_ERROR_NAV_STOP = 2009;

    //无错
    // error free
    public static final int ACTION_NOERROR = 0;
    //成功
    // success
    public static final int ACTION_OK = 0;

    //exception 错误
    //exception Error
    public static final int ACTION_ERROR_EXCEPTION = -1;

    //provider 不存在，或者 URI 无效
    //provider non-existent，or URI of no avail
    public static final int ACTION_ERROR_PROVIDER_URI = -2;

    //无效参数
    //Invalid parameter
    public static final int ACTION_ERROR_INVALID_PARAMS = -3;

    //url 为空
    //url null
    public static final int ACTION_ERROR_URL_EMPTY = -4;

    public static String APP_ONWAKEUP_VOICE = "voice.onwakeup";


}
