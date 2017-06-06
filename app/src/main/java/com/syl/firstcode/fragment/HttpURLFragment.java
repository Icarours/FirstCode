package com.syl.firstcode.fragment;

import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.webkit.WebView;
import android.widget.TextView;

import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Bright on 2017/5/29.
 *
 * @Describe httpUrlConnection的get请求
 * httpUrlConnection的post请求
 * @Called
 */

public class HttpURLFragment extends BaseFragment implements View.OnClickListener {

    private View mRootView;
    private TextView mTvContent;
    private WebView mWv;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_http_url, null);
        mWv = (WebView) mRootView.findViewById(R.id.wv);
        mTvContent = (TextView) mRootView.findViewById(R.id.tv_content);
        mTvContent.setMovementMethod(ScrollingMovementMethod.getInstance());
        return mRootView;
    }

    @Override
    public void initListener() {
        mRootView.findViewById(R.id.btn_load).setOnClickListener(this);
    }

    public void load() {
        new Thread() {

            HttpURLConnection connection;
            BufferedReader reader;

            @Override
            public void run() {
                try {
                    URL url = new URL("https://www.baidu.com");
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setConnectTimeout(6000);
                    connection.setReadTimeout(6000);
                    /*------------------ 如果是post请求start -----------------*/
                    // postRequest(connection);//post请求
                    /*------------------ 如果是post请求end -----------------*/
                    int responseCode = connection.getResponseCode();
                    InputStream inputStream;
                    String location = connection.getHeaderField("Location");
                    System.out.println("location==" + location);
                    if (responseCode == 200) {
                        inputStream = connection.getInputStream();
                    } else {
                        System.out.println("responseCode==" + responseCode);
                        return;
                    }
                    reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    showResponse(response.toString());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    if (reader != null) {
                        try {
                            reader.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }

        }.start();
    }

    /**
     * post请求
     *
     * @param connection
     * @throws IOException
     */
    private void postRequest(HttpURLConnection connection) throws IOException {
        connection.setDoOutput(true);
        connection.setRequestMethod("POST");
        DataOutputStream dataOutputStream = new DataOutputStream(connection.getOutputStream());
        dataOutputStream.writeBytes("username=admin&password=222");
    }

    private void showResponse(final String response) {
        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mTvContent.setText(response);
                //mWv.loadData(response,"text/html","UTF-8");
            }
        });
    }

    @Override
    public void onClick(View v) {
        load();
    }
}
