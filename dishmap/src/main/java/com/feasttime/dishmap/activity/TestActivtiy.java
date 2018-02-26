package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.application.MyApplication;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.im.FakeServer;
import com.feasttime.dishmap.im.HttpUtil;
import com.feasttime.dishmap.im.message.CustomizeMessage;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.DeviceTool;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;
import com.jakewharton.rxbinding2.view.RxView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.imlib.model.MessageContent;
import io.rong.message.FileMessage;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;
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

    private static String mSenderIdTest; //发送信息者ID
    private static String mSenderNameTest = "Oliver"; //发送信息者的昵称
    private static String mPortraitUriTest = "http://static.yingyonghui.com/screenshots/1657/1657011_5.jpg"; //获取发送信息者头像的url
    private static String token = "";

    /**
     * 设置接收消息的监听器
     */
    private RongIMClient.OnReceiveMessageListener onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
        @Override
        public boolean onReceived(Message message, int i) {

            if (message.getContent() instanceof TextMessage) {
                Log.d(TAG, "收到文本消息: " + ((TextMessage) message.getContent()).getContent());
                Log.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
                //setMessageRead(message); //设置收到的消息为已读消息
            } else if (message.getContent() instanceof ImageMessage) {
                Log.d(TAG, "收到图片消息, Uri --> " + ((ImageMessage) message.getContent()).getThumUri() + '\n');
            } else if (message.getContent() instanceof VoiceMessage) {
                Log.d(TAG, "收到语音消息,Uri --> " + ((VoiceMessage)message.getContent()).getUri());
                Log.d(TAG, "语音消息时长: " + ((VoiceMessage)message.getContent()).getDuration() + '\n');
            } else if (message.getContent() instanceof FileMessage) {
                Log.d(TAG, "服务端 Uri --> " + ((FileMessage)message.getContent()).getFileUrl() + '\n');
            } else if (message.getContent() instanceof CustomizeMessage) {
                Log.d(TAG, "成功发送自定义消息，它的时间戳: " + ((CustomizeMessage) message.getContent()).getSendTime());
                Log.d(TAG, "自定义消息的内容: " + ((CustomizeMessage) message.getContent()).getContent() + '\n');
            }
//            setMessageId(message.getMessageId());
            return false;
        }
    };


    /**
     * 设置消息为已读消息
     */
    private void setMessageRead(Message message) {
        if (message.getMessageId() > 0) {
            Message.ReceivedStatus status = message.getReceivedStatus();
            status.setRead();
            message.setReceivedStatus(status);
            RongIMClient.getInstance().setMessageReceivedStatus(message.getMessageId(), status, null);
            Toast.makeText(this, "该条消息已设置为已读", Toast.LENGTH_LONG).show();
        }
    }

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


        Button sendBtn = (Button)this.findViewById(R.id.activity_test_join_store_btn);
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextMessage textMessage = TextMessage.obtain("{\"name\":\"chen\"}");
                textMessage.setExtra("guoliang");
                sendTextMessage(textMessage);
            }
        });

//
//        CustomizeMessage customizeMessage = CustomizeMessage.obtain(System.currentTimeMillis(), "融云，国内首家专业的即时通讯云服务提供商");
//        sendCustomizeMessage(customizeMessage);

        RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);

        RongIMClient.getInstance().joinChatRoom("12",100,new RongIMClient.OperationCallback(){
            @Override
            public void onCallback() {
                super.onCallback();
            }

            @Override
            public void onFail(int errorCode) {
                super.onFail(errorCode);
            }

            @Override
            public void onFail(RongIMClient.ErrorCode errorCode) {
                super.onFail(errorCode);
            }

            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        });



        Button connectBtn = (Button)this.findViewById(R.id.activity_test_connect);
        connectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                connect(token);
            }
        });

        getToken();

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
//        RxView.clicks( joinStoreBtn )
//                .throttleFirst( 1 , TimeUnit.SECONDS )   //两秒钟之内只取一个点击事件，防抖操作
//                .subscribe(new Consumer<Object>() {
//                    @Override
//                    public void accept(Object o) throws Exception {
////                        ToastUtil.showToast(TestActivtiy.this,"哈哈",Toast.LENGTH_SHORT);
////                        String jsonData = "";
////                        RxBus.getDefault().post(new WebSocketEvent(WebSocketEvent.PRICE_RANK_CHANGE,jsonData));
////                        MyDialogs.showBetPriceDialog(TestActivtiy.this,"55","66");
//                       // MyDialogs.showGrapTableLoserDialog(TestActivtiy.this,"哈哈");
////                        MyDialogs.showGrapTableSeatDialog(TestActivtiy.this,"2","2");
//                        UtilTools.loginWithWeChat(TestActivtiy.this);
//                    }
//                });

//        MyDialogs.modifyEatPersonNumber(this);
//        MyDialogs.showGrabRedPacketResult(this);
        //UtilTools.loginWithWeChat(MyApplication.getInstance());
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


    /**
     * 发送文本消息
     */
    private void sendTextMessage(MessageContent messageContent) {
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "12315",
                messageContent, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        Log.d(TAG, "发送的文本消息已保存至本地数据库中");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        if (message.getContent() instanceof TextMessage) {
                            Log.d(TAG, "成功发送文本消息: " + ((TextMessage) message.getContent()).getContent());
                            Log.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');
                        }
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
                    }
                });
    }



    /**
     * 通过服务器端请求获取token，客户端不提供获取token的接口
     */
    private void getToken() {
        FakeServer.getToken("12315", mSenderNameTest, mPortraitUriTest, new HttpUtil.OnResponse() {
            @Override
            public void onResponse(int code, String body) {
                if (code == 200) {
                    JSONObject jsonObj = null;
                    try {
                        jsonObj = new JSONObject(body);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    token = jsonObj.optString("token");


                    Log.i(TAG, "获取的 token 值为:\n" + token + '\n');
                } else {
                    Log.i(TAG, "获取 token 失败" + '\n');
                }
            }
        });
    }

    /**
     * 连接融云服务器
     */
    private void connect(String token) {
        RongIMClient.connect(token, new RongIMClient.ConnectCallback() {

            /**
             * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
             */
            @Override
            public void onTokenIncorrect() {
                Log.d(TAG, "Token 错误---onTokenIncorrect---" + '\n');
            }

            /**
             * 连接融云成功
             * @param userid 当前 token
             */
            @Override
            public void onSuccess(String userid) {
                Log.d(TAG, "连接融云成功---onSuccess---用户ID:" + userid + '\n');
            }

            /**
             * 连接融云失败
             * @param errorCode 错误码，可到官网 查看错误码对应的注释
             */
            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                Log.d(TAG, "连接融云失败, 错误码: " + errorCode + '\n');
            }
        });
    }



    /**
     * 发送自定义消息
     */
    public void sendCustomizeMessage(CustomizeMessage customizeMessage) {
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "22",
                customizeMessage, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        Log.d(TAG, "发送的自定义消息已保存至本地数据库中");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        if (message.getContent() instanceof CustomizeMessage) {
                            Log.d(TAG, "成功发送自定义消息，它的时间戳: " + ((CustomizeMessage) message.getContent()).getSendTime());
                            Log.d(TAG, "自定义消息的内容: " + ((CustomizeMessage) message.getContent()).getContent() + '\n');
                        }
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
                    }
                });
    }
}
