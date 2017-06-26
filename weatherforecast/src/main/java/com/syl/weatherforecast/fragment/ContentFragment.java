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
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
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

    private View mRootView;
    private TextView mTvCnodTxt;
    private TextView mTvTmp;
    private TextView mTvWind;
    private TextView mTvBasicCity;
    private TextView mTvBasicUpdateTime;
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
    private ListView mLvHourlyForecast;
    private ListView mLvDailyForecast;
    private List<Weather.HeWeatherBean.DailyForecastBean> mDailyForecastList = new ArrayList<>();//每日预报
    private List<Weather.HeWeatherBean.HourlyForecastBean> mHourlyForecastList = new ArrayList<>();//每小时预报
    private DailyForecastAdapter mDailyForecastAdapter;
    private HourlyForecastAdapter mHourlyForecastAdapter;
    private Weather.HeWeatherBean mHeWeather;
    private RelativeLayout mRlAqi;

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

    /**
     * 初始化视图
     */
    private void initView() {
        mRlAqi = (RelativeLayout) mRootView.findViewById(R.id.rl_aqi);//aqi的根布局
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
        mLvDailyForecast = (ListView) mRootView.findViewById(R.id.lv_daily_forecast);
        mDailyForecastAdapter = new DailyForecastAdapter();
        mLvDailyForecast.setAdapter(mDailyForecastAdapter);

        //hourly_forecast
        mLvHourlyForecast = (ListView) mRootView.findViewById(R.id.lv_hourly_forecast);
        mHourlyForecastAdapter = new HourlyForecastAdapter();
        mLvHourlyForecast.setAdapter(mHourlyForecastAdapter);

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
//        if (mPreferences != null) {//如果mPreferences
//            String dataResponse = mPreferences.getString("dataResponse", null);
//            long currentTime = mPreferences.getLong("currentTime", 0);
//            long intervalTime = SystemClock.currentThreadTimeMillis() - currentTime;
//            LogUtil.d(TAG, "intervalTime==" + intervalTime);
//            if (dataResponse != null && intervalTime < 1000 * 5) {//如果dataResponse存在(如果内存中有数据)
//                try {
//                    handleResponseStr(dataResponse);
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            } else {
//
//            }
//        }


        //从ChooseCountyActivity拿到weatherId
        String weatherId = getActivity().getIntent().getStringExtra("weatherId");
        // TODO: 2017/6/25
//                String url = "http://guolin.tech/api/weather?cityid=CN101190401&key=e9d466dcc631437c9d49dafcb6b8fc20";
        String url = "http://guolin.tech/api/weather?cityid=" + weatherId + "&key=e9d466dcc631437c9d49dafcb6b8fc20";
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
                    //返回的是HeWeather
                    mHeWeather = handleResponseStr(responsStr);
                    mDailyForecastList = mHeWeather.getDaily_forecast();
                    mHourlyForecastList = mHeWeather.getHourly_forecast();
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            //刷新UI
                            updateView();
                            mDailyForecastAdapter.notifyDataSetChanged();
                            mHourlyForecastAdapter.notifyDataSetChanged();
                        }
                    });
                    LogUtil.d(TAG, mHeWeather.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 刷新UI
     */
    private void updateView() {
        //数据和视图的绑定
        //now
//         mTvNow.setText(mHeWeather.getNow().toString());
        mTvCnodTxt.setText(mHeWeather.getNow().getCond().getTxt());
        mTvTmp.setText(mHeWeather.getNow().getTmp());
        mTvWind.setText(mHeWeather.getNow().getWind().getDir());

        //basic
        //mTvBasic.setText(mHeWeather.getBasic().toString());
        mTvBasicCity.setText(mHeWeather.getBasic().getCity());
        mTvBasicUpdateTime.setText(mHeWeather.getBasic().getUpdate().getLoc());

        //DailyForecast
        //mTvDailyForecast.setText(mHeWeather.getDaily_forecast().toString());

        //HourlyForecast
        //mTvHourlyForecast.setText(mHeWeather.getHourly_forecast().toString());

        //status
        mTvStatus.setText(mHeWeather.getStatus());

        //aqi,有的城市的返回数据没有aqi这一项
        Weather.HeWeatherBean.AqiBean aqi = mHeWeather.getAqi();
        LogUtil.d(TAG, mHeWeather.toString());
        if (aqi != null) {//如果解析出来的数据有aqi这一项
            mTvAqiTxt.setText("Aqi:" + aqi.getCity().getAqi());
            mTvAqiQlty.setText("Qlty.:" + aqi.getCity().getQlty());
            mTvAqiPm10.setText("Pm10:" + aqi.getCity().getPm10());
            mTvAqiPm25.setText("Pm25:" + aqi.getCity().getPm25());
        } else {//如果解析出来的数据没有aqi这一项
//            mTvAqi.setText("aqi");
            mRlAqi.setVisibility(View.GONE);
        }

        //suggestion
        //mTvSuggestion.setText(mHeWeather.getSuggestion().toString());
        mTvComf.setText(mHeWeather.getSuggestion().getComf().getBrf() + ":" + mHeWeather.getSuggestion().getComf().getTxt());
        mTvCw.setText(mHeWeather.getSuggestion().getCw().getBrf() + ":" + mHeWeather.getSuggestion().getCw().getTxt());
        mTvDrsg.setText(mHeWeather.getSuggestion().getDrsg().getBrf() + ":" + mHeWeather.getSuggestion().getDrsg().getTxt());
        mTvFlu.setText(mHeWeather.getSuggestion().getFlu().getBrf() + ":" + mHeWeather.getSuggestion().getFlu().getTxt());
        mTvSport.setText(mHeWeather.getSuggestion().getSport().getBrf() + ":" + mHeWeather.getSuggestion().getSport().getTxt());
        mTvTrav.setText(mHeWeather.getSuggestion().getTrav().getBrf() + ":" + mHeWeather.getSuggestion().getTrav().getTxt());
        mTvUv.setText(mHeWeather.getSuggestion().getUv().getBrf() + ":" + mHeWeather.getSuggestion().getUv().getTxt());
    }

    /**
     * @param responsStr 网络请求返回的所有数据,根数据Weather
     *                   传入的是Weather,返回的是
     * @throws JSONException
     */
    private Weather.HeWeatherBean handleResponseStr(String responsStr) throws JSONException {
        JSONObject jsonObject = new JSONObject(responsStr);
        JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
        String jsonString = jsonArray.getJSONObject(0).toString();
        Gson gson = new Gson();
        return gson.fromJson(jsonString, Weather.HeWeatherBean.class);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        LogUtil.d(TAG, "onActivityCreated");
        super.onActivityCreated(savedInstanceState);
    }

    /**
     * DailyForecastAdapter
     */
    private class DailyForecastAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mDailyForecastList != null) {
                return mDailyForecastList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mDailyForecastList != null) {
                return mDailyForecastList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mDailyForecastList != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = View.inflate(getContext(), R.layout.lv_item_forecast_daily, null);
                holder = new ViewHolder();
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date_daily);
                holder.tvCnod = (TextView) convertView.findViewById(R.id.tv_cnod_daily);
                holder.tvTmpMax = (TextView) convertView.findViewById(R.id.tv_tmp_max_daily);
                holder.tvTmpMin = (TextView) convertView.findViewById(R.id.tv_tmp_min_daily);
                holder.tvWind = (TextView) convertView.findViewById(R.id.tv_wind_daily);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Weather.HeWeatherBean.DailyForecastBean dailyForecastBean = mDailyForecastList.get(position);
            holder.tvDate.setText(dailyForecastBean.getDate());
            holder.tvCnod.setText(dailyForecastBean.getCond().getTxt_d());
            holder.tvTmpMax.setText(dailyForecastBean.getTmp().getMax());
            holder.tvTmpMin.setText(dailyForecastBean.getTmp().getMin());
            holder.tvWind.setText(dailyForecastBean.getWind().getDir());
            return convertView;
        }
    }

    /**
     * HourlyForecastAdapter
     */
    private class HourlyForecastAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mHourlyForecastList != null) {
                return mHourlyForecastList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mHourlyForecastList != null) {
                return mHourlyForecastList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mHourlyForecastList != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                //虽然forecast_daily和forecast_hourly对应的数据结构不一样,但是布局展示的时候还是可以用同一个条目布局
                convertView = View.inflate(getContext(), R.layout.lv_item_forecast_hourly, null);
                holder = new ViewHolder();
                holder.tvDate = (TextView) convertView.findViewById(R.id.tv_date_daily);
                holder.tvCnod = (TextView) convertView.findViewById(R.id.tv_cnod_daily);
                holder.tvTmpMax = (TextView) convertView.findViewById(R.id.tv_tmp_max_daily);
                holder.tvTmpMin = (TextView) convertView.findViewById(R.id.tv_tmp_min_daily);
                holder.tvWind = (TextView) convertView.findViewById(R.id.tv_wind_daily);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Weather.HeWeatherBean.HourlyForecastBean hourlyForecastBean = mHourlyForecastList.get(position);
            holder.tvDate.setText(hourlyForecastBean.getDate().substring(10, 15));
            holder.tvCnod.setText(hourlyForecastBean.getCond().getTxt());
            //每小时预报中只有气温,没有最高气温和最低气温的分别,所以两者的值一样
            holder.tvTmpMax.setText(hourlyForecastBean.getTmp());
            holder.tvTmpMin.setText(hourlyForecastBean.getTmp());
            holder.tvWind.setText(hourlyForecastBean.getWind().getDir());
            return convertView;
        }
    }

    class ViewHolder {
        TextView tvDate;
        TextView tvCnod;
        TextView tvTmpMax;
        TextView tvTmpMin;
        TextView tvWind;
    }
}
