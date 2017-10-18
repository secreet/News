package com.example.administrator.news.network;

import android.util.Log;

import com.example.administrator.news.biz.UrlUtils;
import com.example.administrator.news.entity.NewsListInfo;
import com.google.gson.Gson;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

/**
 * Created by Administrator on 2017/10/11.
 */

public class NewsClient {
    private static NewsClient mNewsClient;
    private OkHttpClient mOkHttpClient;
    private Gson mGson;

    public static NewsClient getInstance(){
        if (mNewsClient == null){
            mNewsClient = new NewsClient();
        }
        return mNewsClient;
    }
    private NewsClient(){
        //日志拦截器，主要拦截响应行  响应头 响应体
        HttpLoggingInterceptor httpLoggingInterceptor=new HttpLoggingInterceptor();
        //设置拦截器级别
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);//获取响应行  响应头 响应体
        mOkHttpClient=new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)//添加日志拦截器
                .build();
        mGson=new Gson();
    }
    public Call getNewsType(){
        String url = "news_list?ver=2&subid=1&dir=1&nid=0&stamp=null&cnt=2";
        Request request = new Request.Builder()
                .url(UrlUtils.APP_URL+url)
                .build();
                return mOkHttpClient.newCall(request);
    }
    /**
     *
     * @param subid 新闻类别
     * @param dir 方向--下拉(1)or上拉(2)
     * @param nid 如果是上拉，那么最后一条新闻id，否则0
     * @return
     */
}
