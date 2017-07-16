package com.feasttime;

import android.app.Application;
import android.util.Log;

import com.feasttime.menu.BuildConfig;
import com.feasttime.model.RetrofitService;
import com.feasttime.tools.LogUtil;


public class MenuApplication extends Application {

    private static  MenuApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        //CrashReport.initCrashReport(getApplicationContext(), "3b4f343c2c", false);
        RetrofitService.init(this);
        if (BuildConfig.DEBUG) {
            LogUtil.DEBUG = true;
        } else {
            LogUtil.DEBUG = false;
        }
    }

    public static Application getInstance() {
        return sInstance;
    }
}
