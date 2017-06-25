package com.syl.weatherforecast.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syl.weatherforecast.R;
import com.syl.weatherforecast.bean.City;
import com.syl.weatherforecast.bean.Province;
import com.syl.weatherforecast.config.Constant;
import com.syl.weatherforecast.utils.HttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author   Bright
 * date     2017/6/25 19:39
 * desc
 * 选择市级行政单位
 */
public class ChooseCityActivity extends AppCompatActivity {

    private String mAddressCity;
    private List<City> mCityList;
    private CityAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_city);
        Province province = (Province) getIntent().getSerializableExtra("province");
        int provinceId = province.getId();

        mAddressCity = Constant.URL_ADDRESS_PROVINCE + provinceId;
        initData();
        ListView lvCity = (ListView) findViewById(R.id.lv_city);
        mAdapter = new CityAdapter();
        lvCity.setAdapter(mAdapter);
        lvCity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChooseCityActivity.this, ChooseCountyActivity.class);
                City city = mCityList.get(position);
                //通过Intent将引用数据类型数据传递过去
                intent.putExtra("city", city);
                intent.putExtra("addressCity",mAddressCity);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 初始化数据
     * 数据处理完成之后,刷新数据,此时在子线程中,刷新UI必须在UI线程中完成
     */
    private void initData() {
        HttpUtil.sendOkHttpRequest(mAddressCity, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponseCity = response.body().string();
                mCityList = handleResponseCity(stringResponseCity);
                //数据处理完成之后,刷新数据,此时在子线程中,刷新UI必须在UI线程中完成
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mAdapter.notifyDataSetChanged();
                    }
                });
            }
        });
    }

    /**
     * 使用Gson解析网络请求返回的数据
     *
     * @param stringResponseCity
     */
    private List<City> handleResponseCity(String stringResponseCity) {
        return new Gson().fromJson(stringResponseCity, new TypeToken<List<City>>() {
        }.getType());
    }

    private class CityAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mCityList != null) {
                return mCityList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mCityList != null) {
                mCityList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mCityList != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ChooseCityActivity.this, R.layout.lv_item_province, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_province);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(mCityList.get(position).getName());
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv;
    }
}
