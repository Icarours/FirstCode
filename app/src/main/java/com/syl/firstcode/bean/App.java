package com.syl.firstcode.bean;

import java.io.Serializable;

/**
 * Created by Bright on 2017/5/30.
 *
 * @Describe app bean实例,实现Serializable,方便实例对象传递
 * @Called
 */

public class App implements Serializable{
    private String id;
    private String name;
    private String version;

    @Override
    public String toString() {
        return "App{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", version='" + version + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }
}
