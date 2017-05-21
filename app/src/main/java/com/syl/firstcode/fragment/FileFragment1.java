package com.syl.firstcode.fragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;

/**
 * Created by Bright on 2017/5/19.
 *
 * @Describe 文件, 数据存储
 * SharedPreference
 * @Called
 */

public class FileFragment1 extends BaseFragment implements View.OnClickListener {

    public static final String ARG_FILE_NUMBER = "file1";
    private TextView mTvContent;
    private EditText mEtName;
    private View mRootView;
    private EditText mEtAge;
    private CheckBox mCbIsMarried;
    private RadioGroup mRgGender;
    private String mGender;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_file1, null);
        mCbIsMarried = (CheckBox) mRootView.findViewById(R.id.cb_is_married);
        mRgGender = (RadioGroup) mRootView.findViewById(R.id.rg_gender);
        mTvContent = (TextView) mRootView.findViewById(R.id.tv_content);
        mEtName = (EditText) mRootView.findViewById(R.id.et_name);
        mEtAge = (EditText) mRootView.findViewById(R.id.et_age);
        return mRootView;
    }

    @Override
    public void initListener() {
        mRootView.findViewById(R.id.btn_save_file).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_read_file).setOnClickListener(this);
        mRgGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rb_male:
                        System.out.println("male--------");
                        mGender = "male";
                        break;
                    case R.id.rb_female:
                        System.out.println("female--------");
                        mGender = "female";
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save_file:
                saveFile();
                break;
            case R.id.btn_read_file:
                readFile();
                break;
            default:
                break;
        }
    }

    /**
     * 读取保存的文件
     */
    private void readFile() {
        StringBuffer sb = new StringBuffer();
        SharedPreferences sp = getActivity().getSharedPreferences("data_sp", Context.MODE_PRIVATE);
        String name = sp.getString("name", "");
        String age = sp.getString("age", "");
        String gender = sp.getString("gender", "");
        boolean isMarried = sp.getBoolean("isMarried", false);
        sb.append(name).append(age).append(gender);
        mCbIsMarried.setChecked(isMarried);
        mTvContent.setText(sb.toString());

        if ("male".equals(gender)) {
            mRgGender.check(R.id.rb_male);
        } else {
            mRgGender.check(R.id.rb_female);
        }
    }

    /**
     * 保存文件
     */
    private void saveFile() {
        String name = mEtName.getText().toString();
        String age = mEtAge.getText().toString();
        boolean checked = mCbIsMarried.isChecked();

        SharedPreferences preferences = getActivity().getSharedPreferences("data_sp", Context.MODE_PRIVATE);
        SharedPreferences.Editor edit = preferences.edit();
        edit.putString("name", name);
        edit.putString("age", age);
        edit.putString("gender", mGender);
        edit.putBoolean("isMarried", checked);
        edit.apply();
    }

    /**
     * 退出的时候保存一下内容
     */
    @Override
    public void onDestroy() {
        saveFile();
        super.onDestroy();
    }
}
