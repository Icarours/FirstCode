package com.syl.firstcode;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
import android.widget.Toast;

import com.syl.firstcode.activity.NavigationActivity;
import com.syl.firstcode.activity.SecondActivity;
import com.syl.firstcode.base.BaseFragment;
import com.syl.firstcode.bean.App;
import com.syl.firstcode.bean.Person;
import com.syl.firstcode.config.Constant;
import com.syl.firstcode.factory.FragmentFactory;
import com.syl.firstcode.fragment.FileFragment;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * author   Bright
 * date     2017/6/6 15:20
 * desc
 * toolBar和DrawerLayout控制主界面
 * ToolBar的使用
 */
public class MainActivity extends AppCompatActivity {

    private static final int FLAG_PARCELABLE = 1;//跳转到SecondActivity时intent的flag标记
    private static final int FLAG_SERIALIZABLE = 2;//跳转到SecondActivity时intent的flag标记
    @BindView(R.id.fl_content)
    FrameLayout mFlContent;
    @BindView(R.id.lv_index)
    ListView mLvIndex;
    @BindView(R.id.activity_main)
    DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private BaseFragment mFragment;
    private int mCurrentPosition;//记录选中的位置


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
                if (Constant.mFragmentIsActives[position]) {
                    mDrawerLayout.closeDrawer(mLvIndex);
                    return;
                }
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
                getSupportActionBar().setTitle(Constant.mTitles[mCurrentPosition]);
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        //交给Toggle去分析并操作绑定的DrawerLayout
        mDrawerToggle.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.item_navigation:
                Toast.makeText(this, "item_data_storage was clicked..", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, NavigationActivity.class);
                startActivity(intent);
                break;
            case R.id.item_parcelable:
                transferDataWithParcelable();//使用Parcelable接口传递数据
                break;
            case R.id.item_serializable:
                transferDataWithSerializable();//使用Serializable接口传递数据
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * 使用Serializable接口传递数据
     */
    private void transferDataWithSerializable() {
        App app = new App();
        app.setName("android");
        app.setVersion("1.0");
        app.setId("111");
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.addFlags(FLAG_SERIALIZABLE);//添加flag,区分传递的是哪个数据
        intent.putExtra("app_data", app);
        startActivity(intent);
    }

    /**
     * 使用Parcelable接口传递数据
     */
    private void transferDataWithParcelable() {
        Person person = new Person();
        person.setName("zs");
        person.setAge(23);
        person.setHeight(167);
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        intent.addFlags(FLAG_PARCELABLE);//添加flag,区分传递的是哪个数据
        intent.putExtra("person_data", person);
        startActivity(intent);
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
     * 修改当前条目对应Fragment的状态
     *
     * @param position
     */
    private void selectItem(int position) {
        mCurrentPosition = position;
        for (int i = 0; i < Constant.mFragmentIsActives.length; i++) {
            Constant.mFragmentIsActives[i] = false;
        }
        // update the main content by replacing fragments
        mFragment = FragmentFactory.createFragment(position);
        Bundle args = new Bundle();
        args.putInt(FileFragment.ARG_FILE_NUMBER, position);
        mFragment.setArguments(args);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fl_content, mFragment).commit();

        // update selected item and title, then close the drawer
        mLvIndex.setItemChecked(position, true);
        setTitle(Constant.mTitles[position]);
        mDrawerLayout.closeDrawer(mLvIndex);//关闭抽屉菜单

        //修改Fragment对应条目的状态
        Constant.mFragmentIsActives[position] = true;
    }
}
