package com.syl.weatherforecast;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.syl.weatherforecast.activity.ChooseProvinceActivity;
import com.syl.weatherforecast.fragment.ContentFragment;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * author   Bright
 * date     2017/6/23 0:26
 * desc
 * 天气预报的主界面
 */
public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ImageView mIvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        //让通知栏的背景和APP的背景融为一体
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_main);//根布局使用FrameLayout,如此才能做出层次透明的效果

//        Intent intent = new Intent(this, WeatherService.class);
//        startService(intent);

        initView();
        findViewById(R.id.btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ChooseProvinceActivity.class);
                startActivity(intent);
            }
        });
    }

    /**
     * 主体内容使用Fragment来展示
     */
    private void initView() {
        updatePic();
        mIvContent = (ImageView) findViewById(R.id.iv_content);
//        FrameLayout flContent = (FrameLayout) findViewById(R.id.fl_content);
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, new ContentFragment());
        transaction.commit();
    }

    /**
     * 加载背景图片
     */
    private void updatePic() {
        final SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(this);
        String picUrl = "http://guolin.tech/api/bing_pic";
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(picUrl)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String mPicUrl2 = response.body().string();
                SharedPreferences.Editor edit = preferences.edit();//存缓存
                edit.putString("mPicUrl", mPicUrl2);
                edit.apply();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Glide.with(MainActivity.this).load(mPicUrl2).into(mIvContent);
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_choose_city:
                Intent intent = new Intent(this, ChooseProvinceActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
