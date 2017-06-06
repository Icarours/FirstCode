package com.syl.firstcode.config;

/**
 * Created by Bright on 2017/5/19.
 *
 * @Describe 常量
 * 如果需要添加内容就在这里加Fragment的标题和状态
 * @Called
 */

public class Constant {
    //Fragment 的标题
    public static String[] mTitles = {
            "文件存储(openFileOutput)",
            "文件存储sp",
            "文件存储sqlite",
            "RadioButton/RadioGroup",
            "ContentProvider/联系人",
            "WebView",
            "HttpURLConnection",
            "okHttp_xml",
            "okHttp_json",
    };
    //Fragment的状态,是否处于运行状态
    public static boolean[] mFragmentIsActives = {false, false, false, false,
            false,
            false,
            false,
            false,
            false,};

    public static class URLS {
        public static final String BASEURL = "http://192.168.123.133:8080/";
        public static final String XML_URL = BASEURL + "text/get_data.txt";
        public static final String JSON_URL = BASEURL + "text/get_data.json";
    }
}
