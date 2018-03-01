package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.MessageAdapter;
import com.feasttime.dishmap.im.message.ChatTextMessage;
import com.feasttime.dishmap.im.message.ReceiveRedPackageMessage;
import com.feasttime.dishmap.model.bean.MessageItemInfo;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.UtilTools;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.MessageContent;

/**
 * Created by chen on 2018/3/1.
 */

public class ConversationsListActivity extends BaseActivity{

    @Bind(R.id.activity_conversations_lv)
    ListView contentLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversations);
        ButterKnife.bind(this);

        //获取融云历史消息
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback(){
            @Override
            public void onSuccess(Object o) {

                List<Conversation> resultList = (List<Conversation>)o;
                ArrayList<MessageItemInfo> testDatasItemList = new ArrayList<MessageItemInfo>();
                for(int i = 0 ; i < resultList.size() ; i++) {
                    Conversation conversation = resultList.get(i);
                    MessageContent messageContent = conversation.getLatestMessage();

                    MessageItemInfo messageItemInfo = new MessageItemInfo();

                    String lastMessage = null;
                    if (messageContent instanceof ChatTextMessage) {
                        ChatTextMessage chatTextMessage = ((ChatTextMessage) messageContent);
                        String receiveMsg = chatTextMessage.getContent();
                        JSONObject jsonObject = JSON.parseObject(receiveMsg);
                        lastMessage = jsonObject.getString("message");
                    } else if (messageContent instanceof ReceiveRedPackageMessage) {
                        lastMessage = "[红包消息]";
                    }

                    messageItemInfo.setName(conversation.getConversationTitle());
                    messageItemInfo.setMessage(lastMessage);
                    messageItemInfo.setMsgCount(conversation.getUnreadMessageCount());
                    messageItemInfo.setTime(UtilTools.formateDate(conversation.getSentTime()));
                    testDatasItemList.add(messageItemInfo);
                }

                MessageAdapter messageAdapter = new MessageAdapter(ConversationsListActivity.this,testDatasItemList);
                contentLv.setAdapter(messageAdapter);
                LogUtil.d("111","2222");
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {

            }
        }, Conversation.ConversationType.GROUP);



    }
}
