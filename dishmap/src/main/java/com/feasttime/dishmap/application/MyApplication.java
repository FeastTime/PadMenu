package com.feasttime.dishmap.application;

import android.app.Application;

import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.model.RetrofitService;
import com.mob.MobSDK;

import cn.smssdk.SMSSDK;
import okhttp3.OkHttpClient;


public class MyApplication extends Application {


    private static  MyApplication sInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

            //SDKInitializer.initialize(getApplicationContext());

        RetrofitService.init(this);

        // 注册短信验证码sdk
        MobSDK.init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();


    }

    public static Application getInstance() {
        return sInstance;
    }


}
