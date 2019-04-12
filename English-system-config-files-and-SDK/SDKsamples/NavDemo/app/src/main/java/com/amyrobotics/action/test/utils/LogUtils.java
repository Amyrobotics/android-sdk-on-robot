package com.amyrobotics.action.test.utils;

import android.util.Log;

import java.text.SimpleDateFormat;
import java.util.Date;


/**
 *  LogUtils
 */
public class LogUtils {
    static final boolean DEBUG = true;
    private static final int LOG_LEVEL_MIN = Log.VERBOSE;
    public static boolean LOG_TEST = false;
    public static boolean LOG_NET = false;

    private static final LogUtils defaultInstance = new LogUtils();

    private static LogUtils getInstance()
    {
        return defaultInstance;
    }

    private static String getStr(String message) {
        if(DEBUG) {
            return getInstance().getExtraInfo() + " " + message;
        } else {
            return message;
        }
    }

    static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM-dd_HH:mm:ss.SSS");

    static String getThreadInfo() {
        return String.format("%s %d ",simpleDateFormat.format(new Date()),
                Thread.currentThread().getId());
    }

    public static void i(String tag, String s) {
        if (LOG_LEVEL_MIN <= Log.INFO) {
            if(LOG_TEST) {
                System.out.println(getThreadInfo() + tag + getStr(s));
                return;
            }

            s = getStr(s);
            Log.i(tag, s);

            if(LOG_NET) {
//                NetLog.sendLog(tag, s);
            }
        }
    }

    public static void e(String tag, String s) {
        if (LOG_LEVEL_MIN <= Log.ERROR) {
            if(LOG_TEST) {
                System.out.println(getThreadInfo() + tag + getStr(s));
                return;
            }

            s = getStr(s);
            Log.e(tag, s);

            if(LOG_NET) {
//                NetLog.sendLog(tag, s);
            }
        }
    }

    public static void e(String tag, String s, Throwable tr) {
        if (LOG_LEVEL_MIN <= Log.ERROR) {
            if(LOG_TEST) {
                String msg = tr != null ? tr.getMessage() : "";
                System.out.println(getThreadInfo() + tag + getStr(s) + "\n" + msg);
                return;
            }

            s = getStr(s);
            Log.e(tag, s, tr);

            if(LOG_NET) {
//                String msg = tr != null ? tr.getMessage() : "";
//                NetLog.sendLog(tag, s + "\n" + msg);
            }
        }
    }

    public static void d(String tag, String s) {
        if (LOG_LEVEL_MIN <= Log.DEBUG) {
            if(LOG_TEST) {
                System.out.println(getThreadInfo() + tag + " " + getStr(s));
            } else {
                s = getStr(s);
                Log.d(tag, s);

                if(LOG_NET) {
//                    NetLog.sendLog(tag, s);
                }
            }
        }
    }

    public static void w(String tag, String s) {
        if (LOG_LEVEL_MIN <= Log.WARN) {
            if (LOG_TEST) {
                System.out.println(getThreadInfo() + tag + " " + getStr(s));
            } else {
                s = getStr(s);
                Log.w(tag, s);

                if(LOG_NET) {
//                    NetLog.sendLog(tag, s);
                }
            }
        }
    }

    public static void w(String tag, String s, Throwable tr) {
        if (LOG_LEVEL_MIN <= Log.WARN) {
            if(LOG_TEST) {
                System.out.println(getThreadInfo() + tag + " " + getStr(s));
            } else {
                s = getStr(s);
                Log.w(tag, s, tr);

                if(LOG_NET) {
//                    NetLog.sendLog(tag, s);
                }
            }
        }
    }

    public static void v(String tag, String s) {
        if (LOG_LEVEL_MIN <= Log.VERBOSE) {
            if(LOG_TEST) {
                System.out.println(getThreadInfo() + tag + " " + getStr(s));
            } else {
                s = getStr(s);
                Log.v(tag, s);

                if(LOG_NET) {
//                    NetLog.sendLog(tag, s);
                }
            }
        }
    }

    public static void v(String tag, String s, Throwable tr) {
        if (LOG_LEVEL_MIN <= Log.VERBOSE) {
            if(LOG_TEST) {
                System.out.println(getThreadInfo() + tag + " " + getStr(s));
            } else {
                s = getStr(s);
                Log.v(tag, s, tr);

                if(LOG_NET) {
//                    NetLog.sendLog(tag, s);
                }
            }
        }
    }

    public String getExtraInfo() {
        StringBuilder stringBuilder = new StringBuilder();
        int i = getStackTraceElementIndex();
        StackTraceElement ste = Thread.currentThread().getStackTrace()[i];
//        stringBuilder.append(getClassName(ste.getClassName())).append(".");
//        stringBuilder.append(ste.getMethodName());
        stringBuilder.append("(");
        stringBuilder.append(ste.getFileName()).append(":");
        stringBuilder.append(ste.getLineNumber()).append(")");
        return stringBuilder.toString();
    }

    private String getClassName(String className) {
        int pos = className.lastIndexOf(46);
        if(pos == -1) {
            return className;
        } else {
            className = className.substring(pos + 1);
            pos = className.lastIndexOf(36);
            return pos == -1?className:className.substring(pos + 1);
        }
    }

    private int getStackTraceElementIndex() {
        StackTraceElement[] stack = Thread.currentThread().getStackTrace();

        int i;
        for(i = 0; i < stack.length; ++i) {
            String cls = stack[i].getClassName();
            if(cls.equals(this.getClass().getName())) {
                i += 3;
                break;
            }
        }

        return i;
    }

}
