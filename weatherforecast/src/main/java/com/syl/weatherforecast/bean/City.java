package com.syl.weatherforecast.bean;

import java.io.Serializable;

/**
 * Created by Bright on 2017/6/25.
 *
 * @Describe
 * @Called
 */

public class City implements Serializable {

    /**
     * id : 10
     * name : 哈尔滨
     */

    private int id;
    private String name;

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

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
