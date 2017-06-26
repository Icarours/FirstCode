package com.syl.weatherforecast.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.syl.weatherforecast.bean.Weather;
import com.syl.weatherforecast.db.City;
import com.syl.weatherforecast.db.County;
import com.syl.weatherforecast.db.Province;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Bright on 2017/6/10.
 *
 * @Describe 解析省市区信息
 * @Called
 */

public class Utility {
    /**
     * 解析省的信息
     *
     * @param response
     * @return
     */
    public static boolean handleProvinceResponse(String response) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allProvinces = new JSONArray(response);
                for (int i = 0; i < allProvinces.length(); i++) {
                    JSONObject provinceObject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setId(provinceObject.getInt("id"));
                    province.setProvinceName(provinceObject.getString("name"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析市的信息
     *
     * @param response
     * @param provinceId
     * @return
     */
    public static boolean handleCityResponse(String response, int provinceId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCities = new JSONArray(response);
                for (int i = 0; i < allCities.length(); i++) {
                    City city = new City();
                    JSONObject cityObject = allCities.getJSONObject(i);
                    city.setId(cityObject.getInt("id"));
                    city.setCityName(cityObject.getString("name"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析县区的信息
     *
     * @param response
     * @param cityId
     * @return
     */
    public static boolean handleCountyResponse(String response, int cityId) {
        if (!TextUtils.isEmpty(response)) {
            try {
                JSONArray allCounties = new JSONArray(response);
                for (int i = 0; i < allCounties.length(); i++) {
                    County county = new County();
                    JSONObject countyObject = allCounties.getJSONObject(i);
                    county.setCountyName(countyObject.getString("name"));
                    county.setWeatherId(countyObject.getString("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 解析天气信息
     * 将返回的JsonString解析成实体类
     *
     * @param weatherString
     * @return
     */
    public static Weather.HeWeatherBean handleWeatherResponse(String weatherString) {
        try {
            JSONObject jsonObject = new JSONObject(weatherString);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String jsonString = jsonArray.getJSONObject(0).toString();
            Gson gson = new Gson();
            return gson.fromJson(jsonString, Weather.HeWeatherBean.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }
}
