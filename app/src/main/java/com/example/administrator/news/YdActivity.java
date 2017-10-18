package com.example.administrator.news;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

public class YdActivity extends AppCompatActivity {
    private Timer timer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yd);
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        timer.cancel();
                        Intent intent = new Intent();
                        intent.setClass(YdActivity.this,MainActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
            }
        },1500);
    }

}
