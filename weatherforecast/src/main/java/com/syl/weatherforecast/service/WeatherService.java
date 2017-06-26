package com.syl.weatherforecast.service;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.os.SystemClock;
import android.preference.PreferenceManager;

import com.syl.weatherforecast.bean.Weather;
import com.syl.weatherforecast.utils.HttpUtil;
import com.syl.weatherforecast.utils.LogUtil;
import com.syl.weatherforecast.utils.Utility;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author   Bright
 * date     2017/6/25 15:23
 * desc
 * <p>
 * 网络请求的放在Service中完成,将请求获得的数据缓存在SharedPreference中
 */
// TODO: 2017/6/25  (这种方案是原作者的,暂时没有完全领会,就不往下写了,这个类暂时不用)
public class WeatherService extends Service {
    public static final String TAG = WeatherService.class.getSimpleName();

    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        LogUtil.d(TAG, "onBind");
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtil.d(TAG, "onStartCommand");
        updateWeather();
        updateBingPic();

        AlarmManager manager = (AlarmManager) getSystemService(ALARM_SERVICE);
        long anHour = 1000 * 60 * 60;//一个小时
        long triggerTime = SystemClock.elapsedRealtime() + anHour;
        PendingIntent pi = PendingIntent.getService(this, 0, new Intent(this, WeatherService.class), 0);
//        manager.cancel(pi);
        manager.set(AlarmManager.ELAPSED_REALTIME, triggerTime, pi);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 更新天气数据
     */
    private void updateWeather() {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String weatherString = preferences.getString("weather", null);
        if (weatherString != null) {
            //有缓存是,直接解析数据
            Weather.HeWeatherBean heWeather = Utility.handleWeatherResponse(weatherString);
            String weatherId = heWeather.getBasic().getId();
//            String url = "http://guolin.tech/api/weather?weatherId="+cityId+"&key=e9d466dcc631437c9d49dafcb6b8fc20";
            String url = "http://guolin.tech/api/weather?weatherId=CN101190401&key=e9d466dcc631437c9d49dafcb6b8fc20";
            HttpUtil.sendOkHttpRequest(url, new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    String stringResponse = response.body().string();
                    Weather.HeWeatherBean heWeather = Utility.handleWeatherResponse(stringResponse);
                    String weatherId = heWeather.getBasic().getId();
                    if (heWeather != null && "ok".equals(heWeather.getStatus())) {
                        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(WeatherService.this).edit();
                        editor.putString("weather", stringResponse);
                        editor.putString("weatherId", weatherId);
                        editor.apply();
                    }
                }
            });
        }
    }

    /**
     * 更新图片
     */
    private void updateBingPic() {
        String requestBingPic = "http://guolin.tech/api/bing_pic";
        HttpUtil.sendOkHttpRequest(requestBingPic, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String bingPic = response.body().string();
                SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(WeatherService.this).edit();
                edit.putString("bingPic", bingPic);
                edit.apply();
            }
        });
    }

    @Override
    public void onCreate() {
        LogUtil.d(TAG, "onCreate");
        super.onCreate();
    }
}
