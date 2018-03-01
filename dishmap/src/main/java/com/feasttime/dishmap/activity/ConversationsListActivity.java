package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.MessageAdapter;
import com.feasttime.dishmap.model.bean.MessageItemInfo;
import com.feasttime.dishmap.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

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

        RongIMClient.getInstance().getConversationList(new RongIMClient.ResultCallback(){
            @Override
            public void onSuccess(Object o) {

                List<Conversation> resultList = (List<Conversation>)o;
                ArrayList<MessageItemInfo> testDatasItemList = new ArrayList<MessageItemInfo>();
                for(int i = 0 ; i < resultList.size() ; i++) {
                    Conversation conversation = resultList.get(i);
                    MessageItemInfo messageItemInfo = new MessageItemInfo();
                    messageItemInfo.setName(conversation.getConversationTitle());
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
