package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.ChatAdapter;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

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



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        ButterKnife.bind(this);

        List<ChatMsgItemInfo> datas = new ArrayList<ChatMsgItemInfo>();
        for (int i = 0 ; i < 10 ; i++) {
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

        ChatAdapter chatAdapter = new ChatAdapter(this,datas);
        contentLv.setAdapter(chatAdapter);

        initViews();
    }


    private void initViews() {
        shareIv.setVisibility(View.GONE);
        rightPersonsIv.setVisibility(View.VISIBLE);
        titleTv.setText("全聚德");
    }

}
