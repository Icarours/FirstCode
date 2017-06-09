package com.syl.firstcode.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.syl.firstcode.R;
import com.syl.firstcode.bean.App;
import com.syl.firstcode.bean.Person;
import com.syl.firstcode.utils.LogUtil;

public class SecondActivity extends AppCompatActivity {

    private static final String TAG = SecondActivity.class.getSimpleName();
    private TextView mTvContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        mTvContent = (TextView) findViewById(R.id.tv_content);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        if (getIntent().getFlags() == 1) {//如果flag是1传递的数据是Person的实例
            Person person = getIntent().getParcelableExtra("person_data");
            mTvContent.setText(person.toString());
            LogUtil.i(TAG,person.toString());
        } else if (getIntent().getFlags() == 2) {//如果flag是2传递的数据是App的实例
            App app = (App) getIntent().getSerializableExtra("app_data");
            mTvContent.setText(app.toString());
            LogUtil.w(TAG,app.toString());
        }
    }
}
