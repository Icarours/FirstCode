package com.syl.firstcode.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

/**
 * Created by Bright on 2017/5/21.
 *
 * @Describe 数据库帮助类
 * @Called
 */

public class MyDataBaseHelper extends SQLiteOpenHelper {
    private String CREATE_BOOK = "create table book(id integer primary key autoincrement," +
            "author text," +
            "price real," +
            "page integer," +
            "name text)";
    private String CREATE_CATEGORY = "create table category(id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer)";
    private Context mContext;

    public MyDataBaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_BOOK);
        db.execSQL(CREATE_CATEGORY);
        Toast.makeText(mContext, "CREATE_BOOK create success", Toast.LENGTH_SHORT).show();
        System.out.println("CREATE_BOOK create success");
        Toast.makeText(mContext, "CREATE_CATEGORY create success", Toast.LENGTH_SHORT).show();
        System.out.println("CREATE_CATEGORY create success");
    }

    /**
     * 通过onUpgrade()刷新数据库
     * @param db
     * @param oldVersion
     * @param newVersion
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists book");
        db.execSQL("drop table if exists category");
        onCreate(db);
    }
}
