package com.feasttime.dishmap.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;
import com.feasttime.dishmap.activity.MerchantActivity;
import com.feasttime.dishmap.activity.TestActivtiy;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;


import org.reactivestreams.Subscription;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;
import okhttp3.WebSocket;

/**
 * Created by chen on 2017/10/29.
 */

public class MyService extends Service {
    private static final String TAG =  "MyService";

    private Disposable mDisposable;

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
        //如果多次请求service 那么先结束之前的连接
        closeWebSocket();

        String storeId = intent.getStringExtra("STORE_ID");

        OkHttpClient okHttpClient = new OkHttpClient();

        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);


        RxWebSocketUtil.getInstance().setClient(okHttpClient);
        // show log,default false
        RxWebSocketUtil.getInstance().setShowLog(true);

        final String requestUrl = WebSocketConfig.baseWsUrl + "/" + token + "/" + storeId ;

        LogUtil.d(TAG,"will connect:" + requestUrl);
        //get StringMsg
        mDisposable = RxWebSocketUtil.getInstance().getWebSocketString(requestUrl)
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {

                        if (!TextUtils.isEmpty(s) && s.equals("success666success")){
                            WebSocketConfig.wsRequestUrl = requestUrl;
                        } else {

                            try{
                                JSONObject jsonObject = JSON.parseObject(s);
                                String type = jsonObject.getString("type");
                                RxBus.getDefault().post(new WebSocketEvent(Integer.parseInt(type),s));

                            } catch (Exception e){
                                e.printStackTrace();
                                LogUtil.d(TAG,"receive json pase exception");
                            }
                        }

                    }});


        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        closeWebSocket();
        LogUtil.d(TAG,"myService ondestory");
    }

    private void closeWebSocket() {
        if (mDisposable != null && !mDisposable.isDisposed()) {
            mDisposable.dispose();
        }
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
