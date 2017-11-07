package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
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
import com.feasttime.dishmap.model.bean.BidResultInfo;
import com.feasttime.dishmap.model.bean.BidResultItem;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.model.bean.GrobResultInfo;
import com.feasttime.dishmap.model.bean.NewTableNofiticationinfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.service.MyService;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.StringUtils;

import org.w3c.dom.Text;

import java.util.ArrayList;
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
                    Log.d("lixiaoqing", "return json is : " + orderEvent.jsonData);
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

                        MyDialogs.showBetPriceDialog(ChatActivity.this,storeId,newTableNofiticationinfo.getBid(),newTableNofiticationinfo.getTimeLimit(), PreferenceUtil.getStringKey(PreferenceUtil.MOBILE_NO));
                    }
                } else if (orderEvent.eventType == WebSocketEvent.BID_TABLE_RESULT_NOTIFICATION) { // 竞价结果通知

                    String myPhone = PreferenceUtil.getStringKey(PreferenceUtil.MOBILE_NO);

                    Log.d("lixiaoqing", "竞价结果通知");
//                    JSONObject jsonObject = JSON.parseObject(orderEvent.jsonData);
                    BidResultInfo bidResultInfo = JSON.parseObject(orderEvent.jsonData,BidResultInfo.class);

                    List<BidResultItem> bidResultItemList = bidResultInfo.getData();

                    boolean canGrob = false;
                    if (null != bidResultItemList && bidResultItemList.size()>1){
                        for (BidResultItem bidResultItem : bidResultItemList){
                            if (TextUtils.equals(bidResultItem.getUserId(), myPhone)){
                                canGrob = true;
                            }
                        }
                    }

                    Log.d("lixiaoqing", "竞价结果通知 size " + bidResultItemList.size());
                    if (bidResultItemList.size() == 1) {
                        //仅仅一个人中奖

                        BidResultItem bidResultItemInfo = bidResultItemList.get(0);

                        Log.d("lixiaoqing", "竞价结果通知 size " + bidResultItemInfo.getUserId()  +"---" + myPhone);
                        if (TextUtils.equals(myPhone,bidResultItemInfo.getUserId())) {
                            //我中奖了

                            Log.d("lixiaoqing", "我中奖了");
                            MyDialogs.showGrapTableWinnerDialog(ChatActivity.this,bidResultItemInfo.getUserId());
                        } else {
                            //我没中奖
                            Log.d("lixiaoqing", "没中奖");
                            MyDialogs.showGrapTableLoserDialog(ChatActivity.this,bidResultItemInfo.getUserId());
                        }
                    } else if ((canGrob && bidResultItemList.size() > 1) || bidResultItemList.size() == 0) {
                        //多人中奖再抢一次
                        MyDialogs.showGrapTableSeatDialog(ChatActivity.this,storeId,bidResultInfo.getBid());
                    } else {
                        LogUtil.d(TAG,"the server data is error");
                    }

                } else if (orderEvent.eventType == WebSocketEvent.BEFORE_TABLES_LIST) {

                } else if (orderEvent.eventType == WebSocketEvent.GROB_RESULT_NOTIFICATION) {

                    Log.d("lixiaoqing", "抢桌位结果通知");
                    GrobResultInfo bidResultInfo = JSON.parseObject(orderEvent.jsonData,GrobResultInfo.class);


                    Log.d("lixiaoqing", "抢桌位结果通知 : " + bidResultInfo.getBid() + "---" + bidResultInfo.getUserID() +"---"+ bidResultInfo.getResultCode());

                    if (null != bidResultInfo && !TextUtils.isEmpty(bidResultInfo.getUserID())){

                        String myPhone = PreferenceUtil.getStringKey(PreferenceUtil.MOBILE_NO);

                        // 关闭抢的对话框
                        MyDialogs.closeGrapTableSeatDialog();

                        if (TextUtils.equals(myPhone, bidResultInfo.getUserID())){
                            //我中奖了

                            Log.d("lixiaoqing", "我中奖了");
                            MyDialogs.showGrapTableWinnerDialog(ChatActivity.this,bidResultInfo.getUserID());
                        } else {
                            //我没中奖
                            Log.d("lixiaoqing", "没中奖");
                            MyDialogs.showGrapTableLoserDialog(ChatActivity.this,bidResultInfo.getUserID());
                        }
                    }

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
