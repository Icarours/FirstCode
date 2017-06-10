package com.syl.firstcode.fragment;

import android.app.ProgressDialog;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;
import com.syl.firstcode.bean.App;
import com.syl.firstcode.config.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Bright on 2017/6/5.
 *
 * @Describe okhttp, json解析, Gson解析
 * @Called
 */

public class OkHttpJsonFragment extends BaseFragment implements View.OnClickListener {
    public static final String TAG = OkHttpJsonFragment.class.getSimpleName();
    private View mRootView;
    private TextView mTvContent;
    private ArrayList<App> mAppArrayList;//存放解析出来的数据集

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_okhttp_json, null);
        mTvContent = (TextView) mRootView.findViewById(R.id.tv_content);
        return mRootView;
    }

    @Override
    public void initListener() {
        mRootView.findViewById(R.id.btn_load_json).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_load_gson).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_json:
                loadJson();
                mAppArrayList = null;//置空集合
                break;
            case R.id.btn_load_gson:
                loadGson();
                mAppArrayList = null;//置空集合
                break;
            default:
                break;
        }
    }

    /**
     * json解析
     */
    private void loadJson() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("loading");
        progressDialog.setMessage("please wait,loading .....");
        progressDialog.show();
        //主要是为了分辨使用了不同的数据解析方法
        mTvContent.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        OkHttpClient okHttpClient = new OkHttpClient();
        String url = Constant.URLS.JSON_URL;
        Request request = new Request.Builder()
                .url(url)
                .get()
                .build();
        Call call = okHttpClient.newCall(request);//异步网络请求
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Toast.makeText(getContext(), "网络请求失败", Toast.LENGTH_SHORT).show();
                Log.d(TAG, "网络请求失败..");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                getActivity().runOnUiThread(new Runnable() {//主线程中更新UI
                    @Override
                    public void run() {
                        parseJSONWithJSONObject(response);//使用JSONArray,JSONObject解析数据
                        StringBuilder stringBuilder = new StringBuilder();
                        for (int i = 0; i < mAppArrayList.size(); i++) {
                            stringBuilder.append(mAppArrayList.get(i) + "\r\n");
                        }
                        mTvContent.setText(stringBuilder.toString());
                        progressDialog.dismiss();
                    }
                });
            }
        });
    }

    /**
     * 使用JSONArray,JSONObject解析数据
     *
     * @param response
     */
    private void parseJSONWithJSONObject(Response response) {
        mAppArrayList = new ArrayList<>();
        try {
            String jsonString = response.body().string();
            JSONArray jsonArray = new JSONArray(jsonString);
            for (int i = 0; i < jsonArray.length(); i++) {
                App app = new App();
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                app.setId(jsonObject.getString("id"));
                app.setVersion(jsonObject.getString("version"));
                app.setName(jsonObject.getString("name"));
                mAppArrayList.add(app);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    /**
     * Gson解析,开启子线程,异步网络请求
     */
    private void loadGson() {
        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("loading");
        progressDialog.setMessage("please wait,loading .....");
        progressDialog.show();
        //主要是为了分辨使用了不同的数据解析方法
        mTvContent.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        new Thread() {
            @Override
            public void run() {
                try {
                    OkHttpClient okHttpClient = new OkHttpClient();
                    String url = Constant.URLS.JSON_URL;
                    Request request = new Request.Builder()
                            .url(url)
                            .build();
                    Response response = okHttpClient.newCall(request).execute();
                    parseJSONWithGson(response);//使用Gson解析
                    final StringBuilder stringBuilder = new StringBuilder();
                    for (int i = 0; i < mAppArrayList.size(); i++) {
                        stringBuilder.append(mAppArrayList.get(i) + "\r\n");
                    }
                    getActivity().runOnUiThread(new Runnable() {//主线程中更新UI
                        @Override
                        public void run() {
                            mTvContent.setText(stringBuilder.toString());
                            progressDialog.dismiss();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 使用Gson解析数据
     *
     * @param response
     */
    private void parseJSONWithGson(Response response) {
        mAppArrayList = new ArrayList<>();
        try {
            String jsonString = response.body().string();
            Gson gson = new Gson();
            //如果需要解析的是一段json数组,我们需要借助TypeToken将期望解析成的数据类型闯入到fromJson()方法中.
            mAppArrayList = gson.fromJson(jsonString, new TypeToken<List<App>>() {
            }.getType());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
