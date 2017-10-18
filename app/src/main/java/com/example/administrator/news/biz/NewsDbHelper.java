package com.example.administrator.news.biz;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 17-9-4.
 */

public class NewsDbHelper extends SQLiteOpenHelper {
    public NewsDbHelper(Context context) {
        super(context,"saveNews.db",null,1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table news (id integer primary key autoincrement,nid integer,stamp text,icon text,title text,summary text,link text)");

    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
