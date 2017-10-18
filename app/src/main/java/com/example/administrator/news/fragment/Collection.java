package com.example.administrator.news.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.administrator.news.R;
import com.example.administrator.news.adapter.NewsListAdapter;
import com.example.administrator.news.biz.LogUtils;
import com.example.administrator.news.biz.NewsDBUtils;
import com.example.administrator.news.entity.News;
import com.example.administrator.news.entity.NewsEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2017/10/10.
 */

public class Collection extends Fragment {
    @BindView(R.id.rc)
    RecyclerView mRc;
    Unbinder unbinder;
    @BindView(R.id.srl)
    SwipeRefreshLayout mSrl;
    @BindView(R.id.rootView)
    LinearLayout mRootView;
    private List<News> mList;
    private NewsListAdapter mAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_collection, container, false);
        unbinder = ButterKnife.bind(this, mView);
        mRc.setLayoutManager(new LinearLayoutManager(getActivity()));
        mList = new ArrayList<>();
        mList = NewsDBUtils.query(getActivity());
        mAdapter = new NewsListAdapter(getActivity(),mList);
        mRc.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
        EventBus.getDefault().register(this);
        return mView;
    }

    @Subscribe(threadMode  = ThreadMode.MAIN)
    public void onEvent(NewsEvent event){
       //清空当前和适配器中的集合
        //从数据库中获得新数据
        //刷新适配器
        mList.clear();
        mList = NewsDBUtils.query(getActivity());
        mAdapter = new NewsListAdapter(getActivity(),mList);
        mRc.setAdapter(mAdapter);
        mAdapter.notifyDataSetChanged();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
        EventBus.getDefault().unregister(this);
    }
}
