package com.syl.bdl1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.syl.bdl1.R;
import com.syl.bdl1.config.Constants;
/**
 * author   Bright
 * date     2017/5/28 17:37
 * desc
 * 基础地图
 */
public class BaseMapActivity extends AppCompatActivity {
    MapView mMapView = null;
    protected BaiduMap mBaiduMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //在使用SDK各组件之前初始化context信息，传入ApplicationContext
        //注意该方法要再setContentView方法之前实现
        //SDKInitializer.initialize(getApplicationContext());，因此我们建议该方法放在Application的初始化方法中
        //SDKInitializer.initialize(getApplicationContext());
        setContentView(R.layout.activity_base_map);
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.bmapView);

        mBaiduMap = mMapView.getMap();//获取BaiduMap的实例,地图控制器

        //控制缩放按钮的显隐
        mMapView.showZoomControls(false);

        //默认放大级别,缩放取值范围3-19,可取小数
        MapStatusUpdate mapStatus = MapStatusUpdateFactory.zoomTo(18);
        mBaiduMap.animateMapStatus(mapStatus);

        //设置默认位置
        trans();
    }
    /**
     * 平移到指定位置
     */
    private void trans() {
        LatLng latLng = Constants.mCurrentLatLng;//当下位置
        MapStatusUpdate mapStatus = MapStatusUpdateFactory.newLatLng(latLng);
        mBaiduMap.animateMapStatus(mapStatus);
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //在activity执行onDestroy时执行mMapView.onDestroy()，实现地图生命周期管理
        mMapView.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        //在activity执行onPause时执行mMapView. onPause ()，实现地图生命周期管理
        mMapView.onPause();
    }
}