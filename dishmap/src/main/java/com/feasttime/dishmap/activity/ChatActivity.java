package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.ChatAdapter;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/10/25.
 */

public class ChatActivity extends BaseActivity {
    @Bind(R.id.activity_chat_lv)
    ListView contentLv;

    @Bind(R.id.title_bar_persons_iv)
    ImageView rightPersonsIv;

    @Bind(R.id.title_bar_share_iv)
    ImageView shareIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleTv;

    ChatAdapter mChatAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        List<ChatMsgItemInfo> datas = new ArrayList<ChatMsgItemInfo>();
        for (int i = 0; i < 10; i++) {
            ChatMsgItemInfo chatMsgItemInfo = new ChatMsgItemInfo();
            chatMsgItemInfo.setMsg(Math.random() + "");
            if (i % 2 == 0) {
                chatMsgItemInfo.setLeft(true);
                chatMsgItemInfo.setIcon(R.mipmap.temp_icon_1);
            } else {
                chatMsgItemInfo.setIcon(R.mipmap.temp_icon_2);
                chatMsgItemInfo.setLeft(false);
            }
            datas.add(chatMsgItemInfo);
        }

        mChatAdapter = new ChatAdapter(this, datas);
        contentLv.setAdapter(mChatAdapter);

        initViews();

        Intent intent = new Intent(this, MyService.class);
//        intent.putExtra("storeId","666666");
//        intent.putExtra("mobileNo","13673668068");
        startService(intent);

        RxBus.getDefault().register(this, WebSocketEvent.class, new Consumer<WebSocketEvent>() {
            @Override
            public void accept(WebSocketEvent orderEvent) throws Exception {
                if (orderEvent.eventType == WebSocketEvent.RECEIVE_SERVER_DATA) {
                    LogUtil.d("result", "test activity received data:" + orderEvent.jsonData);
                    //ChatMsgItemInfo obj = JSON.parseObject(orderEvent.jsonData,ChatMsgItemInfo.class);

                    JSONObject object = JSON.parseObject(orderEvent.jsonData);
                    JSONObject type = (JSONObject) object.get("type");


                    ChatMsgItemInfo chatMsgItemInfo = new ChatMsgItemInfo();
                    int tempRandom = (int) (Math.random() * 10);
                    if (tempRandom % 2 == 0) {
                        chatMsgItemInfo.setIcon(R.mipmap.temp_icon_1);
                        chatMsgItemInfo.setLeft(true);
                    } else {
                        chatMsgItemInfo.setIcon(R.mipmap.temp_icon_2);
                        chatMsgItemInfo.setLeft(false);
                    }
                    chatMsgItemInfo.setMsg(orderEvent.jsonData);
                }
            }
        });
    }


    private void initViews() {
        shareIv.setVisibility(View.GONE);
        rightPersonsIv.setVisibility(View.VISIBLE);
        titleTv.setText("全聚德");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
        stopService(new Intent(this,MyService.class));
    }
}
