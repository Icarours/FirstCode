package com.syl.firstcode.utils;

import com.syl.firstcode.interface_.HttpCallbackListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by Bright on 2017/6/6.
 *
 * @Describe
 * @Called
 */

public class HttpUtils {
    /**
     * HttpUrlConnection网络请求get
     *
     * @param httpAddress
     * @return
     */
    public static String sendHttpUrlRequest(String httpAddress) {
        HttpURLConnection connection = null;
        try {
            URL url = new URL(httpAddress);
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.setReadTimeout(8000);
            connection.setConnectTimeout(8000);
            connection.setDoInput(true);
            connection.setDoOutput(true);
            InputStream inputStream = connection.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder response = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            return response.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
        }
        return null;
    }

    /**
     * HttpUrlConnection网络请求get
     *
     * @param httpAddress
     * @param listener
     */
    public static void sendHttpUrlRequest(final String httpAddress, final HttpCallbackListener listener) {
        new Thread() {
            @Override
            public void run() {
                HttpURLConnection connection = null;
                try {
                    URL url = new URL(httpAddress);
                    connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("GET");
                    connection.setReadTimeout(8000);
                    connection.setConnectTimeout(8000);
                    connection.setDoInput(true);
                    connection.setDoOutput(true);
                    InputStream inputStream = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                    StringBuilder response = new StringBuilder();
                    String line;
                    while ((line = reader.readLine()) != null) {
                        response.append(line);
                    }
                    if (listener != null) {
                        //回调onSuccess()方法
                        listener.onSuccess(response.toString());
                    }
                } catch (Exception e) {
                    if (listener != null) {
                        //回调onError()方法
                        listener.onError(e);
                    }
                } finally {
                    if (connection != null) {
                        connection.disconnect();
                    }
                }
            }
        }.start();
    }

    /**
     * okHttp网络请求get
     *
     * @param httpAddress
     * @param callback
     */
    public static void sendOkhttpRequest(String httpAddress, Callback callback) {
        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = new Request.Builder()
                .get()
                .url(httpAddress)
                .build();
        okHttpClient.newCall(request).enqueue(callback);
    }
}
