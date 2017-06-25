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
import com.syl.weatherforecast.bean.Province;
import com.syl.weatherforecast.config.Constant;
import com.syl.weatherforecast.utils.HttpUtil;
import com.syl.weatherforecast.utils.LogUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * author   Bright
 * date     2017/6/25 18:55
 * desc
 * 发起网络请求
 * 选择省级行政单位
 */
public class ChooseProvinceActivity extends AppCompatActivity {

    private static final String TAG = ChooseProvinceActivity.class.getSimpleName();
    private List<Province> mProvince = new ArrayList<>();
    private ListView mLvProvince;
    private LvProvinceAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_province);
        initData();
        mLvProvince = (ListView) findViewById(R.id.lv_province);
        mAdapter = new LvProvinceAdapter();
        mLvProvince.setAdapter(mAdapter);
        mLvProvince.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(ChooseProvinceActivity.this, ChooseCityActivity.class);
                Province province = mProvince.get(position);
                //通过Intent将引用数据类型数据传递过去
                intent.putExtra("province", province);
                startActivity(intent);
                finish();
            }
        });
    }

    /**
     * 数据处理完成之后,刷新数据,此时在子线程中,刷新UI必须在UI线程中完成
     */
    private void initData() {
//        String address = "http://guolin.tech/api/china/";
        HttpUtil.sendOkHttpRequest(Constant.URL_ADDRESS_PROVINCE, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                LogUtil.d(TAG, string);
                mProvince = handleResponseProvince(string);
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
     * 处理省返回的数据
     *
     * @param string
     */
    private List<Province> handleResponseProvince(String string) {
        return new Gson().fromJson(string, new TypeToken<List<Province>>() {
        }.getType());
    }

    private class LvProvinceAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            if (mProvince != null) {
                return mProvince.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mProvince != null) {
                return mProvince.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mProvince != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(ChooseProvinceActivity.this, R.layout.lv_item_province, null);
                holder.tvProvince = (TextView) convertView.findViewById(R.id.tv_province);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            Province province = mProvince.get(position);
            holder.tvProvince.setText(province.getName());
            return convertView;
        }

        class ViewHolder {
            TextView tvProvince;
        }
    }
}
