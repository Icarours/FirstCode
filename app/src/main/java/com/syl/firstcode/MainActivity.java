package com.syl.firstcode;

import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ListView;

import com.syl.firstcode.config.Constant;
import com.syl.firstcode.fragment.FileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.lv_index)
    ListView mLvIndex;
    @BindView(R.id.activity_main)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        ActionBar actionBar = getSupportActionBar();
        //设置图标
        actionBar.setIcon(R.mipmap.ic_launcher);
        actionBar.setHomeButtonEnabled(true);
        //显示图标,默认是隐藏的,先显示icon
        actionBar.setDisplayShowHomeEnabled(true);
        //控制icon和logo显示的优先级
        //actionBar.setDisplayUseLogoEnabled(true);
        //显示回退按钮
        actionBar.setDisplayHomeAsUpEnabled(true);

        mLvIndex.setAdapter(new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, Constant.mTitles));
        mLvIndex.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectItem(position);
            }
        });
        if (savedInstanceState == null) {
            selectItem(0);
        }
        /**
         *ActionBarDrawerToggle要用v7包下的
         * 如果使用v4包下的,就没有动画同步的效果
         */
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
                R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                getSupportActionBar().setTitle("内容标题");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                getSupportActionBar().setTitle("抽屉目录标题");
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerToggle.syncState();
        mDrawerLayout.addDrawerListener(mDrawerToggle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //交给Toggle去分析并操作绑定的DrawerLayout
        mDrawerToggle.onOptionsItemSelected(item);
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        //通过配置变化更改mDrawerToggle
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public void onPostCreate(Bundle savedInstanceState, PersistableBundle persistentState) {
        super.onPostCreate(savedInstanceState, persistentState);
        //同步状态
        mDrawerToggle.syncState();
    }

    /**
     * 使用FragmentFactory,从FragmentFactory中取出Fragment
     * @param position
     */
    private void selectItem(int position) {
        // update the main content by replacing fragments
        Fragment fragment = new FileFragment();
        Bundle args = new Bundle();
        args.putInt(FileFragment.ARG_FILE_NUMBER, position);
        fragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction().replace(R.id.fl_content, fragment).commit();

        // update selected item and title, then close the drawer
        mLvIndex.setItemChecked(position, true);
        setTitle(Constant.mTitles[position]);
        mDrawerLayout.closeDrawer(mLvIndex);
    }
}
