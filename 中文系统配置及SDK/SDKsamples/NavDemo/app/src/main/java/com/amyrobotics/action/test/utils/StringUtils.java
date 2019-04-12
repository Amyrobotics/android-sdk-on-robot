package com.amyrobotics.action.test.utils;

/**
 * Created by Leo on 2018/1/5.
 */
public class StringUtils {

    public static String safe(String str) {
        return str == null ? "" : str;
    }

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty();
    }

    public static boolean compare(String src, String dst) {
        return (src != null) && src.equals(dst);
    }

}
