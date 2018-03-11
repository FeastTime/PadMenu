package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.ChatAdapter;
import com.feasttime.dishmap.adapter.MySeatAdapter;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.im.message.ChatTextMessage;
import com.feasttime.dishmap.im.message.ReceiveRedPackageMessage;
import com.feasttime.dishmap.im.message.ReceivedRedPackageSurprisedMessage;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.model.bean.MyTableInfo;
import com.feasttime.dishmap.model.bean.RedPackageCountDown;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.KeybordS;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.SoftHideKeyBoardUtil;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;


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
    private String storeName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        SoftHideKeyBoardUtil.assistActivity(this);

        storeId = this.getIntent().getStringExtra("STORE_ID");
        userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        storeName = this.getIntent().getStringExtra("STORE_NAME");

        //清除所有未读消息状态
        RongIMClient.getInstance().clearMessagesUnreadStatus(Conversation.ConversationType.GROUP,storeId);

        List<ChatMsgItemInfo> chatMsgItemInfoList = new ArrayList<>();

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

        long lastChangeTime = PreferenceUtil.getLongKey(PreferenceUtil.DINER_COUNT_TIME + storeId);
        if (System.currentTimeMillis() - lastChangeTime > 45 * 60 * 1000){

            MyDialogs.modifyEatPersonNumber(ChatActivity.this, storeId);
        }



//        List<Conversation> myList = RongIMClient.getInstance().getHistoryMessages();

        requestCounDownData(); //请求倒计时数据
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

        HashMap<String, String > requestData = new HashMap<>();
        requestData.put("message", inputMessageStr);
        requestData.put("type", WebSocketEvent.SEND_MESSAGE+"");
        requestData.put("storeId", storeId);
        requestData.put("userId",userId);
        requestData.put("userIcon",PreferenceUtil.getStringKey(PreferenceUtil.USER_ICON));


