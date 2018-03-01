package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.BaseActivity;
import com.feasttime.dishmap.activity.ChatActivity;
import com.feasttime.dishmap.activity.ScanActivity;
import com.feasttime.dishmap.adapter.MessageAdapter;
import com.feasttime.dishmap.im.message.ChatTextMessage;
import com.feasttime.dishmap.im.message.ReceiveRedPackageMessage;
import com.feasttime.dishmap.model.bean.MessageItemInfo;
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

public class UserConversationFragment extends Fragment implements View.OnClickListener {
    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.activity_conversations_lv)
    ListView conversationsLv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversations, container, false);
        ButterKnife.bind(this,view);
        initViews();
        loadHistoryMessage();
        return view;
    }

    private void initViews() {
        titleBarBackIv.setVisibility(View.GONE);
        titleBarRightIv.setVisibility(View.GONE);
        titleCenterTv.setText("呼啦圈");


        conversationsLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageAdapter messageAdapter =  (MessageAdapter) parent.getAdapter();
                MessageItemInfo messageItemInfo = (MessageItemInfo) messageAdapter.getItem(position);

                //跳转到聊天页面
                Context context = UserConversationFragment.this.getActivity();
                Intent intent = new Intent(context, ChatActivity.class);
                intent.putExtra("STORE_ID", messageItemInfo.getStoreId());
                intent.putExtra("STORE_NAME",messageItemInfo.getName());
                startActivity(intent);
            }
        });
    }

    public void loadHistoryMessage() {

        final BaseActivity baseActivity = (BaseActivity) this.getActivity();
        baseActivity.showLoading(null);

        //获取融云历史消息
        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback(){
            @Override
            public void onSuccess(Object o) {
                baseActivity.hideLoading();

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
                    messageItemInfo.setStoreId(conversation.getTargetId());
                    testDatasItemList.add(messageItemInfo);
                }

                MessageAdapter messageAdapter = new MessageAdapter(UserConversationFragment.this.getActivity(),testDatasItemList);
                conversationsLv.setAdapter(messageAdapter);
            }

            @Override
            public void onError(RongIMClient.ErrorCode errorCode) {
                baseActivity.hideLoading();
            }
        }, Conversation.ConversationType.GROUP);
    }


    @Override
    public void onClick(View v) {

    }
}
