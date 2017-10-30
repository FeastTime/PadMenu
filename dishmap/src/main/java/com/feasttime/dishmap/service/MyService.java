package com.feasttime.dishmap.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;
import com.feasttime.dishmap.activity.TestActivtiy;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.ToastUtil;

import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

/**
 * Created by chen on 2017/10/29.
 */

public class MyService extends Service {
    private static final String TAG =  "MyService";
    private String requestUrl;

    @Override
    public void onCreate() {
        super.onCreate();

        LogUtil.d(TAG,"myService oncreate");









    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        OkHttpClient okHttpClient = new OkHttpClient();

        String token = "6554455";
        String storeId = "0011";

        RxWebSocketUtil.getInstance().setClient(okHttpClient);
        // show log,default false
        RxWebSocketUtil.getInstance().setShowLog(true);
        requestUrl = WebSocketConfig.wsUrl + "/" + token + "/" + storeId;

        LogUtil.d(TAG,"will connect:" + requestUrl);
        //get StringMsg
        RxWebSocketUtil.getInstance().getWebSocketString(requestUrl)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //连接成功后收到消息
                        LogUtil.d(TAG,"receive websocket:" + s);
                        RxBus.getDefault().post(new WebSocketEvent(WebSocketEvent.RECEIVE_SERVER_DATA,s));
                    }});


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxWebSocketUtil.getInstance().getWebSocketInfo(requestUrl).subscribe(new Consumer<WebSocketInfo>() {
            @Override
            public void accept(WebSocketInfo webSocketInfo) throws Exception {
                WebSocket webSocket = webSocketInfo.getWebSocket();
                webSocket.close(3000,"关闭");
                LogUtil.d(TAG,"myService websocket closed");
            }
        });
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
