package com.syl.firstcode.fragment;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Toast;

import com.syl.firstcode.R;
import com.syl.firstcode.base.BaseFragment;
import com.syl.firstcode.db.MyDataBaseHelper;

/**
 * Created by Bright on 2017/5/21.
 *
 * @Describe 文件存储
 * sqlite数据库存储
 * sqlite的简单操作,增删改查
 * @Called
 */

public class FileFragment2 extends BaseFragment implements View.OnClickListener {

    private View mRootView;
    private MyDataBaseHelper mHelper;

    @Override
    public View initView() {
        mRootView = View.inflate(getContext(), R.layout.fragment_file2, null);
        mRootView.findViewById(R.id.btn_create).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_add).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_delete).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_update).setOnClickListener(this);
        mRootView.findViewById(R.id.btn_query).setOnClickListener(this);
        return mRootView;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_create:
                create();
                break;
            case R.id.btn_add:
                add();
                break;
            case R.id.btn_delete:
                delete();
                break;
            case R.id.btn_update:
                update();
                break;
            case R.id.btn_query:
                query();
                break;
            default:
                break;
        }
    }

    @Override
    public void init() {
        mHelper = new MyDataBaseHelper(getContext(), "BookStore.db", null, 2);
    }

    private void create() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
    }

    private void add() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        //插入第一条数据
        values.put("name", "first code");
        values.put("author", "guo lin");
        values.put("page", 452);
        values.put("price", 12.5);
        db.insert("book", null, values);
        //清楚先前的数据,插入第二条数据
        values.clear();
        values.put("name", "second blood");
        values.put("author", "data");
        values.put("page", 356);
        values.put("price", 8.5);
        db.insert("book", null, values);
        db.close();
        Toast.makeText(getContext(), "数据插入成功..", Toast.LENGTH_SHORT).show();
        System.out.println("数据插入成功..");
    }

    private void delete() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        db.delete("book", "page>?", new String[]{"500"});
        db.close();
    }

    private void update() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("price", 23);
        db.update("book", values, "name=?", new String[]{"second blood"});
        values.clear();
        values.put("page", 888);
        db.update("book", values, "name=?", new String[]{"first code"});
        db.close();
        Toast.makeText(getContext(), "数据修改成功..", Toast.LENGTH_SHORT).show();
        System.out.println("数据修改成功..");
    }

    private void query() {
        SQLiteDatabase db = mHelper.getWritableDatabase();
//        Cursor cursor = db.rawQuery("select * from book", null);
        Cursor cursor = db.query("book", null, null, null, null, null, null);
        while (cursor.moveToNext()) {//刚才大脑短路了,本应该用while语句,结果用了if语句
            int id = cursor.getInt(0);
            String author = cursor.getString(1);
            int price = cursor.getInt(2);
            int page = cursor.getInt(3);
            String name = cursor.getString(4);

            System.out.println("id==" + id + "--name==" + name + "--price==" + price + "--page==" + page + "--author==" + author);
        }
        cursor.close();
        db.close();
    }
}
