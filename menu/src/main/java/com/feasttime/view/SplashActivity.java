package com.feasttime.view;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.feasttime.menu.R;
import com.feasttime.presenter.IBasePresenter;

/**
 * Created by chen on 2017/4/16.
 */

public class SplashActivity extends BaseActivity {
    private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
                finish();
            }
        },3000);
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[0];
    }

    @Override
    protected void onInitPresenters() {
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.splash_activity;
    }
}
