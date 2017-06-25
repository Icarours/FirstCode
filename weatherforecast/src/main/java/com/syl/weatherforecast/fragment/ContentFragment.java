package com.syl.weatherforecast.fragment;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.text.method.ScrollingMovementMethod;
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

    private HeWeather mWeather;
    private View mRootView;
    private TextView mTvCnodTxt;
    private TextView mTvTmp;
    private TextView mTvWind;
    private TextView mTvBasicCity;
    private TextView mTvBasicUpdateTime;
    private TextView mTvDate1;
    private TextView mTvCnod1;
    private TextView mTvTmpMax1;
    private TextView mTvTemMin1;
    private TextView mTvWind1;
    private TextView mTvDate2;
    private TextView mTvCnod2;
    private TextView mTvTmpMax2;
    private TextView mTvTemMin2;
    private TextView mTvWind2;
    private TextView mTvDate3;
    private TextView mTvCnod3;
    private TextView mTvTmpMax3;
    private TextView mTvTemMin3;
    private TextView mTvWind3;
    private TextView mTvHourlyDate1;
    private TextView mTvHourlyCnod1;
    private TextView mTvHourlyTmp1;
    private TextView mTvHourlyWind1;
    private TextView mTvHourlyDate2;
    private TextView mTvHourlyCnod2;
    private TextView mTvHourlyTmp2;
    private TextView mTvHourlyWind2;
    private TextView mTvAqiTxt;
    private TextView mTvAqiQlty;
    private TextView mTvAqiPm10;
    private TextView mTvAqiPm25;
    private TextView mTvComf;
    private TextView mTvCw;
    private TextView mTvDrsg;
    private TextView mTvFlu;
    private TextView mTvSport;
    private TextView mTvTrav;
    private TextView mTvUv;
    private SharedPreferences mPreferences;

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
        initView();

        //使用Preference存储数据
        mPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());

        ButterKnife.bind(this, mRootView);//跟Activity的用法一样,不过要指定绑定的视图mRootView

        initData();
        return mRootView;
    }

    private void initView() {
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) mRootView.findViewById(R.id.srl);
        //设置下拉背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置进度条颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        initData();
                        //刷新完毕,收起刷新状态
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1200);
            }
        });
        //now
        mTvCnodTxt = (TextView) mRootView.findViewById(R.id.tv_cnod_txt);
        mTvTmp = (TextView) mRootView.findViewById(R.id.tv_tmp);
        mTvWind = (TextView) mRootView.findViewById(R.id.tv_wind);

        //basic
        mTvBasicCity = (TextView) mRootView.findViewById(R.id.tv_basic_city);
        mTvBasicUpdateTime = (TextView) mRootView.findViewById(R.id.tv_basic_update_time);

        //daily_forecast
        mTvDate1 = (TextView) mRootView.findViewById(R.id.tv_date1);
        mTvCnod1 = (TextView) mRootView.findViewById(R.id.tv_cnod1);
        mTvTmpMax1 = (TextView) mRootView.findViewById(R.id.tv_tmp_max1);
        mTvTemMin1 = (TextView) mRootView.findViewById(R.id.tmp_min1);
        mTvWind1 = (TextView) mRootView.findViewById(R.id.tv_wind1);

        mTvDate2 = (TextView) mRootView.findViewById(R.id.tv_date2);
        mTvCnod2 = (TextView) mRootView.findViewById(R.id.tv_cnod2);
        mTvTmpMax2 = (TextView) mRootView.findViewById(R.id.tv_tmp_max2);
        mTvTemMin2 = (TextView) mRootView.findViewById(R.id.tmp_min2);
        mTvWind2 = (TextView) mRootView.findViewById(R.id.tv_wind2);

        mTvDate3 = (TextView) mRootView.findViewById(R.id.tv_date3);
        mTvCnod3 = (TextView) mRootView.findViewById(R.id.tv_cnod3);
        mTvTmpMax3 = (TextView) mRootView.findViewById(R.id.tv_tmp_max3);
        mTvTemMin3 = (TextView) mRootView.findViewById(R.id.tmp_min3);
        mTvWind3 = (TextView) mRootView.findViewById(R.id.tv_wind3);

        //hourly_forecast
        mTvHourlyDate1 = (TextView) mRootView.findViewById(R.id.tv_hourly_date1);
        mTvHourlyCnod1 = (TextView) mRootView.findViewById(R.id.tv_hourly_cnod1);
        mTvHourlyTmp1 = (TextView) mRootView.findViewById(R.id.tv_hourly_tmp1);
        mTvHourlyWind1 = (TextView) mRootView.findViewById(R.id.tv_hourly_wind1);

        mTvHourlyDate2 = (TextView) mRootView.findViewById(R.id.tv_hourly_date2);
        mTvHourlyCnod2 = (TextView) mRootView.findViewById(R.id.tv_hourly_cnod2);
        mTvHourlyTmp2 = (TextView) mRootView.findViewById(R.id.tv_hourly_tmp2);
        mTvHourlyWind2 = (TextView) mRootView.findViewById(R.id.tv_hourly_wind2);

        //aqi
        mTvAqiTxt = (TextView) mRootView.findViewById(R.id.tv_aqi_txt);
        mTvAqiQlty = (TextView) mRootView.findViewById(R.id.tv_aqi_qlty);
        mTvAqiPm10 = (TextView) mRootView.findViewById(R.id.tv_aqi_pm10);
        mTvAqiPm25 = (TextView) mRootView.findViewById(R.id.tv_aqi_pm25);

        //suggestion
        mTvComf = (TextView) mRootView.findViewById(R.id.tv_comf);
        mTvCw = (TextView) mRootView.findViewById(R.id.tv_cw);
        mTvDrsg = (TextView) mRootView.findViewById(R.id.tv_drsg);
        mTvFlu = (TextView) mRootView.findViewById(R.id.tv_flu);
        mTvSport = (TextView) mRootView.findViewById(R.id.tv_sport);
        mTvTrav = (TextView) mRootView.findViewById(R.id.tv_trav);
        mTvUv = (TextView) mRootView.findViewById(R.id.tv_uv);

        mTvComf.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvCw.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvDrsg.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvFlu.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvSport.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvTrav.setMovementMethod(ScrollingMovementMethod.getInstance());
        mTvUv.setMovementMethod(ScrollingMovementMethod.getInstance());
    }

    /**
     * 初始化数据
     */
    private void initData() {
        if (mPreferences != null) {//如果mPreferences
            String dataResponse = mPreferences.getString("dataResponse", null);
            long currentTime = mPreferences.getLong("currentTime", 0);
            long intervalTime = SystemClock.currentThreadTimeMillis() - currentTime;
            LogUtil.d(TAG, "intervalTime==" + intervalTime);
            if (dataResponse != null && intervalTime < 1000 * 5) {//如果dataResponse存在(如果内存中有数据)
                try {
                    handleResponseStr(dataResponse);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                //从ChooseCountyActivity拿到weatherId
                String weatherId = getActivity().getIntent().getStringExtra("weatherId");
                // TODO: 2017/6/25
//                String url = "http://guolin.tech/api/weather?cityid=CN101190401&key=e9d466dcc631437c9d49dafcb6b8fc20";
                String url = "http://guolin.tech/api/weather?cityid="+weatherId+"&key=e9d466dcc631437c9d49dafcb6b8fc20";
                OkHttpClient okHttpClient = new OkHttpClient();
                final Request request = new Request.Builder()
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
                            //存缓存
                            SharedPreferences.Editor edit = mPreferences.edit();
                            edit.putString("dataResponse", responsStr);
                            long currentThreadTimeMillis = SystemClock.currentThreadTimeMillis();
                            edit.putLong("currentTime", currentThreadTimeMillis);
                            edit.apply();
                            handleResponseStr(responsStr);
                            LogUtil.d(TAG, mWeather.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }

        //数据和视图的绑定
        //now
        // mTvNow.setText(mWeather.getNow().toString());
        mTvCnodTxt.setText(mWeather.getNow().getCond().getTxt());
        mTvTmp.setText(mWeather.getNow().getTmp());
        mTvWind.setText(mWeather.getNow().getWind().getDir());

        //basic
        //mTvBasic.setText(mWeather.getBasic().toString());
        mTvBasicCity.setText(mWeather.getBasic().getCity());
        mTvBasicUpdateTime.setText(mWeather.getBasic().getUpdate().getLoc());

        //DailyForecast
        //mTvDailyForecast.setText(mWeather.getDaily_forecast().toString());
        mTvDate1.setText(mWeather.getDaily_forecast().get(0).getDate());
        mTvCnod1.setText(mWeather.getDaily_forecast().get(0).getCond().getTxt_d());
        mTvTmpMax1.setText(mWeather.getDaily_forecast().get(0).getTmp().getMax());
        mTvTemMin1.setText(mWeather.getDaily_forecast().get(0).getTmp().getMin());
        mTvWind1.setText(mWeather.getDaily_forecast().get(0).getWind().getDir());

        mTvDate2.setText(mWeather.getDaily_forecast().get(1).getDate());
        mTvCnod2.setText(mWeather.getDaily_forecast().get(1).getCond().getTxt_d());
        mTvTmpMax2.setText(mWeather.getDaily_forecast().get(1).getTmp().getMax());
        mTvTemMin2.setText(mWeather.getDaily_forecast().get(1).getTmp().getMin());
        mTvWind2.setText(mWeather.getDaily_forecast().get(1).getWind().getDir());

        mTvDate3.setText(mWeather.getDaily_forecast().get(2).getDate());
        mTvCnod3.setText(mWeather.getDaily_forecast().get(2).getCond().getTxt_d());
        mTvTmpMax3.setText(mWeather.getDaily_forecast().get(2).getTmp().getMax());
        mTvTemMin3.setText(mWeather.getDaily_forecast().get(2).getTmp().getMin());
        mTvWind3.setText(mWeather.getDaily_forecast().get(2).getWind().getDir());

        //HourlyForecast
        //mTvHourlyForecast.setText(mWeather.getHourly_forecast().toString());
        mTvHourlyDate1.setText(mWeather.getHourly_forecast().get(0).getDate().substring(5, 16));
        mTvHourlyCnod1.setText(mWeather.getHourly_forecast().get(0).getCond().getTxt());
        mTvHourlyTmp1.setText(mWeather.getHourly_forecast().get(0).getTmp());
        mTvHourlyWind1.setText(mWeather.getHourly_forecast().get(0).getWind().getDir());

        mTvHourlyDate2.setText(mWeather.getHourly_forecast().get(1).getDate().substring(5, 16));
        mTvHourlyCnod2.setText(mWeather.getHourly_forecast().get(1).getCond().getTxt());
        mTvHourlyTmp2.setText(mWeather.getHourly_forecast().get(1).getTmp());
        mTvHourlyWind2.setText(mWeather.getHourly_forecast().get(1).getWind().getDir());

        //status
        mTvStatus.setText(mWeather.getStatus());

        //aqi
        //mTvAqi.setText(mWeather.getAqi().toString());
        mTvAqiTxt.setText("Aqi:" + mWeather.getAqi().getCity().getAqi());
        mTvAqiQlty.setText("Qlty.:" + mWeather.getAqi().getCity().getQlty());
        mTvAqiPm10.setText("Pm10:" + mWeather.getAqi().getCity().getPm10());
        mTvAqiPm25.setText("Pm25:" + mWeather.getAqi().getCity().getPm25());

        //suggestion
        //mTvSuggestion.setText(mWeather.getSuggestion().toString());
        mTvComf.setText(mWeather.getSuggestion().getComf().getBrf() + ":" + mWeather.getSuggestion().getComf().getTxt());
        mTvCw.setText(mWeather.getSuggestion().getCw().getBrf() + ":" + mWeather.getSuggestion().getCw().getTxt());
        mTvDrsg.setText(mWeather.getSuggestion().getDrsg().getBrf() + ":" + mWeather.getSuggestion().getDrsg().getTxt());
        mTvFlu.setText(mWeather.getSuggestion().getFlu().getBrf() + ":" + mWeather.getSuggestion().getFlu().getTxt());
        mTvSport.setText(mWeather.getSuggestion().getSport().getBrf() + ":" + mWeather.getSuggestion().getSport().getTxt());
        mTvTrav.setText(mWeather.getSuggestion().getTrav().getBrf() + ":" + mWeather.getSuggestion().getTrav().getTxt());
        mTvUv.setText(mWeather.getSuggestion().getUv().getBrf() + ":" + mWeather.getSuggestion().getUv().getTxt());
    }

    private void handleResponseStr(String responsStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(responsStr);
        JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
        String jsonString = jsonArray.getJSONObject(0).toString();
        Gson gson = new Gson();
        mWeather = gson.fromJson(jsonString, HeWeather.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }
}
