package com.example.administrator.news;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.news.adapter.NewsListAdapter;
import com.example.administrator.news.biz.NewsDBUtils;
import com.example.administrator.news.entity.News;
import com.example.administrator.news.entity.NewsEvent;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsDetailActivity extends AppCompatActivity {

    @BindView(R.id.title)
    TextView mTitle;
    @BindView(R.id.toolBar)
    Toolbar mToolBar;
    @BindView(R.id.webview)
    WebView mWebview;
    @BindView(R.id.pro)
    ProgressBar mPro;
    private News mNewsInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        ButterKnife.bind(this);
        mNewsInfo = (News) getIntent().getExtras().getSerializable("NewsInfo");
        initTitle();
        initWebView();
    }

    private void initTitle() {
        setSupportActionBar(mToolBar);
        ActionBar mActionBar = getSupportActionBar();
        mActionBar.setDisplayHomeAsUpEnabled(true);
        mActionBar.setTitle("");
        mTitle.setText(mNewsInfo.title);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_newsdatils,menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        menu.clear();
        if (NewsDBUtils.insert(NewsDetailActivity.this,mNewsInfo)==false){
            menu.add(0,0,0,"已收藏");
        }else if (NewsDBUtils.deleteOne(NewsDetailActivity.this,mNewsInfo)==true){
            getMenuInflater().inflate(R.menu.menu_newsdatils,menu);
        }
        return true;

    }

    private void initWebView() {
        mWebview.setWebViewClient(new WebViewClient());
        mWebview.loadUrl(mNewsInfo.link);
        WebSettings mSettings = mWebview.getSettings();
        mSettings.setJavaScriptEnabled(true);
        mSettings.setLoadWithOverviewMode(true);
        mSettings.setUseWideViewPort(true);
        mWebview.setWebChromeClient(mChromeClient);
    }

    WebChromeClient mChromeClient = new WebChromeClient() {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            super.onProgressChanged(view, newProgress);
            mPro.setProgress(newProgress);
            if (mPro.getProgress() == 100) {
                mPro.setVisibility(View.GONE);
            }
        }
    };
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                NewsDetailActivity.this.finish();
                break;
            case 0:
            case R.id.collect:
                saveNews();
                //发送EventBus
                EventBus.getDefault().post(new NewsEvent());
                break;
        }
        return true;
    }
    private void saveNews() {
        if (NewsDBUtils.insert(NewsDetailActivity.this,mNewsInfo)){
            Toast.makeText(NewsDetailActivity.this,"收藏成功",Toast.LENGTH_SHORT).show();

        }else if (NewsDBUtils.deleteOne(NewsDetailActivity.this,mNewsInfo)){
            Toast.makeText(NewsDetailActivity.this,"取消收藏",Toast.LENGTH_SHORT).show();
        }
        EventBus.getDefault().post(new NewsEvent());
    }

}
