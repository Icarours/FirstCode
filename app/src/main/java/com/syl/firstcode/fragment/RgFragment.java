package com.syl.firstcode.fragment;

import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;

/**
 * Created by Bright on 2017/5/19.
 *
 * @Describe
 * RadioButton/RadioGroup举例
 * @Called
 */

public class RgFragment extends BaseFragment {
    private TextView myTextView;
    private RadioButton chinaBtn;
    private RadioButton ukBtn;
    private RadioButton usaBtn;
    private RadioGroup rg;
    public static final String ARG_FILE_NUMBER = "file2";
    private View mRootView;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_rg, null);
        //通过ID找到TextView
        myTextView = (TextView) mRootView.findViewById(R.id.myTextView);
        //通过ID找到RadioButton
        chinaBtn = (RadioButton) mRootView.findViewById(R.id.china_Button);
        ukBtn = (RadioButton)mRootView.findViewById(R.id.uk_Button);
        usaBtn = (RadioButton) mRootView.findViewById(R.id.usa_Button);
        //通过ID找到RadioGroup
        rg = (RadioGroup) mRootView.findViewById(R.id.rBtnGroup);
        //只要对RadioGroup进行监听
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // TODO Auto-generated method stub
                if(R.id.china_Button == checkedId){
                    myTextView.setText("您选择的国家是：" + chinaBtn.getText().toString());
                    System.out.println("您选择的国家是：" + chinaBtn.getText().toString());
                }
                else if(R.id.uk_Button == checkedId){
                    myTextView.setText("您选择的国家是：" + ukBtn.getText().toString());
                    System.out.println("您选择的国家是：" + chinaBtn.getText().toString());
                }
                else if(R.id.usa_Button == checkedId){
                    myTextView.setText("您选择的国家是：" + usaBtn.getText().toString());
                    System.out.println("您选择的国家是：" + chinaBtn.getText().toString());
                }
            }
        });
        return mRootView;
    }
}
