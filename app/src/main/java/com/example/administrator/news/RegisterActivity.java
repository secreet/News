package com.example.administrator.news;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.news.biz.CommonUtil;
import com.example.administrator.news.biz.LogUtils;
import com.example.administrator.news.biz.UrlUtils;
import com.example.administrator.news.entity.OkhttpUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.Call;

public class RegisterActivity extends AppCompatActivity {

    @BindView(R.id.tv_title)
    TextView mTvTitle;
    @BindView(R.id.tlb)
    Toolbar mTlb;
    @BindView(R.id.ed_userName)
    EditText mEdUserName;
    @BindView(R.id.ed_email)
    EditText mEdEmail;
    @BindView(R.id.ed_password)
    EditText mEdPassword;
    @BindView(R.id.bt_register)
    Button mBtRegister;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        ButterKnife.bind(this);
        initTitle();
    }
    @OnClick(R.id.bt_register)
    public void onViewClicked() {
        register();
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
    private void register() {

        final String mUserName = mEdUserName.getText().toString().trim();
        String mEmail = mEdEmail.getText().toString().trim();
        final String mPwd = mEdPassword.getText().toString().trim();

        if(mUserName.length()==0){
            Toast.makeText(this,"用户名不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mEmail.length()==0){
            Toast.makeText(this,"邮箱不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if(mPwd.length()==0){
            Toast.makeText(this,"密码不能为空",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonUtil.verifyEmail(mEmail)){
            Toast.makeText(this,"邮箱格式不正确",Toast.LENGTH_SHORT).show();
            return;
        }
        if (!CommonUtil.verifyPassword(mPwd)){
            Toast.makeText(this,"密码格式不正确，6-16位",Toast.LENGTH_SHORT).show();
            return;
        }
        String url= UrlUtils.APP_URL+"user_register?ver=1&uid="+mUserName+"&email="+mEmail+"&pwd="+mPwd;
        OkhttpUtils.getOkhttp(url, RegisterActivity.this, new OkhttpUtils.CallBack() {
            @Override
            public void fail(Call call, IOException e) {

            }

            @Override
            public void successed(Call call, String jsonStr) {
                LogUtils.Loge("result",jsonStr);
                try {
                    JSONObject mJSONObject = new JSONObject(jsonStr);
                    if (mJSONObject.getInt("status")==0){
                        mJSONObject=mJSONObject.getJSONObject("data");
                        int mResult = mJSONObject.getInt("result");
                        if (mResult==0){
                            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                            Intent mIntent=new Intent();
                            Bundle mBundle = new Bundle();
                            mBundle.putString("userName",mUserName);
                            mBundle.putString("pwd",mPwd);
                            mIntent.putExtras(mBundle);
                            setResult(1,mIntent);
                            RegisterActivity.this.finish();

                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

}
