package com.qzl.qianggexinwen;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;

import com.facebook.drawee.backends.pipeline.Fresco;

/**
 * 闪屏页开发
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化Fresco
        Fresco.initialize(this);
        setContentView(R.layout.activity_splash);
        //延时执行
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                //跳转到主界面
                startActivity(new Intent(getApplicationContext(),MainActivity.class));
                //销毁当前界面
                finish();
            }
        },2000);
    }
}
