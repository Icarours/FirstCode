package com.syl.weatherforecast.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.gson.Gson;
import com.syl.weatherforecast.R;
import com.syl.weatherforecast.bean.Weather;
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
 * author   Bright
 * date     2017/6/22 13:45
 * desc
 * 测试用Activity
 */
public class Main2Activity extends AppCompatActivity {

    private static final String TAG = Main2Activity.class.getSimpleName();
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
    private List<Weather.HeWeatherBean> mHeWeatherList = new ArrayList<>();
    private Weather.HeWeatherBean mHeWeatherBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        ButterKnife.bind(this);

        mTvAqi = (TextView) findViewById(R.id.tv_aqi);
        mTvBasic = (TextView) findViewById(R.id.tv_basic);
        mTvDailyForecast = (TextView) findViewById(R.id.tv_daily_forecast);
        mTvHourlyForecast = (TextView) findViewById(R.id.tv_hourly_forecast);
        mTvNow = (TextView) findViewById(R.id.tv_now);
        mTvStatus = (TextView) findViewById(R.id.tv_status);
        mTvSuggestion = (TextView) findViewById(R.id.tv_suggestion);
        initData();
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
                    mHeWeatherBean = gson.fromJson(jsonString, Weather.HeWeatherBean.class);
                    LogUtil.d(TAG, mHeWeatherBean.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvAqi.setText(mHeWeatherBean.getAqi().toString());
                        mTvBasic.setText(mHeWeatherBean.getBasic().toString());
                        mTvDailyForecast.setText(mHeWeatherBean.getDaily_forecast().toString());
                        mTvHourlyForecast.setText(mHeWeatherBean.getHourly_forecast().toString());
                        mTvNow.setText(mHeWeatherBean.getNow().toString());
                        mTvStatus.setText(mHeWeatherBean.getStatus());
                        mTvSuggestion.setText(mHeWeatherBean.getSuggestion().toString());
                    }
                });
            }
        });
    }
}
