package com.syl.firstcode.interface_;

/**
 * Created by Bright on 2017/6/6.
 *
 * @Describe 网络请求的回调接口
 * @Called
 */

public interface HttpCallbackListener {
    void onSuccess(String response);

    void onError(Exception e);
}
