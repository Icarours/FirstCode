package com.syl.firstcode.handler;

import android.util.Log;

import com.syl.firstcode.bean.App;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

/**
 * Created by Bright on 2017/5/30.
 *
 * @Describe 内容处理者
 * 有两种不同的处理方式:
 * 1.在控制状态上打印
 * 2.将解析出来的数据存放到list集合中
 * @Called
 */

public class MyContentHandler extends DefaultHandler {
    public static final String TAG = MyContentHandler.class.getSimpleName();
    private String nodeName;
    private StringBuilder id;
    private StringBuilder name;
    private StringBuilder version;
    private List<App> mAppList;
    private App mApp;
    private String preTag;//解析式记录上一个节点的名称

    public List<App> getAppList(InputStream inputStream) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory saxParserFactory = SAXParserFactory.newInstance();
        SAXParser saxParser = saxParserFactory.newSAXParser();
        MyContentHandler handler = new MyContentHandler();
        saxParser.parse(inputStream, handler);
        return handler.mAppList;
    }

    public List<App> getAppList() {
        return mAppList;
    }

    @Override
    public void startDocument() throws SAXException {
        mAppList = new ArrayList<>();
        id = new StringBuilder();
        name = new StringBuilder();
        version = new StringBuilder();
    }

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        if ("app".equals(qName)) {
            mApp = new App();
            mApp.setId(attributes.getValue(0));
            mApp.setName(attributes.getValue(1));
            mApp.setVersion(attributes.getValue(2));
        }
        //记录当前节点名
        nodeName = localName;
        //将正在解析的节点赋值给preTag
        preTag = qName;
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        if (preTag != null) {
            //根据当前的节点名,判断将内容添加到那一个StringBuilder中
            if ("id".equals(nodeName)) {
                id.append(ch, start, length);
                mApp.setId(new String(ch, start, length));
            } else if ("name".equals(nodeName)) {
                name.append(ch, start, length);
                mApp.setName(new String(ch, start, length));
            } else if ("version".equals(nodeName)) {
                version.append(ch, start, length);
                mApp.setVersion(new String(ch, start, length));
            }
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        //super.endElement(uri, localName, qName);
        if ("app".equals(qName)) {
            Log.d(TAG, "id is==" + id.toString().trim());
            Log.d(TAG, "name is==" + name.toString().trim());
            Log.d(TAG, "version is==" + version.toString().trim());

            //最后将所有的StringBuilder清空
            id.setLength(0);
            name.setLength(0);
            version.setLength(0);
            mAppList.add(mApp);
            mApp = null;
        }
        preTag = null;
    }

    @Override
    public void endDocument() throws SAXException {
        super.endDocument();
    }
}
