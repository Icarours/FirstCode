package com.syl.bdl1.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.syl.bdl1.R;
import com.syl.bdl1.config.Constants;

/**
 * author   Bright
 * date     2017/5/28 17:35
 * desc
 * 百度定位
 * 真正的定位过程已经在MyApplication中完成
 */
public class LocationActivity extends AppCompatActivity {
    private TextView mTvLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_location);
        mTvLocation = (TextView) findViewById(R.id.tv_location);
    }

    /**
     * 展示定位信息
     * @param view
     */
    public void location(View view) {
        mTvLocation.setText(Constants.mSbCurrentLocation.toString());
    }
}
