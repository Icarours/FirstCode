package com.syl.weatherforecast.utils;

import android.util.Log;

import com.syl.weatherforecast.config.Constant;


/**
 * Created by Bright on 2017/6/8.
 *
 * @Describe 日志管理工具
 * 由isLog控制是否打印日志
 * @Called
 */

public class LogUtil {
    private static boolean isLog = Constant.isLog;//是否打印日志
//    public static final int VERBOSE = 2;
//    public static final int DEBUG = 3;
//    public static final int INFO = 4;
//    public static final int WARN = 5;
//    public static final int ERROR = 6;

    public static void v(String tag, String msg) {
        if (true == isLog) {
            Log.v(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (true == isLog) {
            Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (true == isLog) {
            Log.i(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (true == isLog) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (true == isLog) {
            Log.e(tag, msg);
        }
    }
}
