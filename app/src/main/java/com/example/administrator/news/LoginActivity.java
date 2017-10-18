package com.example.administrator.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tlb)
    Toolbar mTlb;
    @BindView(R.id.ed_userName)
    EditText mEdUserName;
    @BindView(R.id.ed_password)
    EditText mEdPassword;
    @BindView(R.id.bt_login)
    Button mBtLogin;
    @BindView(R.id.tv_register)
    TextView mTvRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        initTitle();
    }

    private void initTitle() {
        setSupportActionBar(mTlb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        mTlb.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @OnClick(R.id.tv_register)
    public void onViewClicked(View view) {
        switch (view.getId()){
            case R.id.tv_register:
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                break;
        }
    }
}
