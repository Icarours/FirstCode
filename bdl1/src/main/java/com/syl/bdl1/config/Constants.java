package com.syl.bdl1.config;

import com.baidu.mapapi.model.LatLng;
import com.syl.bdl1.activity.BaseMapActivity;
import com.syl.bdl1.activity.LocationActivity;
import com.syl.bdl1.activity.ShowMeActivity;

/**
 * Created by Bright on 2017/5/28.
 *
 * @Describe
 * @Called
 */

public class Constants {
    public static final String[] TITLES = {
            "基础地图",
            "百度定位",
            "显示当前位置",
    };
    public static Class<?>[] ACTIVITIES = {
            BaseMapActivity.class,
            LocationActivity.class,
            ShowMeActivity.class,
    };
    public static LatLng mCurrentLatLng ;//当前的纬度和经度
    public static StringBuffer mSbCurrentLocation;//存放当前的定位信息
    public static LatLng mLydsLatLng = new LatLng(22.582392, 113.927331);//凌云大厦
    public static LatLng mZldsLatLng = new LatLng(22.584262, 113.923029);//69区中粮工业园
    public static LatLng mTyjLatLng = new LatLng(22.621414,113.870936);//桃源居
    public static LatLng mJbhyLatLng = new LatLng(22.595323,113.88574);//金碧花园
    public static LatLng mKqsyLatLng = new LatLng(22.656704,113.870217);//康桥书院
}
