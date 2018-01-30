package com.feasttime.dishmap.application;

import android.app.Application;
import android.content.Intent;
import android.text.TextUtils;

import com.feasttime.dishmap.config.GlobalConfig;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.mob.MobSDK;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class MyApplication extends Application {


    private static  MyApplication sInstance;
    public static IWXAPI iwxapi;

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

        iwxapi = WXAPIFactory.createWXAPI(this, GlobalConfig.WECHAT_APPID, false);

    }

    @Override
    public void onTerminate() {
        super.onTerminate();


    }

    public static Application getInstance() {
        return sInstance;
    }


}
