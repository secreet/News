package com.example.administrator.news.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.administrator.news.NewsDetailActivity;
import com.example.administrator.news.R;
import com.example.administrator.news.entity.News;
import com.example.administrator.news.entity.NewsListInfo;
import com.example.administrator.news.entity.NewsType;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2017/10/11.
 */

public class NewsListAdapter extends RecyclerView.Adapter {
    private Context mContext;
    private List<News> mList = new ArrayList<>();
    private static final int TYPE_TV = 0;
    private static final int TYPE_IV = 1;

    public NewsListAdapter(Context context, List<News> list) {
        mContext = context;
        mList = list;
    }

    public void Clear(){
        mList.clear();
    }
    public void setmNewsList(List<News> mNewsList) {
        this.mList = mNewsList;
    }
    public void add(List<News> data) {
        mList.clear();
        mList.addAll(data);
        notifyDataSetChanged();
    }

    public int getItemViewType(int position) {
        if (position % 3 == 0) {
            return TYPE_IV;
        } else {
            return TYPE_TV;
        }
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (viewType == TYPE_IV) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist_iv, parent, false);
            return new IvViewHolder(v);
        } else {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_newslist_tv, parent, false);
            return new TvViewHolder(v);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final News mNews = mList.get(position);
        if (holder instanceof TvViewHolder) {
            ((TvViewHolder) holder).mNewsIvTitle.setText(mList.get(position).title);
            ((TvViewHolder) holder).mNewsIvContent.setText(mList.get(position).summary);
            ((TvViewHolder) holder).mNewsIvDate.setText(mList.get(position).stamp);
            Glide.with(mContext).load(mList.get(position).icon).into(((TvViewHolder) holder).mNewsIvIcon);
        } else if (holder instanceof IvViewHolder) {
            ((IvViewHolder) holder).mNewsIvTitle.setText(mList.get(position).title);
            Glide.with(mContext).load(mList.get(position).icon).into(((IvViewHolder) holder).mIv1);
            Glide.with(mContext).load(mList.get(position).icon).into(((IvViewHolder) holder).mIv2);
            Glide.with(mContext).load(mList.get(position).icon).into(((IvViewHolder) holder).mIv3);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent mIntent = new Intent(mContext, NewsDetailActivity.class);
                Bundle mBundle=new Bundle();
                mBundle.putSerializable("NewsInfo",mNews);
                mIntent.putExtras(mBundle);
                mContext.startActivity(mIntent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    static class IvViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_iv_title)
        TextView mNewsIvTitle;
        @BindView(R.id.iv1)
        ImageView mIv1;
        @BindView(R.id.iv2)
        ImageView mIv2;
        @BindView(R.id.iv3)
        ImageView mIv3;

        public IvViewHolder(View v) {
            super(v);
            ButterKnife.bind(this, v);
        }
    }

    static class TvViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.news_iv_icon)
        ImageView mNewsIvIcon;
        @BindView(R.id.news_iv_title)
        TextView mNewsIvTitle;
        @BindView(R.id.news_iv_date)
        TextView mNewsIvDate;
        @BindView(R.id.news_iv_content)
        TextView mNewsIvContent;

        public TvViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}