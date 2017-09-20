package com.feasttime.dishmap.application;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.feasttime.dishmap.map.MyLocation;


public class MyApplication extends Application {

    MyLocation myLocation;

    @Override
    public void onCreate() {
        super.onCreate();


        SDKInitializer.initialize(getApplicationContext());

    }

    @Override
    public void onTerminate() {
        super.onTerminate();


    }
}
