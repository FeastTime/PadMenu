package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.BaseActivity;
import com.feasttime.dishmap.activity.ChatActivity;
import com.feasttime.dishmap.activity.ExpireCouponActivity;
import com.feasttime.dishmap.adapter.ConversationsAdapter;
import com.feasttime.dishmap.adapter.FragmentCouponAdapter;
import com.feasttime.dishmap.adapter.MessageAdapter;
import com.feasttime.dishmap.im.message.ChatTextMessage;
import com.feasttime.dishmap.im.message.ReceiveRedPackageMessage;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.model.bean.CouponInfo;
import com.feasttime.dishmap.model.bean.MessageItemInfo;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.UtilTools;
import com.yanzhenjie.recyclerview.swipe.SwipeMenu;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuBridge;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuCreator;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItem;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuItemClickListener;
import com.yanzhenjie.recyclerview.swipe.SwipeMenuRecyclerView;
import com.yanzhenjie.recyclerview.swipe.widget.DefaultItemDecoration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
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

    @Bind(R.id.activity_conversations_smrv)
    SwipeMenuRecyclerView conversationsSmrv;

    ConversationsAdapter conversationsAdapter;

    protected RecyclerView.LayoutManager createLayoutManager() {
        return new LinearLayoutManager(this.getActivity());
    }

    protected RecyclerView.ItemDecoration createItemDecoration() {
        return new DefaultItemDecoration(ContextCompat.getColor(this.getActivity(), R.color.divider_color));
    }

    /**
     * RecyclerView的Item的Menu点击监听。
     */
    private SwipeMenuItemClickListener mMenuItemClickListener = new SwipeMenuItemClickListener() {
        @Override
        public void onItemClick(SwipeMenuBridge menuBridge) {
            menuBridge.closeMenu();

            int direction = menuBridge.getDirection(); // 左侧还是右侧菜单。
            int adapterPosition = menuBridge.getAdapterPosition(); // RecyclerView的Item的position。
            int menuPosition = menuBridge.getPosition(); // 菜单在RecyclerView的Item中的Position。




            if (direction == SwipeMenuRecyclerView.RIGHT_DIRECTION) {
                //右侧菜单
                final BaseActivity baseActivity = (BaseActivity) UserConversationFragment.this.getActivity();

                MessageItemInfo messageItemInfo = conversationsAdapter.getItem(menuBridge.getAdapterPosition());
                String storeId = messageItemInfo.getStoreId();

                RongIMClient.getInstance().quitGroup(storeId,new RongIMClient.OperationCallback(){
                    @Override
                    public void onCallback() {
                        super.onCallback();
                    }

                    @Override
                    public void onFail(int errorCode) {
                        super.onFail(errorCode);
                    }

                    @Override
                    public void onFail(RongIMClient.ErrorCode errorCode) {
                        super.onFail(errorCode);
                    }

                    @Override
                    public void onSuccess() {

                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {

                    }
                });

                //取消用户关系
                HashMap<String,Object> infoMap = new HashMap<String,Object>();
                String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
                String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
                infoMap.put("token",token);
                infoMap.put("userId",userId);
                baseActivity.showLoading(null);
                RetrofitService.setRelationshipWithStore(infoMap).subscribe(new Consumer<BaseResponseBean>(){
                    @Override
                    public void accept(BaseResponseBean baseResponseBean) throws Exception {
                        if (baseResponseBean.getResultCode() == 0) {

                        } else {

                        }
                        baseActivity.hideLoading();
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        baseActivity.hideLoading();
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {

                    }
                });

            } else if (direction == SwipeMenuRecyclerView.LEFT_DIRECTION) {
                //左侧菜单

            }
        }
    };

    /**
     * 菜单创建器。
     */
    private SwipeMenuCreator mSwipeMenuCreator = new SwipeMenuCreator() {
        @Override
        public void onCreateMenu(SwipeMenu swipeLeftMenu, SwipeMenu swipeRightMenu, int viewType) {
            int width = (int)UserConversationFragment.this.getResources().getDimension(R.dimen.x150);

            // 1. MATCH_PARENT 自适应高度，保持和Item一样高;
            // 2. 指定具体的高，比如80;
            // 3. WRAP_CONTENT，自身高度，不推荐;
            int height = ViewGroup.LayoutParams.MATCH_PARENT;
            // 添加右侧的，如果不添加，则右侧不会出现菜单。
            {
                SwipeMenuItem deleteItem = new SwipeMenuItem(UserConversationFragment.this.getActivity())
                        .setBackground(R.drawable.selector_green)
                        .setText("删除")
                        .setTextColor(Color.WHITE)
                        .setWidth(width)
                        .setHeight(height);
                swipeRightMenu.addMenuItem(deleteItem);// 添加一个按钮到右侧侧菜单。
            }
        }
    };

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
        return view;
    }

    private void initViews() {
        titleBarBackIv.setVisibility(View.GONE);
        titleBarRightIv.setVisibility(View.GONE);
        titleCenterTv.setText("呼啦圈");
        conversationsAdapter = new ConversationsAdapter(this.getActivity());

        conversationsSmrv.setSwipeMenuCreator(mSwipeMenuCreator);
        conversationsSmrv.setSwipeMenuItemClickListener(mMenuItemClickListener); // Item的Menu点击。

        conversationsSmrv.setLayoutManager(createLayoutManager());
        conversationsSmrv.addItemDecoration(createItemDecoration());

        conversationsSmrv.setLongPressDragEnabled(true); // 长按拖拽，默认关闭。

        loadHistoryMessage();
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
                ArrayList<MessageItemInfo> datasItemList = new ArrayList<MessageItemInfo>();
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
                    datasItemList.add(messageItemInfo);
                }

                conversationsSmrv.setAdapter(conversationsAdapter);
                conversationsAdapter.notifyDataSetChanged(datasItemList);
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
