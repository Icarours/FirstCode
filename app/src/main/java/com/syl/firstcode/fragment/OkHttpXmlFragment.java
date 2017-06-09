package com.syl.firstcode.fragment;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;
import com.syl.firstcode.bean.App;
import com.syl.firstcode.config.Constant;
import com.syl.firstcode.handler.MyContentHandler;

import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Bright on 2017/5/30.
 *
 * @Describe okHttp, xml文件
 * @Called
 */

public class OkHttpXmlFragment extends BaseFragment implements View.OnClickListener {

    private static final String TAG = OkHttpXmlFragment.class.getSimpleName();
    private View mRootView;
    private TextView mTvContent;
    private List<App> mAppList;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_okhttp_xml, null);
        mTvContent = (TextView) mRootView.findViewById(R.id.tv_content);
        return mRootView;
    }

    @Override
    public void initListener() {
        mRootView.findViewById(R.id.btn_load_pull).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_load_sax).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_load_pull:
                loadPull();
                break;
            case R.id.btn_load_sax:
                loadSax();
                break;
            default:
                break;
        }
    }

    /**
     * 网络请求,异步请求
     * ,数据加载,sax解析
     */
    private void loadSax() {
        //主要是为了分辨使用了不同的数据解析方法
        mTvContent.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        //1.创建OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建请求体RequestBody,请求体可以为空
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "222")
                .build();
        //3.创建网络请求Request,get
        Request request = new Request.Builder()
                .get()
                .url(Constant.URLS.XML_URL)
                .build();
        //3.创建网络请求Request,post
//        Request request = new Request.Builder()
//                .post(requestBody)
//                .url("https://www.baidu.com")
//                .build();
        /*------------------ okhttp同步请求start -----------------*/
        //4.创建Response.接收返回的请求数据(同步请求)
        //syncOkhttp(okHttpClient, request);
        /*------------------ okhttp同步请求end -----------------*/

        //4.创建Call.接收返回的请求数据(异步请求)
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                InputStream inputStream = response.body().byteStream();
                List<App> apps = parseXMLWithSax(inputStream);
                final StringBuffer sb = new StringBuffer();
                for (int i = 0; i < apps.size(); i++) {
                    App app = apps.get(i);
                    //linux或者Android中的换行符\r\n
                    sb.append(app.getId() + "--" + app.getName() + "--" + app.getVersion() + "\r\n");
                    Log.d(TAG, app.toString());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvContent.setText(sb.toString());
                    }
                });
            }
        });
    }

    /**
     * 网络请求,异步请求
     * ,数据加载,pull解析
     */
    private void loadPull() {
        mAppList = new ArrayList<>();
        //主要是为了分辨使用了不同的数据解析方法
        mTvContent.setBackgroundColor(getResources().getColor(R.color.colorAccent));
        //1.创建OkHttpClient
        OkHttpClient okHttpClient = new OkHttpClient();
        //2.创建请求体RequestBody,请求体可以为空
        RequestBody requestBody = new FormBody.Builder()
                .add("username", "admin")
                .add("password", "222")
                .build();
        //3.创建网络请求Request,get
        Request request = new Request.Builder()
                .get()
                .url(Constant.URLS.XML_URL)
                .build();
        //3.创建网络请求Request,post
//        Request request = new Request.Builder()
//                .post(requestBody)
//                .url("https://www.baidu.com")
//                .build();
        /*------------------ okhttp同步请求start -----------------*/
        //4.创建Response.接收返回的请求数据(同步请求)
        //syncOkhttp(okHttpClient, request);
        /*------------------ okhttp同步请求end -----------------*/

        //4.创建Call.接收返回的请求数据(异步请求)
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.d(TAG, "网络请求失败");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String xmlData = response.body().string();
                parseXMLWithPull(xmlData);
                final StringBuffer sb = new StringBuffer();
                for (int i = 0; i < mAppList.size(); i++) {
                    App app = mAppList.get(i);
                    //linux或者Android中的换行符\r\n
                    sb.append(app.getId() + "--" + app.getName() + "--" + app.getVersion() + "\r\n");
                    Log.d(TAG, app.toString());
                }
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mTvContent.setText(sb.toString());
                    }
                });
                //在子线程解析加载完成数据之后,将mAppList置空,否则,每次点击button都会在TextView中添加mAppList中的数据
                mAppList = null;
            }
        });
    }

    /**
     * 使用sax解析string
     *
     * @param xmlData
     */
    private void parseXMLWithSax(String xmlData) {
        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            ContentHandler contentHandler = new MyContentHandler();
            XMLReader xmlReader = saxParser.getXMLReader();
            xmlReader.setContentHandler(contentHandler);
            xmlReader.parse(new InputSource(new StringReader(xmlData)));
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 使用sax解析string
     *
     * @param inputStream
     */
    private List<App> parseXMLWithSax(InputStream inputStream) {
        MyContentHandler myContentHandler = new MyContentHandler();
        try {
            List<App> appList = myContentHandler.getAppList(inputStream);
            return appList;
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 使用pull解析器解析string
     *
     * @param string
     */
    private void parseXMLWithPull(String string) {
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser xmlPullParser = factory.newPullParser();
            xmlPullParser.setInput(new StringReader(string));
//            String id = null;
//            String name = null;
//            String version = null;
            int eventType = xmlPullParser.getEventType();
            App app = null;
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    if ("app".equals(xmlPullParser.getName())) {
                        app = new App();
                    } else if ("id".equals(xmlPullParser.getName())) {
                        app.setId(xmlPullParser.nextText());
                    } else if ("name".equals(xmlPullParser.getName())) {
                        app.setName(xmlPullParser.nextText());
                    } else if ("version".equals(xmlPullParser.getName())) {
                        app.setVersion(xmlPullParser.nextText());
                    }
                } else if (eventType == XmlPullParser.END_TAG) {
                    if ("app".equals(xmlPullParser.getName())) {
                        mAppList.add(app);
                    }
                }
                eventType = xmlPullParser.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * okHttp同步请求
     *
     * @param okHttpClient
     * @param request
     */
    private void syncOkhttp(OkHttpClient okHttpClient, Request request) {
        try {
            Response response = okHttpClient.newCall(request).execute();
            String string = response.body().string();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
