package com.syl.weatherforecast.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.gson.Gson;
import com.syl.weatherforecast.R;
import com.syl.weatherforecast.bean.HeWeather;
import com.syl.weatherforecast.utils.LogUtil;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Bright on 2017/6/22.
 *
 * @Describe 天气预报的具体内容
 * @Called
 */

public class ContentFragment extends Fragment {
    public static final String TAG = ContentFragment.class.getSimpleName();
    @BindView(R.id.tv_aqi)
    TextView mTvAqi;
    @BindView(R.id.tv_basic)
    TextView mTvBasic;
    @BindView(R.id.tv_daily_forecast)
    TextView mTvDailyForecast;
    @BindView(R.id.tv_hourly_forecast)
    TextView mTvHourlyForecast;
    @BindView(R.id.tv_now)
    TextView mTvNow;
    @BindView(R.id.tv_status)
    TextView mTvStatus;
    @BindView(R.id.tv_suggestion)
    TextView mTvSuggestion;

    private List<HeWeather> mHeWeatherList = new ArrayList<>();
    private HeWeather mWeather;
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

        ButterKnife.bind(this, mRootView);//跟Activity的用法一样,不过要指定绑定的视图mRootView
        initData();
        return mRootView;
    }

    private void initData() {
        String url = "http://guolin.tech/api/weather?cityid=CN101190401&key=e9d466dcc631437c9d49dafcb6b8fc20";
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .url(url)
                .build();
        okHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtil.d(TAG, e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                LogUtil.d(TAG, response.toString());
                try {
                    String responsStr = response.body().string();
                    JSONObject jsonObject = new JSONObject(responsStr);
                    JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
                    String jsonString = jsonArray.getJSONObject(0).toString();
                    Gson gson = new Gson();
                    mWeather = gson.fromJson(jsonString, HeWeather.class);
                    LogUtil.d(TAG, mWeather.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvAqi.setText(mWeather.getAqi().toString());
                        mTvBasic.setText(mWeather.getBasic().toString());
                        mTvDailyForecast.setText(mWeather.getDaily_forecast().toString());
                        mTvHourlyForecast.setText(mWeather.getHourly_forecast().toString());
                        mTvNow.setText(mWeather.getNow().toString());
                        mTvStatus.setText(mWeather.getStatus());
                        mTvSuggestion.setText(mWeather.getSuggestion().toString());
                    }
                });
            }
        });
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }
}
