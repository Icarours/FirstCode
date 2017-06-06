package com.syl.bdl1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.MyLocationData;
import com.syl.bdl1.R;
import com.syl.bdl1.config.Constants;

/**
 * author   Bright
 * date     2017/5/29 1:17
 * desc
 * 显示自己当前所在位置
 */
public class ShowMeActivity extends AppCompatActivity {
    private boolean isFirstLocation = true;//第一次定位
    private MapView mMapView;
    private BaiduMap mBaiduMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_me);

        mMapView = (MapView) findViewById(R.id.mv);
        //获得子图控制器
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setMyLocationEnabled(true);
        navigationTo();
    }

    /**
     * 将当前位置显示在地图上
     */
    private void navigationTo() {
        if (isFirstLocation) {
            MapStatusUpdate update = MapStatusUpdateFactory.newLatLng(Constants.mCurrentLatLng);
            mBaiduMap.animateMapStatus(update);
            update = MapStatusUpdateFactory.zoomTo(16);
            mBaiduMap.animateMapStatus(update);
            isFirstLocation = false;
        }
        MyLocationData.Builder builder = new MyLocationData.Builder();
        MyLocationData locationData = builder.latitude(Constants.mCurrentLatLng.latitude)
                .latitude(Constants.mCurrentLatLng.longitude)
                .build();
        //设置定位数据, 只有先允许定位图层后设置数据才会生效
        mBaiduMap.setMyLocationData(locationData);

        //添加标注覆盖物
        MarkerOptions options2 = new MarkerOptions();
        options2.icon(BitmapDescriptorFactory.fromResource(R.mipmap.icon_gcoding))
                .position(Constants.mCurrentLatLng)
                .animateType(MarkerOptions.MarkerAnimateType.grow);
        //添加标注覆盖物
        mBaiduMap.addOverlay(options2);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //设置是否允许定位图层,(设置定位图层配置信息，只有先允许定位图层后设置定位图层配置信息才会生效)
        mBaiduMap.setMyLocationEnabled(false);
    }
}
