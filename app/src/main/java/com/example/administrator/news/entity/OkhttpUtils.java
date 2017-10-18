package com.example.administrator.news.entity;

import android.app.Activity;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 17-9-1.
 */

public class OkhttpUtils {
    public static  void getOkhttp(String url, final Activity activity, final CallBack callBack){
        OkHttpClient mOkhttpClient = new OkHttpClient();
        Request mRequest = new Request.Builder()
                .url(url)
                .build();
        Call mCall=mOkhttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(final Call call, final IOException e) {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    callBack.fail(call,e);
                }
            });
            }

            @Override
            public void onResponse(final Call call, final Response response) throws IOException {
            activity.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String mString = response.body().string();
                        callBack.successed(call,mString);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            });
            }
        });
    }

    public  interface CallBack{
        void fail(Call call, IOException e);
        void successed(Call call, String jsonStr);
    }
}
