package com.syl.firstcode.config;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.Process;

/**
 * Created by Bright on 2017/5/20.
 *
 * @Describe
 * Android 提供的全局单例类,将一些需要设置成全局单例的可以放到该类中
 * @Called
 */

public class MyApplication extends Application {
    private static Context sContext ;//Application级别的Context
    private static Handler sMainThreadHandler;//Application级别的主线程Handler
    private static int sMainThreadId;//主线程的线程id

    public static Context getContext() {
        return sContext;
    }

    public static Handler getMainThreadHandler() {
        return sMainThreadHandler;
    }

    public static int getMainThreadId() {
        return sMainThreadId;
    }

    @Override
    public void onCreate() {//程序的入口方法,获取常用的一些变量
        super.onCreate();
        //初始化sContext
        sContext = getApplicationContext();
        /**
         * Process.myTid();Thread
         * Process.myUid();User
         * Process.myPid();Process
         */
        //初始化sMainThreadId
        sMainThreadId = Process.myTid();
        //初始化sMainThreadHandler
        sMainThreadHandler = new Handler();
    }
}
