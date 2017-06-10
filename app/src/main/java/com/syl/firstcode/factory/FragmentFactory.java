package com.syl.firstcode.factory;

import android.util.SparseArray;

import com.syl.firstcode.base.BaseFragment;
import com.syl.firstcode.fragment.AlarmFragment;
import com.syl.firstcode.fragment.CPFragment;
import com.syl.firstcode.fragment.FileFragment;
import com.syl.firstcode.fragment.FileFragment1;
import com.syl.firstcode.fragment.FileFragment2;
import com.syl.firstcode.fragment.HttpURLFragment;
import com.syl.firstcode.fragment.OkHttpJsonFragment;
import com.syl.firstcode.fragment.OkHttpXmlFragment;
import com.syl.firstcode.fragment.RgFragment;
import com.syl.firstcode.fragment.WVFragment;

/**
 * Created by Bright on 2017/5/20.
 *
 * @Describe Fragment 的工厂
 * 缓存管理,如果工厂缓存中有指定的Fragment对象,就直接从缓存拿到该对象
 * 展示巨日内容的Fragment
 * @Called
 */

public class FragmentFactory {
    //创建Fragment的缓存
    public static SparseArray<BaseFragment> mFragmentCache = new SparseArray<>();

    public static BaseFragment createFragment(int position) {
        BaseFragment baseFragment;
        //先从缓存中拿baseFragment,如果拿到的baseFragment不是null,就返回该对象.
        baseFragment = mFragmentCache.get(position);
        if (baseFragment != null) {
            return baseFragment;
        }
        //如果baseFragment为null,根据position创建对应的fragment
        switch (position) {
            case 0:
                baseFragment = new FileFragment();//0文件存储(openFileOutput)
                break;
            case 1:
                baseFragment = new FileFragment1();//1文件存储sp
                break;
            case 2:
                baseFragment = new FileFragment2();//2文件存储sqlite
                break;
            case 3:
                baseFragment = new RgFragment();//3RadioButton/RadioGroup
                break;
            case 4:
                baseFragment = new CPFragment();//4ContentProvider
                break;
            case 5:
                baseFragment = new WVFragment();//5WebView
                break;
            case 6:
                baseFragment = new HttpURLFragment();//6HttpURLConnection
                break;
            case 7:
                baseFragment = new OkHttpXmlFragment();//7okHttp_xml
                break;
            case 8:
                baseFragment = new OkHttpJsonFragment();//8okHttp_json
                break;
            case 9:
                baseFragment = new AlarmFragment();//9alarm机制
                break;
            default:
                break;
        }
        mFragmentCache.put(position, baseFragment);
        return baseFragment;
    }
}
