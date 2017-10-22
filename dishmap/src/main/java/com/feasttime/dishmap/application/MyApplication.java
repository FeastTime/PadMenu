package com.feasttime.dishmap.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.map.MyLocation;
import com.feasttime.dishmap.model.RetrofitService;

import okhttp3.OkHttpClient;


public class MyApplication extends Application {

    MyLocation myLocation;

    private static  MyApplication sInstance;


    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        SDKInitializer.initialize(getApplicationContext());

        RetrofitService.init(this);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();


    }

    public static Application getInstance() {
        return sInstance;
    }


}