//        UtilTools.requestByWebSocket(this, requestData);

        ChatTextMessage textMessage = ChatTextMessage.obtain(System.currentTimeMillis(),JSON.toJSONString(requestData));
        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.GROUP, storeId,
                textMessage, null, null, new IRongCallback.ISendMessageCallback() {
                    @Override
                    public void onAttached(Message message) {
                        Log.d(TAG, "发送的文本消息已保存至本地数据库中");
                    }

                    @Override
                    public void onSuccess(Message message) {
                        if (message.getContent() instanceof TextMessage) {
                            String sendMessage = ((TextMessage) message.getContent()).getContent();
                            Log.d(TAG, "成功发送文本消息: " + ((TextMessage) message.getContent()).getContent());
                        } else if (message.getContent() instanceof ChatTextMessage) {
                            String sendMessage = ((ChatTextMessage) message.getContent()).getContent();
                            Log.d(TAG, "成功发送文本消息: " + ((ChatTextMessage) message.getContent()).getContent());
                            JSONObject jsonObject = JSON.parseObject(sendMessage);
                            recevieMessageAndAdd(jsonObject.getString("message"),message.getSentTime(),jsonObject.getString("userId"),jsonObject.getString("userIcon"));
                        }
                    }

                    @Override
                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
                    }
                });

    }


    private void initViews() {

        contentLv.setOnLongClickListener(null);
        shareIv.setVisibility(View.GONE);
        rightTitleBarIv.setVisibility(View.VISIBLE);
        titleTv.setText(storeName);

        rightTitleBarIv.requestFocus();

        KeybordS.closeKeybord(inputMessage, this);

        // 获取远程消息记录
        RongIMClient.getInstance().getHistoryMessages(Conversation.ConversationType.GROUP, storeId, 0, 50, new RongIMClient.ResultCallback<List<Message>>() {
            @Override
            public void onSuccess(List<Message> messages) {
                if (messages != null) {
                    LogUtil.d(TAG, "远端服务器存储的历史消息个数为 " + messages.size());

                    //处理老的消息
                    for (int i = messages.size() - 1; i >= 0; i--) {
                        Message message = messages.get(i);
                        handleRongImMessageLogic(message);
                    }
                } else
                    LogUtil.d(TAG, "远端服务器存储的历史消息个数为 0" + '\n');

                //处理完消息后，去接受服务器消息
                RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                LogUtil.d(TAG, "读取远端服务器存储的历史消息失败，错误码: " + errorCode.getValue() + '\n');

                //报错之后也要去接受历史消息
                RongIMClient.setOnReceiveMessageListener(onReceiveMessageListener);
            }
        });

        // 获取倒计时数据

        HashMap<String,Object> infoMap = new HashMap<>();
        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        infoMap.put("token",token);
        infoMap.put("userId",userId);
        infoMap.put("storeId",storeId);

//        RetrofitService.queryRedPackageCountDown(infoMap).subscribe(new Consumer<RedPackageCountDown>(){
//            @Override
//            public void accept(RedPackageCountDown redPackageCountDown) throws Exception {
//
//                if (redPackageCountDown.getResultCode() == 0) {
////                    MySeatAdapter mySeatAdapter = new MySeatAdapter(ChatActivity.this,myTableInfo.getTablesList());
////                    contentLv.setAdapter(mySeatAdapter);
//                } else {
//                }
//
//                hideLoading();
//            }
//        }, new Consumer<Throwable>() {
//            @Override
//            public void accept(Throwable throwable) throws Exception {
//            }
//        }, new Action() {
//            @Override
//            public void run() throws Exception {
//            }
//        });


    }

    @OnClick(R.id.title_bar_right_iv)
    public void topRightBarClick(View view){
        MyDialogs.modifyEatPersonNumber(ChatActivity.this, storeId);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        RongIMClient.setOnReceiveMessageListener(null);
    }

    @Override
    public void overInput(int personNum) {

    }

    public interface OpenWaitingListener {

        void onSend();

        void onResult();

        void onError();
    }

    //收到消息后添加到列表并刷新
    private void recevieMessageAndAdd(String receiveMsg,long receiveTime,String imUserId,String userIcon) {
        ChatMsgItemInfo chatMsgItemInfo = new ChatMsgItemInfo();
        chatMsgItemInfo.setRedPackage(false);
        chatMsgItemInfo.setTime(receiveTime);

        //头像不为空添加头像数据
        if (!TextUtils.isEmpty(userIcon)) {
            chatMsgItemInfo.setIcon(userIcon);
        }

        if (TextUtils.equals(imUserId,userId)) {
            chatMsgItemInfo.setLeft(false);
        } else {
            chatMsgItemInfo.setLeft(true);
        }

        chatMsgItemInfo.setMsg(receiveMsg);
        mChatAdapter.addData(chatMsgItemInfo);
    }

    /**
     * 设置接收消息的监听器
     */
    private RongIMClient.OnReceiveMessageListener onReceiveMessageListener = new RongIMClient.OnReceiveMessageListener() {
        @Override
        public boolean onReceived(final Message message, int i) {
            handleRongImMessageLogic(message);
            return false;
        }
    };


    //处理融云收到消息逻辑
    private void handleRongImMessageLogic(final Message message) {
        try {
            ChatActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Log.d(TAG, "收到融云消息: " + message.getContent());
                    if (message.getContent() instanceof ChatTextMessage) {
                        String receiveMsg = ((ChatTextMessage) message.getContent()).getContent();
                        Log.d(TAG, "收到文本消息: " + receiveMsg);
                        JSONObject jsonObject = JSON.parseObject(receiveMsg);
                        recevieMessageAndAdd(jsonObject.getString("message"),message.getReceivedTime()
                                ,   jsonObject.getString("userId")
                                , jsonObject.getString("userIcon"));

                    } else if (message.getContent() instanceof ReceiveRedPackageMessage) {
                        String receiveMsg = ((ReceiveRedPackageMessage) message.getContent()).getContent();
                        Log.d(TAG, "收到红包消息: " + receiveMsg);
                        JSONObject jsonObject = JSON.parseObject(receiveMsg);

                        // 红包id
                        String redPackageId = jsonObject.getString("redPackageId");
//                    String nickname = jsonObject.getString("nickname");
                        String userIcon = jsonObject.getString("userIcon");
                        String withMessage = jsonObject.getString("withMessage");

                        ChatMsgItemInfo chatMsgItemInfo = new ChatMsgItemInfo();
                        chatMsgItemInfo.setRedPackage(true);
                        chatMsgItemInfo.setTime(message.getSentTime());

                        // 左边添加别人的消息
                        chatMsgItemInfo.setIcon(userIcon);
                        chatMsgItemInfo.setLeft(true);
                        chatMsgItemInfo.setMsg(withMessage);
                        chatMsgItemInfo.setRedPackageId(redPackageId);
                        chatMsgItemInfo.setRedPackage(true);

                        mChatAdapter.addData(chatMsgItemInfo);
                    } else if (message.getContent() instanceof ReceivedRedPackageSurprisedMessage) {

                    }

                    LogUtil.d(TAG,"the message status:" + message.getReceivedStatus().isRead());

//                    //如果消息未读过就设置为已读
//                    if (!message.getReceivedStatus().isRead()) {
//                        setMessageRead(message); //设置收到的消息为已读消息
//                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置消息为已读消息
     */
    private void setMessageRead(Message message) {
        if (message.getMessageId() > 0) {
            io.rong.imlib.model.Message.ReceivedStatus status = message.getReceivedStatus();
            status.setRead();
            message.setReceivedStatus(status);
            RongIMClient.getInstance().setMessageReceivedStatus(message.getMessageId(), status, null);
        }
    }

    //请求倒计时数据
    private void requestCounDownData() {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        infoMap.put("token",token);
        infoMap.put("userId",userId);
        infoMap.put("storeId",storeId);

        RetrofitService.countDown(infoMap).subscribe(new Consumer<BaseResponseBean>(){
            @Override
            public void accept(BaseResponseBean baseResponseBean) throws Exception {
                if (baseResponseBean.getResultCode() == 0) {

                } else {

                }
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
            }
        });
    }
}
