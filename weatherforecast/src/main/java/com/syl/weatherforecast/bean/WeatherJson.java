package com.syl.weatherforecast.bean;

import java.util.List;

/**
 * Created by Bright on 2017/6/23.
 *
 * @Describe
 * @Called
 */

public class WeatherJson {

    private List<HeWeather> mHeHeWeather;

    public List<HeWeather> getHeWeather() {
        return mHeHeWeather;
    }

    public void setHeWeather(List<HeWeather> heHeWeather) {
        this.mHeHeWeather = heHeWeather;
    }

    @Override
    public String toString() {
        return "WeatherJson{" +
                "mHeHeWeather=" + mHeHeWeather +
                '}';
    }
}
