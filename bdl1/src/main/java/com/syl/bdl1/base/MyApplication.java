package com.syl.bdl1.base;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;

/**
 * Created by Bright on 2017/5/28.
 *
 * @Describe 初始化一些全局变量
 * @Called
 */

public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        //SDKInitializer.initialize(getApplicationContext());，因此我们建议该方法放在Application的初始化方法中
        SDKInitializer.initialize(this);
    }

}
