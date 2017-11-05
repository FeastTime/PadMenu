package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.DeviceTool;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.alibaba.fastjson.JSON;
import com.jakewharton.rxbinding2.view.RxView;

import org.reactivestreams.Subscriber;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;

/**
 * Created by chen on 2017/10/22.
 */

public class TestActivtiy extends BaseActivity {
    private static final String TAG = "TestActivtiy";

    @Bind(R.id.activity_test_join_store_btn)
    Button joinStoreBtn;

    private static String imei = "";
    private static String androidID = "";
    private static String ipv4 = "";
    private static String mac = "";
    private static String mobileNO = "";

    Disposable disposable;

//    String wsUrl = "ws://47.94.16.58:9798/feast-web/websocket/";
//    String wsUrl = "ws://192.168.1.101:8081/websocket";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        imei = DeviceTool.getIMEI(this);
        androidID = DeviceTool.getAndroidId(this);
        ipv4 = DeviceTool.getIP(this);
        mobileNO = DeviceTool.getPhoneNumber(this);
        mac = DeviceTool.getLocalMacAddress(this);

//        startService(new Intent(this, MyService.class));
//        testWebSocket();

//        MyDialogs.showEatDishPersonNumDialog(this);
//
//        startActivity(new Intent(this,ChatActivity.class));

//        startActivity(new Intent(this,MerchantActivity.class));

//        RxBus.getDefault().register(this, WebSocketEvent.class, new Consumer<WebSocketEvent>() {
//            @Override
//            public void accept(WebSocketEvent orderEvent) throws Exception {
//                if (orderEvent.eventType == WebSocketEvent.BEFORE_TABLES_LIST) {
//                    LogUtil.d("result","test activity received data:" + orderEvent.jsonData);
//                    //ChatMsgItemInfo obj = JSON.parseObject(orderEvent.jsonData,ChatMsgItemInfo.class);
//                }
//            }
//        });

//        testWebSocket();

//        joinStoreBtn.setTag("chen");
        RxView.clicks( joinStoreBtn )
                .throttleFirst( 1 , TimeUnit.SECONDS )   //两秒钟之内只取一个点击事件，防抖操作
                .subscribe(new Consumer<Object>() {
                    @Override
                    public void accept(Object o) throws Exception {
//                        ToastUtil.showToast(TestActivtiy.this,"哈哈",Toast.LENGTH_SHORT);
//                        String jsonData = "";
//                        RxBus.getDefault().post(new WebSocketEvent(WebSocketEvent.PRICE_RANK_CHANGE,jsonData));
//                        MyDialogs.showBetPriceDialog(TestActivtiy.this,"55","66");
                        MyDialogs.showGrapTableResultDialog(TestActivtiy.this,"哈哈");
                    }
                });


    }


    private void testWebSocket() {
        OkHttpClient okHttpClient = new OkHttpClient();


        RxWebSocketUtil.getInstance().setClient(okHttpClient);
        // show log,default false
        RxWebSocketUtil.getInstance().setShowLog(true);
        //get StringMsg
        disposable = RxWebSocketUtil.getInstance().getWebSocketString(WebSocketConfig.baseWsUrl+"/nicework")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //连接成功后收到消息
                        ToastUtil.showToast(TestActivtiy.this,"receive:" + s, Toast.LENGTH_SHORT);
                        LogUtil.d(TAG,"receive websocket:" + s);
                    }
                });

    }


//    @OnClick({R.id.activity_test_join_store_btn})
//    @Override
//    public void onClick(View v) {
//        if (v == joinStoreBtn) {
////            Disposable disposable = RxWebSocketUtil.getInstance().getWebSocketString(WebSocketConfig.baseWsUrl).subscribe(new Consumer<String>() {
////                @Override
////                public void accept(String s) throws Exception {
////                    LogUtil.d(TAG,"the result:" + s);
////                }
////            });
////
////            disposable.dispose();
//
//
//              if (!disposable.isDisposed()) {
//                  disposable.dispose();
//              } else {
//                  disposable = RxWebSocketUtil.getInstance().getWebSocketString(WebSocketConfig.baseWsUrl+"/nicework")
//                          .subscribe(new Consumer<String>() {
//                              @Override
//                              public void accept(String s) throws Exception {
//                                  //连接成功后收到消息
//                                  ToastUtil.showToast(TestActivtiy.this,"receive:" + s, Toast.LENGTH_SHORT);
//                                  LogUtil.d(TAG,"receive websocket:" + s);
//                              }
//                          });
//              }
//
//
//
////            HashMap<String,String> requestData = new HashMap<String,String>();
////            requestData.put("imei",imei);
////            requestData.put("androidID",androidID);
////            requestData.put("mac",mac);
////            requestData.put("ipv4",ipv4);
////            requestData.put("mobileNo","15810697038");
////            requestData.put("storeID","00010001");
////            requestData.put("type","1");
////
////            String requestJson = JSON.toJSONString(requestData);
////
////            RxWebSocketUtil.getInstance().asyncSend(WebSocketConfig.wsRequestUrl, requestJson);
//
////            MyDialogs.showBetPriceDialog(this,"666");
//
//
////            Observable.interval(1, TimeUnit.SECONDS)
////                    .take(10)
////                    .subscribeOn(Schedulers.io())
////                    .observeOn(AndroidSchedulers.mainThread())
////                    .subscribe(new Consumer<Long>() {
////                        @Override
////                        public void accept(Long aLong) throws Exception {
////                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
////                            ToastUtil.showToast(TestActivtiy.this,"the time:" + aLong,Toast.LENGTH_SHORT);
////                        }
////                    });
//        }
//    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(new Intent(this, MyService.class));
    }
}
