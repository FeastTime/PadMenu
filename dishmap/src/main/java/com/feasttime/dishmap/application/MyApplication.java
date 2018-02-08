package com.feasttime.dishmap.application;

import android.app.Application;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.receiver.NetReceiver;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.mob.MobSDK;

public class MyApplication extends Application {


    private static  MyApplication sInstance;
    private static NetReceiver netReceiver;

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

        // 注册wifi监听器
        netReceiver = new NetReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        this.registerReceiver(netReceiver, filter);
    }

    @Override
    public void onTerminate() {

        stopService(new Intent(this, MyService.class));

        if (null != netReceiver){

            this.unregisterReceiver(netReceiver);
        }

        super.onTerminate();

    }

    public static Application getInstance() {
        return sInstance;
    }


}
