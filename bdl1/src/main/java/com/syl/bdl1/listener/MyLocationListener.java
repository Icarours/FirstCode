package com.syl.bdl1.listener;

import android.util.Log;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.Poi;
import com.baidu.mapapi.model.LatLng;
import com.syl.bdl1.config.Constants;

import java.util.List;

/**
 * Created by Bright on 2017/5/28.
 *
 * @Describe 定位监听
 * @Called
 */

public class MyLocationListener implements BDLocationListener {
    public StringBuffer mSb;//对外返回监听的数据

    @Override
    public void onReceiveLocation(BDLocation location) {
        //Receive Location
        mSb = new StringBuffer(256);
        mSb.append("time : ");
        mSb.append(location.getTime());
        mSb.append("\nerror code : ");
        mSb.append(location.getLocType());
        mSb.append("\nlatitude : ");
        mSb.append(location.getLatitude());//获得纬度
        mSb.append("\nlontitude : ");//获得经度
        mSb.append(location.getLongitude());
        mSb.append("\nradius : ");
        mSb.append(location.getRadius());
        if (location.getLocType() == BDLocation.TypeGpsLocation) {// GPS定位结果
            mSb.append("\nspeed : ");
            mSb.append(location.getSpeed());// 单位：公里每小时
            mSb.append("\nsatellite : ");
            mSb.append(location.getSatelliteNumber());
            mSb.append("\nheight : ");
            mSb.append(location.getAltitude());// 单位：米
            mSb.append("\ndirection : ");
            mSb.append(location.getDirection());// 单位度
            mSb.append("\naddr : ");
            mSb.append(location.getAddrStr());
            mSb.append("\ndescribe : ");
            mSb.append("gps定位成功");

        } else if (location.getLocType() == BDLocation.TypeNetWorkLocation) {// 网络定位结果
            mSb.append("\naddr : ");
            mSb.append(location.getAddrStr());
            //运营商信息
            mSb.append("\noperationers : ");
            mSb.append(location.getOperators());
            mSb.append("\ndescribe : ");
            mSb.append("网络定位成功");
        } else if (location.getLocType() == BDLocation.TypeOffLineLocation) {// 离线定位结果
            mSb.append("\ndescribe : ");
            mSb.append("离线定位成功，离线定位结果也是有效的");
        } else if (location.getLocType() == BDLocation.TypeServerError) {
            mSb.append("\ndescribe : ");
            mSb.append("服务端网络定位失败，可以反馈IMEI号和大体定位时间到loc-bugs@baidu.com，会有人追查原因");
        } else if (location.getLocType() == BDLocation.TypeNetWorkException) {
            mSb.append("\ndescribe : ");
            mSb.append("网络不同导致定位失败，请检查网络是否通畅");
        } else if (location.getLocType() == BDLocation.TypeCriteriaException) {
            mSb.append("\ndescribe : ");
            mSb.append("无法获取有效定位依据导致定位失败，一般是由于手机的原因，处于飞行模式下一般会造成这种结果，可以试着重启手机");
        }
        mSb.append("\nlocationdescribe : ");
        mSb.append(location.getLocationDescribe());// 位置语义化信息
        List<Poi> list = location.getPoiList();// POI数据
        if (list != null) {
            mSb.append("\npoilist size = : ");
            mSb.append(list.size());
            for (Poi p : list) {
                mSb.append("\npoi= : ");
                mSb.append(p.getId() + " " + p.getName() + " " + p.getRank());
            }
        }
        Log.i("BaiduLocationApiDem", mSb.toString());
//            mTvLocation.setText(mSbCurrentLocation.toString());
        //当前的纬度和经度,赋值给mCurrentLatLng
        Constants.mCurrentLatLng = new LatLng(location.getLatitude(), location.getLongitude());
        //当前的定位信息,将当前的位置信息交给mSbCurrentLocation
        Constants.mSbCurrentLocation = mSb;
    }

    @Override
    public void onConnectHotSpotMessage(String s, int i) {
        System.out.println("s==" + s + "--i==" + i);
    }
}