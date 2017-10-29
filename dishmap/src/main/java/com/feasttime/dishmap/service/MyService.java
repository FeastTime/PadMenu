package com.feasttime.dishmap.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.activity.TestActivtiy;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.ToastUtil;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;

/**
 * Created by chen on 2017/10/29.
 */

public class MyService extends Service {
    private static final String TAG =  "MyService";

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.d(TAG,"myService oncreate");



        OkHttpClient okHttpClient = new OkHttpClient();

        String token = "afffffffff";

        RxWebSocketUtil.getInstance().setClient(okHttpClient);
        // show log,default false
        RxWebSocketUtil.getInstance().setShowLog(true);
        //get StringMsg
        RxWebSocketUtil.getInstance().getWebSocketString(WebSocketConfig.wsUrl)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //连接成功后收到消息
                        LogUtil.d(TAG,"receive websocket:" + s);
                        RxBus.getDefault().post(new WebSocketEvent(WebSocketEvent.RECEIVE_SERVER_DATA,s));
                    }});
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.d(TAG,"myService ondestory");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
