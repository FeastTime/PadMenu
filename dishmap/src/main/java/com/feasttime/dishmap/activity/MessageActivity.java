package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.MessageAdapter;
import com.feasttime.dishmap.model.bean.MessageItemInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2018/1/19.
 */

public class MessageActivity extends BaseActivity {

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Bind(R.id.activity_message_lv)
    ListView contentLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("消息");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);


        ArrayList<MessageItemInfo> testDatasItemList = new ArrayList<MessageItemInfo>();
        for(int i = 0 ; i < 10 ; i++) {
            MessageItemInfo messageItemInfo = new MessageItemInfo();
            messageItemInfo.setName(i * 10  + "");
            testDatasItemList.add(messageItemInfo);
        }

        MessageAdapter messageAdapter = new MessageAdapter(this,testDatasItemList);
        contentLv.setAdapter(messageAdapter);
    }
}
