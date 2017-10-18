package com.example.administrator.news.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.administrator.news.LoginActivity;
import com.example.administrator.news.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Administrator on 2017/10/10.
 */

public class Mine extends Fragment {
    @BindView(R.id.tv_dl)
    TextView mTvDl;
    Unbinder unbinder;
    @BindView(R.id.portrait)
    CircleImageView mPortrait;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mView = inflater.inflate(R.layout.fragment_mine, container, false);
        unbinder = ButterKnife.bind(this, mView);
        return mView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_dl, R.id.portrait})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_dl:
            case R.id.portrait:
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }

}
