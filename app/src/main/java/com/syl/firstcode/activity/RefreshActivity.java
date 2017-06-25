package com.syl.firstcode.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.syl.firstcode.R;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * author   Bright
 * date     2017/6/22 20:13
 * desc
 * swipeRefreshLayout控件只能有一个子控件
 * swipeRefreshLayout举例,下拉刷新和上拉加载更多(上拉加载更多稍后再说)
 */
public class RefreshActivity extends AppCompatActivity {
    private List<String> mList = new ArrayList<>();//数据集
    private BaseAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);

        for (int i = 0; i < 20; i++) {
            mList.add("data" + i);
        }

        ListView listView = (ListView) findViewById(R.id.lv);
        mAdapter = new MyAdapter();
        listView.setAdapter(mAdapter);
        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.srl);
        //设置下拉进度的背景颜色
        swipeRefreshLayout.setProgressBackgroundColorSchemeColor(Color.WHITE);
        //设置进度条的颜色
        swipeRefreshLayout.setColorSchemeResources(R.color.colorAccent, R.color.colorPrimary, R.color.colorPrimaryDark);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                final Random random = new Random();
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mList.add("数据" + random.nextInt(100) + "data");
                        mAdapter.notifyDataSetChanged();
                        Toast.makeText(RefreshActivity.this, "刷新了一条数据", Toast.LENGTH_SHORT).show();
                        //刷新完毕,收起刷新状态
                        swipeRefreshLayout.setRefreshing(false);
                    }
                }, 1000);
            }
        });
    }

    /**
     * author   Bright
     * date     2017/6/22 20:57
     * desc
     * 自定义MyAdapter,ListView的标准写法以及优化
     */
    class MyAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            if (mList != null) {
                return mList.size();
            }
            return 0;
        }

        @Override
        public Object getItem(int position) {
            if (mList != null) {
                return mList.get(position);
            }
            return null;
        }

        @Override
        public long getItemId(int position) {
            if (mList != null) {
                return position;
            }
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                holder = new ViewHolder();
                convertView = View.inflate(RefreshActivity.this, R.layout.lv_item, null);
                holder.tv = (TextView) convertView.findViewById(R.id.item_tv);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            holder.tv.setText(mList.get(position));
            return convertView;
        }

        class ViewHolder {
            TextView tv;
        }
    }
}
