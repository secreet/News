package com.example.administrator.news.biz;

import android.util.Log;

/**
 * Created by Administrator on 17-8-29.
 */

public class LogUtils {

    private static boolean flag=true;
    public static void Loge(String message){
        if (flag){
            Log.e("fragment",message);
        }
    }
    public static void Loge(String tag, String message){
        if (flag){
            Log.e(tag,message);
        }
    }
}
