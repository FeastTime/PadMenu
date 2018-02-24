package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.ChatAdapter;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.im.message.CustomizeMessage;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.model.bean.CouponChildListItemInfo;
import com.feasttime.dishmap.model.bean.MyTableItemInfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.SoftHideKeyBoardUtil;
import com.feasttime.dishmap.utils.UtilTools;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.FileMessage;
import io.rong.message.ImageMessage;
import io.rong.message.TextMessage;
import io.rong.message.VoiceMessage;


/**
 *
 * Created by chen on 2017/10/25.
 */

public class ChatActivity extends BaseActivity implements MyDialogs.PersonNumListener {

    private final String TAG = "ChatActivity";

    @Bind(R.id.activity_chat_lv)
    ListView contentLv;

    @Bind(R.id.title_bar_right_iv)
    ImageView rightTitleBarIv;

    @Bind(R.id.title_bar_share_iv)
    ImageView shareIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleTv;

    @Bind(R.id.input_message)
    EditText inputMessage;

    ChatAdapter mChatAdapter;

    private String storeId = "";
    private String userId = "";
    private String userIcon = "";
    private String storeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        SoftHideKeyBoardUtil.assistActivity(this);

        storeId = this.getIntent().getStringExtra("STORE_ID");
        userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        userIcon = PreferenceUtil.getStringKey(PreferenceUtil.USER_ICON);
        storeName = this.getIntent().getStringExtra("STORE_NAME");

        List<ChatMsgItemInfo> chatMsgItemInfoList = new ArrayList<>();

        RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);

        mChatAdapter = new ChatAdapter(this, chatMsgItemInfoList, storeId, new OpenWaitingListener() {
            @Override
            public void onSend() {
                Log.d(TAG, "onSend");
                ChatActivity.this.showLoading("");
            }

            @Override
            public void onResult() {
                Log.d(TAG, "onResult");
                ChatActivity.this.hideLoading();
            }

            @Override
            public void onError() {

                Log.d(TAG, "onError");
                ChatActivity.this.hideLoading();
            }
        });
        contentLv.setAdapter(mChatAdapter);

        initViews();

        RxBus.getDefault().register(this, WebSocketEvent.class,  new Consumer<WebSocketEvent>() {
            @Override
            public void accept(WebSocketEvent orderEvent) throws Exception {

                if (null == orderEvent.jsonData) {
                    return;
                }

                // 收到信息取出其中的message并显示
                Log.d(TAG, "return json is : " + orderEvent.jsonData);

                JSONObject jsonObject = JSON.parseObject(orderEvent.jsonData);

                if (jsonObject.containsKey("withMessage")) {

                    String withMessage = jsonObject.getString("withMessage");
                    ChatMsgItemInfo chatMsgItemInfo = new ChatMsgItemInfo();
                    chatMsgItemInfo.setIcon(jsonObject.getString("userIcon"));
                    chatMsgItemInfo.setLeft(true);
                    chatMsgItemInfo.setMsg(withMessage);

                    mChatAdapter.addData(chatMsgItemInfo);
                }

                String senderUserId = jsonObject.getString("userId");

                if (TextUtils.isEmpty(senderUserId)) {
                    return;
                }

                // 收到新消息
                if (orderEvent.eventType == WebSocketEvent.RECEIVED_MESSAGE) {



                }
                // 收到红包
                else if(orderEvent.eventType == WebSocketEvent.RECEIVED_RED_PACKAGE) {


                    // 红包id
                    String redPackageId = jsonObject.getString("redPackageId");
//                    String nickname = jsonObject.getString("nickname");
                    String userIcon = jsonObject.getString("userIcon");
                    String withMessage = jsonObject.getString("withMessage");

                    ChatMsgItemInfo chatMsgItemInfo = new ChatMsgItemInfo();
                    chatMsgItemInfo.setRedPackage(true);
                    chatMsgItemInfo.setTime(jsonObject.getString("date"));

                    // 左边添加别人的消息
                    chatMsgItemInfo.setIcon(userIcon);
                    chatMsgItemInfo.setLeft(true);
                    chatMsgItemInfo.setMsg(withMessage);
                    chatMsgItemInfo.setRedPackageId(redPackageId);
                    chatMsgItemInfo.setRedPackage(true);

                    mChatAdapter.addData(chatMsgItemInfo);
                }

                // 拆开红包通知
                else if(orderEvent.eventType == WebSocketEvent.RECEIVED_RED_PACKAGE_SURPRISED) {

                    ChatActivity.this.hideLoading();

                    String message = jsonObject.getString("message");

                    MyTableItemInfo tableInfo = jsonObject.getObject("tableInfo", MyTableItemInfo.class);
                    CouponChildListItemInfo couponInfo = jsonObject.getObject("couponInfo", CouponChildListItemInfo.class);


                    //  获得桌位
                    if (null != tableInfo){

                        String title = "座位";
                        String detail = "恭喜您！\n成功抢到座位\n号码：" + tableInfo.getTableId();
                        String description = "领取座位后座位预留" + tableInfo.getRecieveTime() + "分钟";
                        MyDialogs.showGrapTableWinnerDialog(ChatActivity.this, title, detail, description);
                    }
                    //  获得优惠券
                    else if (null != couponInfo){

                        String title = "优惠券";
                        String detail = "恭喜您！\n抢到"+couponInfo.getCouponTitle()+"一张";
                        String description = "已放入您的优惠券卡包";
                        MyDialogs.showGrapTableWinnerDialog(ChatActivity.this, title, detail, description);
                    }
                    // 什么也没得到
                    else {
                        Toast.makeText(ChatActivity.this, message, Toast.LENGTH_SHORT).show();
                        return;
                    }

                }

                LogUtil.d(TAG, "test activity received data:" + orderEvent.jsonData);

            }
        });

        MyDialogs.modifyEatPersonNumber(ChatActivity.this, storeId);

    }


    /**
     * 发送消息
     *
     * @param view View
     */
    public void sendMessage(View view){

        String inputMessageStr = inputMessage.getText().toString();
        inputMessage.setText("");
        if (TextUtils.isEmpty(inputMessageStr)){
            return;
        }

//        HashMap<String, String > requestData = new HashMap<>();
//        requestData.put("message", inputMessageStr);
//        requestData.put("type", WebSocketEvent.SEND_MESSAGE+"");
//        requestData.put("storeId", storeId);
//
//        UtilTools.requestByWebSocket(this, requestData);

        TextMessage textMessage = TextMessage.obtain(inputMessageStr);
        textMessage.setExtra("");
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.PRIVATE, "12315",
                textMessage, null, null, new IRongCallback.ISendMessageCallback() {
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


    private void initViews() {
        shareIv.setVisibility(View.GONE);
        rightTitleBarIv.setVisibility(View.VISIBLE);
        titleTv.setText(storeName);



    }

    @OnClick(R.id.title_bar_right_iv)
    public void topRightBarClick(View view){
        MyDialogs.modifyEatPersonNumber(ChatActivity.this, storeId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);

    }

    @Override
    public void overInput(int personNum) {

    }

    public interface OpenWaitingListener {

        void onSend();

        void onResult();

        void onError();
    }


    /**
     * 设置接收消息的监听器
     */
    private RongIMClient.OnReceiveMessageListener onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
        @Override
        public boolean onReceived(Message message, int i) {

            if (message.getContent() instanceof TextMessage) {
                Log.d(TAG, "收到文本消息: " + ((TextMessage) message.getContent()).getContent());
                Log.d(TAG, "文本消息的附加信息: " + ((TextMessage) message.getContent()).getExtra() + '\n');

                String receiveMsg = ((TextMessage) message.getContent()).getContent();

                ChatMsgItemInfo chatMsgItemInfo = new ChatMsgItemInfo();
                chatMsgItemInfo.setRedPackage(false);
                chatMsgItemInfo.setTime(UtilTools.formateDate(message.getReceivedTime()));

//                Log.d(TAG, "time ----time   " + jsonObject.getString("date"));

//                if (userId.equals(senderUserId)) {
//                    // 右边添加自己的消息
//                    chatMsgItemInfo.setIcon(userIcon);
//                    chatMsgItemInfo.setLeft(false);
//                    chatMsgItemInfo.setMsg(message);
//
//                } else {
                    // 左边添加别人的消息
                    //chatMsgItemInfo.setIcon(jsonObject.getString("userIcon"));
                    chatMsgItemInfo.setLeft(true);
                    chatMsgItemInfo.setMsg(receiveMsg);
//                }
                mChatAdapter.addData(chatMsgItemInfo);

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
}
