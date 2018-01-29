package com.feasttime.dishmap.application;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.PreferenceUtil;
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

        // 如果已经登录，启动长连接
        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);

        if (!TextUtils.isEmpty(userId)){

            Intent intent = new Intent(this, MyService.class);
            startService(intent);
        }

    }

    @Override
    public void onTerminate() {
        super.onTerminate();


    }

    public static Application getInstance() {
        return sInstance;
    }


}
