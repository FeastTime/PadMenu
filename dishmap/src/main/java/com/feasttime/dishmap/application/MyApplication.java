package com.feasttime.dishmap.application;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiManager;
import android.text.TextUtils;

import com.feasttime.dishmap.config.GlobalConfig;
import com.feasttime.dishmap.im.message.ChatTextMessage;
import com.feasttime.dishmap.im.message.CustomizeMessage;
import com.feasttime.dishmap.im.message.EnterStoreMessage;
import com.feasttime.dishmap.im.message.RedPacketMessage;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.receiver.NetReceiver;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.mob.MobSDK;

import io.rong.imlib.AnnotationNotFoundException;
import io.rong.imlib.RongIMClient;
import io.rong.message.FileMessage;


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

        //注册im融云
        registerImRongYun();
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


    //注册融云
    private void registerImRongYun() {
        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIMClient 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {
            RongIMClient.init(this, GlobalConfig.IM_RONGYUN_APPKEY);
        }

        /**
         * 用于自定义消息的注册, 注册后方能正确识别自定义消息, 建议在init后及时注册，保证自定义消息到达时能正确解析。
         */
        try {
            RongIMClient.registerMessageType(CustomizeMessage.class);
            RongIMClient.registerMessageType(EnterStoreMessage.class);
            RongIMClient.registerMessageType(RedPacketMessage.class);
            RongIMClient.registerMessageType(ChatTextMessage.class);
        } catch (AnnotationNotFoundException e) {
            e.printStackTrace();
        }
    }


    public static String getCurProcessName(Context context) {
        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid)
                return appProcess.processName;
        }
        return null;
    }

}
