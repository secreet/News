package com.example.administrator.news;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.administrator.news.fragment.Circle;
import com.example.administrator.news.fragment.Collection;
import com.example.administrator.news.fragment.Mine;
import com.example.administrator.news.fragment.NewsFragment;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tlb)
    Toolbar tlb;
    @BindView(R.id.vp_main)
    ViewPager vpMain;
    @BindViews({R.id.tv_news,R.id.tv_collect,R.id.tv_cof,R.id.tv_me})
   TextView[] tvs;
    @BindView(R.id.linearlayout)
    LinearLayout linearlayout;
    int color;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
       vpMain.setAdapter(adapter);
        vpMain.setCurrentItem(0);
        tvs[0].setSelected(true);
        init();
    }

    private void init() {
        vpMain.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (TextView tv: tvs){
                    tv.setSelected(false);
                }
                tvs[position].setSelected(true);
                tvTitle.setText(tvs[position].getText().toString());
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }


    @OnClick({R.id.tv_news, R.id.tv_collect, R.id.tv_cof, R.id.tv_me})
    public void onViewClicked(View view) {
        for (int i = 0; i < tvs.length; i++) {
            tvs[i].setTextColor(getResources().getColor(R.color.black));
            tvs[i].setSelected(false);
            tvs[i].setTag(i);

        }
        view.setSelected(true);
        vpMain.setCurrentItem((Integer) view.getTag(),false);
    }

    FragmentStatePagerAdapter adapter = new FragmentStatePagerAdapter(getSupportFragmentManager()) {
        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    return new NewsFragment();
                case 1:
                    return new Collection();
                case 2:
                    return new Circle();
                case 3:
                    return new Mine();
            }
            return null;
        }

        @Override
        public int getCount() {
            return tvs.length;
        }
    };
}
