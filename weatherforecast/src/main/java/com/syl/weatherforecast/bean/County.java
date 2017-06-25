package com.syl.weatherforecast.bean;

import java.io.Serializable;

/**
 * Created by Bright on 2017/6/25.
 *
 * @Describe 县级行政单位
 * @Called
 */

public class County implements Serializable {

    /**
     * id : 180
     * name : 长春
     * weather_id : CN101060101
     */

    private int id;
    private String name;
    private String weather_id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWeather_id() {
        return weather_id;
    }

    public void setWeather_id(String weather_id) {
        this.weather_id = weather_id;
    }
}
