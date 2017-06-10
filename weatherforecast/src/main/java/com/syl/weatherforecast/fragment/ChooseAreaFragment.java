package com.syl.weatherforecast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.syl.weatherforecast.R;
import com.syl.weatherforecast.db.City;
import com.syl.weatherforecast.db.County;
import com.syl.weatherforecast.db.Province;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Bright on 2017/6/10.
 *
 * @Describe
 * @Called
 */

public class ChooseAreaFragment extends Fragment {
    @BindView(R.id.title_text)
    TextView mTitleText;
    @BindView(R.id.back_button)
    Button mBackButton;
    @BindView(R.id.list_view)
    ListView mListView;
    private List<String> dataList = new ArrayList();
    public static final int PROVINCE_LEVEL = 0;
    public static final int CITY_LEVEL = 1;
    public static final int COUNTY_LEVEL = 2;
    private List<Province> mProvinceList;//省列表
    private List<City> mCityList;//市列表
    private List<County> mCountyList;//县区列表
    private Province mProvinceSelect;//选中的省
    private City mCitySelect;//选中的市
    private County mCountySelect;//选中的县区
    private int currentLevel;//当前的等级
    private ArrayAdapter<String> mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = View.inflate(getContext(), R.layout.choose_area, null);
        mAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1, dataList);
        mListView.setAdapter(mAdapter);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (currentLevel == PROVINCE_LEVEL) {
                    mProvinceSelect = mProvinceList.get(position);
                    queryCities();
                } else if (currentLevel == CITY_LEVEL) {
                    mCitySelect = mCityList.get(position);
                    queryCounties();
                }
            }
        });
        queryProvinces();
    }

    private void queryCounties() {

    }

    private void queryCities() {

    }

    private void queryProvinces() {

    }
}
