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
import com.syl.weatherforecast.MainActivity;
import com.syl.weatherforecast.R;
import com.syl.weatherforecast.bean.City;
import com.syl.weatherforecast.bean.County;
import com.syl.weatherforecast.utils.HttpUtil;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author   Bright
 * date     2017/6/25 19:55
 * desc
 * 获取县级行政单位信息
 */
public class ChooseCountyActivity extends AppCompatActivity {

    private CountyAdapter mAdapter;
    private String mAddressCounty;
    private List<County> mCountyList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_county);
        Intent intent = getIntent();
        String addressCity = intent.getStringExtra("addressCity");
        City city = (City) intent.getSerializableExtra("city");
        int cityId = city.getId();
        mAddressCounty = addressCity + "/" + cityId;

        initData();
        ListView lvCounty = (ListView) findViewById(R.id.lv_county);
        mAdapter = new CountyAdapter();
        lvCounty.setAdapter(mAdapter);
        lvCounty.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent i = new Intent(ChooseCountyActivity.this, MainActivity.class);
                County county = mCountyList.get(position);
                String weatherId = county.getWeather_id();
                i.putExtra("weatherId", weatherId);
                startActivity(i);
                finish();
            }
        });
    }

    /**
     * 初始化数据
     */
    private void initData() {
        HttpUtil.sendOkHttpRequest(mAddressCounty, new Callback() {

            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String stringResponseCounty = response.body().string();
                mCountyList = handleResponseCounty(stringResponseCounty);
                //此时数据在子线程中,需要刷新,UI线程才能拿到数据
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
     * 解析网络请求返回的数据
     *
     * @param stringResponseCounty
     * @return
     */
    private List<County> handleResponseCounty(String stringResponseCounty) {
        return new Gson().fromJson(stringResponseCounty, new TypeToken<List<County>>() {
        }.getType());
    }

    /**
     *
     */
    class CountyAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mCountyList != null) {
                return mCountyList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mCountyList != null) {
                return mCountyList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mCountyList != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ChooseCountyActivity.this, R.layout.lv_item_province, null);
                holder.tv = (TextView) convertView.findViewById(R.id.tv_province);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(mCountyList.get(position).getName());
            return convertView;
        }
    }

    class ViewHolder {
        TextView tv;
    }
}
