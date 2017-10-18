package com.example.administrator.news.biz;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import com.example.administrator.news.entity.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 17-9-4.
 */

public class NewsDBUtils {

    public static boolean insert(Context context, News news){
        NewsDbHelper mHelper = new NewsDbHelper(context);
        SQLiteDatabase db=mHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from news where nid=" + news.nid, null);
        if(mCursor.moveToNext()){

            return false;
        }

        ContentValues mContentValues = new ContentValues();

        mContentValues.put("nid",news.nid);
        mContentValues.put("stamp",news.stamp);
        mContentValues.put("icon",news.icon);
        mContentValues.put("title",news.title);
        mContentValues.put("summary",news.summary);
        mContentValues.put("link",news.link);

        long mNews = db.insert("news", null, mContentValues);
        if (mNews!=-1){
            return true;
        }else {
            return false;
        }

    }
    public static List<News> query(Context context){
        List<News> mNewsList=new ArrayList<>();
        NewsDbHelper mHelper = new NewsDbHelper(context);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        Cursor mCursor = db.rawQuery("select * from news where length(title)<11 Order By stamp asc", null);

        while (mCursor.moveToNext()){
            int mNid = mCursor.getInt(mCursor.getColumnIndex("nid"));
            String mStamp = mCursor.getString(mCursor.getColumnIndex("stamp"));
            String mIcon = mCursor.getString(mCursor.getColumnIndex("icon"));
            String mTitle = mCursor.getString(mCursor.getColumnIndex("title"));
            String mSummary = mCursor.getString(mCursor.getColumnIndex("summary"));
            String mLink = mCursor.getString(mCursor.getColumnIndex("link"));
            News mNews = new News(mSummary, mIcon, mStamp, mTitle, mNid, mLink);
            mNewsList.add(mNews);

        }
        return mNewsList;
    }
    public static boolean deleteOne(Context context,News news){
        NewsDbHelper mHelper = new NewsDbHelper(context);
        SQLiteDatabase db = mHelper.getReadableDatabase();
        int mDelete = db.delete("news", "nid=?", new String[]{news.nid + ""});
        if (mDelete!=-1){
            return true;
        }else {
            return false;
        }
    }

}
