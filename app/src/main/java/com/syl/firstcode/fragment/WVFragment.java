package com.syl.firstcode.fragment;

import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;

/**
 * Created by Bright on 2017/5/29.
 *
 * @Describe
 * webView的使用
 * @Called
 */

public class WVFragment extends BaseFragment {

    private View mRootView;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_wv, null);
        WebView wv = (WebView) mRootView.findViewById(R.id.wv);
        wv.getSettings().setJavaScriptEnabled(true);
        wv.setWebViewClient(new WebViewClient());
        wv.loadUrl("http://www.baidu.com");//URL必须带上http://,否则显示不出来
        return mRootView;
    }
}
