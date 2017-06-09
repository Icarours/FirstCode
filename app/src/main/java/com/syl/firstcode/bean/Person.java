package com.syl.firstcode.bean;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Bright on 2017/6/8.
 *
 * @Describe
 * bean实例,实现Parcelable,方便实例对象传递
 * @Called
 */

public class Person implements Parcelable {
    private String name;
    private int age;
    private int height;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", height=" + height +
                '}';
    }

    public static final Creator<Person> CREATOR = new Creator<Person>() {
        @Override
        public Person createFromParcel(Parcel in) {
            Person person = new Person();
            person.name = in.readString();//读取name
            person.age = in.readInt();//读取age
            person.height = in.readInt();//读取height
            return person;
        }

        @Override
        public Person[] newArray(int size) {
            return new Person[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);//写出name
        dest.writeInt(age);//写出age
        dest.writeInt(height);//写出height
    }
}
