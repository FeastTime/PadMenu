package com.feasttime.dishmap;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by chen on 2017/9/24.
 */

public class SplashActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
              startActivity(new Intent(SplashActivity.this,MainActivity.class));
              finish();
            }
        },2000);
    }
}
