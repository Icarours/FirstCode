package com.syl.weatherforecast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.syl.weatherforecast.R;
import com.syl.weatherforecast.utils.LogUtil;

/**
 * Created by Bright on 2017/6/22.
 *
 * @Describe 天气预报的具体内容
 * @Called
 */

public class ContentFragment extends Fragment {
    public static final String TAG = ContentFragment.class.getSimpleName();
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreate");
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onCreateView");
        mRootView = inflater.inflate(R.layout.fragment_content, container, false);
        return mRootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }
}
