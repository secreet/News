package com.example.administrator.news.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.news.R;
import com.example.administrator.news.entity.NewsType;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsTypeAdapter extends RecyclerView.Adapter<NewsTypeAdapter.ViewHolder>  {
    public int TitlePosition = 0;
    private List<NewsType> mNewsTypeList;
    private Context mContext;
    private OnItemClickListener mOnItemClickListener;

    public NewsTypeAdapter(Context context, List<NewsType> mNewsTypeList,OnItemClickListener mOnItemClickListener) {
        this.mNewsTypeList = mNewsTypeList;
        this.mContext = context;
        this.mOnItemClickListener = mOnItemClickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext=parent.getContext();
        View mView = LayoutInflater.from(mContext).inflate(R.layout.item_newstype, parent,false);
        ViewHolder mViewHolder = new ViewHolder(mView);
        return mViewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.mTvNewstype.setText(mNewsTypeList.get(position).getSubgroup());
        holder.mTvNewstype.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mOnItemClickListener != null){
                    mOnItemClickListener.setonItemClickListener(mNewsTypeList.get(position),position);
                }
            }
        });
        if (TitlePosition == position){
        holder.mTvNewstype.setTextColor(Color.BLUE);}
        else {
            holder.mTvNewstype.setTextColor(Color.BLACK);
        }
    }

    public NewsType getItem(int position){
        return mNewsTypeList.get(position);
    }
    @Override
    public int getItemCount() {
        return mNewsTypeList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.tv_newstype)
        TextView mTvNewstype;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
    public interface OnItemClickListener{
        void setonItemClickListener(NewsType mNewsType, int position);
    }

}
