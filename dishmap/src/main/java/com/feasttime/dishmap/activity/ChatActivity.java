package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.ChatAdapter;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.model.bean.NewTableNofiticationinfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.UtilTools;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;



/**
 * Created by chen on 2017/10/25.
 */

public class ChatActivity extends BaseActivity implements MyDialogs.PersonNumListener {
    private final String TAG = "ChatActivity";

    @Bind(R.id.activity_chat_lv)
    ListView contentLv;

    @Bind(R.id.title_bar_persons_iv)
    ImageView rightPersonsIv;

    @Bind(R.id.title_bar_share_iv)
    ImageView shareIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleTv;

    ChatAdapter mChatAdapter;

    private String storeId = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        storeId = this.getIntent().getStringExtra("STORE_ID");

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
        intent.putExtra("STORE_ID",storeId);
        startService(intent);

        RxBus.getDefault().register(this, WebSocketEvent.class,  new Consumer<WebSocketEvent>() {
            @Override
            public void accept(WebSocketEvent orderEvent) throws Exception {


                //收到信息取出其中的message并显示
                if (orderEvent.jsonData != null) {
                    JSONObject jsonObject = JSON.parseObject(orderEvent.jsonData);
                    if (jsonObject.containsKey("message")) {
                        String message = jsonObject.getString("message");
                        ChatMsgItemInfo chatMsgItemInfo = new ChatMsgItemInfo();
                        chatMsgItemInfo.setIcon(R.mipmap.temp_icon_1);
                        chatMsgItemInfo.setLeft(true);
                        chatMsgItemInfo.setMsg(message);
                        mChatAdapter.addData(chatMsgItemInfo);
                    } else {
                        LogUtil.d(TAG,"websocket not has message key");
                    }
                }


                if (orderEvent.eventType == WebSocketEvent.NEW_TABLE_NOTIFICATION) {
                    NewTableNofiticationinfo newTableNofiticationinfo = JSON.parseObject(orderEvent.jsonData,NewTableNofiticationinfo.class);

                    int toStorePerson = PreferenceUtil.getIntKey(ChatActivity.this,PreferenceUtil.PERSION_NO);

                    if (toStorePerson >= Integer.parseInt(newTableNofiticationinfo.getMinPerson()) && toStorePerson <= Integer.parseInt(newTableNofiticationinfo.getMaxPerson())) {

                        MyDialogs.showBetPriceDialog(ChatActivity.this,storeId);
                    }
                } else if (orderEvent.eventType == WebSocketEvent.USER_GRAP_TABLE) {
                    MyDialogs.showGrapTableSeatDialog(ChatActivity.this,storeId);
                } else if (orderEvent.eventType == WebSocketEvent.PRICE_RANK_CHANGE) {

                } else if (orderEvent.eventType == WebSocketEvent.GRAP_TABLE_RESULT_NOTIFICATION) {
                    JSONObject jsonObject = JSON.parseObject(orderEvent.jsonData);
                    MyDialogs.showGrapTableResultDialog(ChatActivity.this,jsonObject.getString("name"));
                } else if (orderEvent.eventType == WebSocketEvent.BEFORE_TABLES_LIST) {

                }

                LogUtil.d("result", "test activity received data:" + orderEvent.jsonData);
                //ChatMsgItemInfo obj = JSON.parseObject(orderEvent.jsonData,ChatMsgItemInfo.class);
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

    @Override
    public void overInput(int personNum) {

    }
}
