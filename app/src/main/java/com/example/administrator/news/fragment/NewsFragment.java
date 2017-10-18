package com.example.administrator.news.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.news.R;
import com.example.administrator.news.adapter.NewsListAdapter;
import com.example.administrator.news.adapter.NewsTypeAdapter;
import com.example.administrator.news.biz.UrlUtils;
import com.example.administrator.news.entity.News;
import com.example.administrator.news.entity.NewsType;
import com.example.administrator.news.network.LoadMoreForRecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.administrator.news.R.id.srl;

/**
 * Created by Administrator on 2017/10/10.
 */

public class NewsFragment extends Fragment {

    Unbinder unbinder;
    @BindView(R.id.news_type)
    RecyclerView mNewsType;
    @BindView(R.id.rc)
    RecyclerView mRc;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    private NewsTypeAdapter mAdapter;
    private NewsListAdapter mNewsListAdapter;
    private Context mContext;
    List<NewsType> mNewsTypeList = new ArrayList<NewsType>();
    List<News> mNewsList2 = new ArrayList<News>();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_news, container, false);
        initData();
        unbinder = ButterKnife.bind(this, mView);
        mAdapter = new NewsTypeAdapter(mContext, mNewsTypeList, new NewsTypeAdapter.OnItemClickListener() {
            @Override
            public void setonItemClickListener(NewsType mNewsType, int position) {
                getdate(mNewsType.getSubid(),1,0);
                mAdapter.TitlePosition = position;
                mAdapter.notifyDataSetChanged();
            }
        });
        mNewsType.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.HORIZONTAL, false));
        mNewsType.setAdapter(mAdapter);
        mContext = getActivity();
        mNewsListAdapter = new NewsListAdapter(mContext, mNewsList2);
        mRc.setLayoutManager(new LinearLayoutManager(mContext, LinearLayout.VERTICAL, false));
        mRc.setAdapter(mNewsListAdapter);
        mSrl.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mNewsListAdapter.notifyDataSetChanged();
                mSrl.setRefreshing(false);

            }
        });
        return mView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        new LoadMoreForRecyclerView(mRc, new LoadMoreForRecyclerView.LoadMoreListener() {
            @Override
            public void loadMore() {
                getdate(mAdapter.TitlePosition,2,mNewsList2.get(mRc.getAdapter().getItemCount()-1).nid);
            }
        },getActivity());
    }

    private void initData() {
        OkHttpClient mOkHttpClient = new OkHttpClient();
        FormBody mFormBody = new FormBody.Builder().add("ver", "2").add("imei", "2").build();
        Request mRequest = new Request.Builder().url(UrlUtils.APP_URL + "news_sort").post(mFormBody).build();
        Call mCall = mOkHttpClient.newCall(mRequest);
        mCall.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("result", result);

                try {
                    JSONObject mJSONObject = new JSONObject(result);
                    if (mJSONObject.getInt("status") == 0) {
                        JSONArray mJSONArray = mJSONObject.getJSONArray("data");
                        for (int i = 0; i < mJSONArray.length(); i++) {
                            mJSONObject = mJSONArray.getJSONObject(i);
                            JSONArray mArray = mJSONObject.getJSONArray("subgrp");
                            for (int j = 0; j < mArray.length(); j++) {
                                JSONObject mObject = mArray.getJSONObject(j);
                                String mSubgroup = mObject.getString("subgroup");
                                int mSubid = mObject.getInt("subid");
                                NewsType mNewsType = new NewsType(mSubgroup, mSubid);
                                mNewsTypeList.add(mNewsType);
                                Log.e("result", mNewsTypeList.toString());
                            }
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mAdapter.notifyDataSetChanged();
                                getdate(mAdapter.getItem(0).getSubid(),1,0);
                            }
                        });
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public void getdate(int subid, int dir ,int nid) {

        OkHttpClient mOkHttpClient = new OkHttpClient();
        String url = "news_list?ver=2&subid=" + subid + "&dir=" + dir + "&nid=" + nid + "&stamp=null&cnt=2";
        Request request = new Request.Builder()
                .url(UrlUtils.APP_URL + url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String result = response.body().string();
                Log.e("TAG", result);
                mNewsListAdapter.Clear();
                try {
                    JSONObject jsonObject = new JSONObject(result);
                    if (jsonObject.getInt("status") == 0) {
                        JSONArray jay = jsonObject.getJSONArray("data");
                        for (int i = 0; i < jay.length(); i++) {
                            jsonObject = jay.getJSONObject(i);
                            String mSummary = jsonObject.getString("summary");
                            String mIcon = jsonObject.getString("icon");
                            String mStamp = jsonObject.getString("stamp");
                            String mTitle = jsonObject.getString("title");
                            int mNid = jsonObject.getInt("nid");
                            String mLink = jsonObject.getString("link");
                            int mType = jsonObject.getInt("type");
                            News mNews = new News(mSummary, mIcon, mStamp, mTitle, mNid, mLink, mType);
                            mNewsList2.add(mNews);
                            Log.e("result", mNewsList2.toString());
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                mNewsListAdapter.notifyDataSetChanged();
                            }
                        });
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
