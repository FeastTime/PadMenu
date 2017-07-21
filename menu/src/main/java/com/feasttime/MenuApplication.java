package com.feasttime;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.danikula.videocache.HttpProxyCacheServer;
import com.feasttime.menu.BuildConfig;
import com.feasttime.model.RetrofitService;
import com.feasttime.tools.LogUtil;


public class MenuApplication extends Application {

    private static  MenuApplication sInstance;
    private HttpProxyCacheServer proxy;

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




    public static HttpProxyCacheServer getProxy(Context context) {
        MenuApplication app = (MenuApplication) context.getApplicationContext();
        return app.proxy == null ? (app.proxy = app.newProxy()) : app.proxy;
    }

    private HttpProxyCacheServer newProxy() {
        return new HttpProxyCacheServer.Builder(this)
                .maxCacheSize(1024 * 1024 * 1024)       // 1 Gb for cache
                .build();
    }
}
