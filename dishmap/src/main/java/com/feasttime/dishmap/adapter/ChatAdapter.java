package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.content.Intent;
import android.preference.Preference;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.BaseActivity;
import com.feasttime.dishmap.activity.ChatActivity;
import com.feasttime.dishmap.activity.MySeatActivity;
import com.feasttime.dishmap.activity.OpenedRedPackageActivity;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.im.message.OpenRedPackageMessage;
import com.feasttime.dishmap.im.message.ReceivedRedPackageSurprisedMessage;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.model.bean.CouponChildListItemInfo;
import com.feasttime.dishmap.model.bean.MyTableInfo;
import com.feasttime.dishmap.model.bean.MyTableItemInfo;
import com.feasttime.dishmap.model.bean.ReceivedRedPackageInfo;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;

/**
 *
 * Created by chen on 2017/10/25.
 */

public class ChatAdapter extends BaseAdapter {
    private static final String TAG = "ChatAdapter";

    private ChatActivity.OpenWaitingListener openWaitingListener;
    private List<ChatMsgItemInfo> dataList = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private String storeId;
    private Context context;
    private String userId;

    public ChatAdapter(Context context,List<ChatMsgItemInfo> chatMsgItemInfoList, String storeId, ChatActivity.OpenWaitingListener openWaitingListener) {
        dataList = chatMsgItemInfoList;
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.storeId = storeId;
        this.openWaitingListener = openWaitingListener;
        userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
    }

    public void addData(ChatMsgItemInfo chatMsgItemInfo) {
        dataList.add(chatMsgItemInfo);
        this.notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_chat_listview_item_layout,
                    parent, false);
            holder = new ViewHolder();
            // 时间
            holder.timeTv = (TextView) convertView.findViewById(R.id.activity_chat_listview_item_layout_time_tv);

            // 左侧消息
            holder.leftView = convertView.findViewById(R.id.activity_chat_listview_item_layout_left_ll);
            holder.leftIconIv = (ImageView) convertView.findViewById(R.id.activity_chat_listview_item_layout_left_icon_iv);
            // 红包
            holder.leftRedPackageLayout = (RelativeLayout) convertView.findViewById(R.id.activity_chat_listview_item_layout_left_red_package);
            holder.leftRedPackageImage = (ImageView) convertView.findViewById(R.id.activity_chat_listview_item_layout_left_red_package_image);
            // 文字
            holder.leftMessageLayout = (RelativeLayout) convertView.findViewById(R.id.activity_chat_listview_item_layout_left_msg);
            holder.leftMsgTv = (TextView) convertView.findViewById(R.id.activity_chat_listview_item_layout_left_msg_tv);

            // 右侧消息
            holder.rightView = convertView.findViewById(R.id.activity_chat_listview_item_layout_right_ll);
            holder.rightIconIv = (ImageView) convertView.findViewById(R.id.activity_chat_listview_item_layout_right_icon_iv);
            // 红包
            holder.rightRedPackageLayout = (RelativeLayout) convertView.findViewById(R.id.activity_chat_listview_item_layout_right_red_package);
            holder.rightRedPackageImage = (ImageView) convertView.findViewById(R.id.activity_chat_listview_item_layout_right_red_package_image);
            // 文字
            holder.rightMessageLayout = (RelativeLayout) convertView.findViewById(R.id.activity_chat_listview_item_layout_right_msg);
            holder.rightMsgTv = (TextView) convertView.findViewById(R.id.activity_chat_listview_item_layout_right_msg_tv);

            convertView.setTag(holder);
        }

        else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ChatMsgItemInfo chatMsgItemInfo = dataList.get(position);

        holder.timeTv.setText(UtilTools.formateDateForChat(chatMsgItemInfo.getTime()));

        if (chatMsgItemInfo.isLeft()) {

            holder.rightView.setVisibility(View.GONE);
            holder.leftView.setVisibility(View.VISIBLE);

            if (chatMsgItemInfo.isRedPackage()){

                holder.leftRedPackageLayout.setVisibility(View.VISIBLE);
                holder.leftMessageLayout.setVisibility(View.GONE);

                if (chatMsgItemInfo.isRedPackageUsed()){

                    holder.leftRedPackageImage.setImageResource(R.mipmap.red_package_png_used);
                } else {

                    holder.leftRedPackageImage.setImageResource(R.mipmap.red_package_png);
                }

                holder.leftRedPackageImage.setTag(R.id.red_package_id, chatMsgItemInfo.getRedPackageId());

                if (null != chatMsgItemInfo.getRedPackageId()){

                    // 红包点击事件
                    holder.leftRedPackageImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            if (chatMsgItemInfo.isRedPackageUsed()){

                                openRedPackageDetail(chatMsgItemInfo.getRedPackageId());
                                return;
                            }

                            HashMap<String, Object > requestData = new HashMap<String, Object>();

                            String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
                            String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);

                            requestData.put("redPackageId", chatMsgItemInfo.getRedPackageId());
                            requestData.put("type", WebSocketEvent.OPEN_RED_PACKAGE+"");
                            requestData.put("storeId", storeId);
                            requestData.put("userId",userId);
                            requestData.put("token",token);

                            final BaseActivity baseActivity = (BaseActivity) context;

                            baseActivity.showLoading(null);
                            RetrofitService.takeRedPackage(requestData).subscribe(new Consumer<ReceivedRedPackageInfo>(){
                                @Override
                                public void accept(ReceivedRedPackageInfo receivedRedPackageInfo) throws Exception {
                                    if (receivedRedPackageInfo.getResultCode() == 0) {

                                        switch (receivedRedPackageInfo.getTakeRedPackageResultType()){

                                            case ReceivedRedPackageInfo.RESULT_TYPE_EMPTY :
                                                MyDialogs.showEmptyRedPackage(context, chatMsgItemInfo.getRedPackageId()+"", PreferenceUtil.getStringKey(PreferenceUtil.USER_ICON));
                                                break;

                                            case ReceivedRedPackageInfo.RESULT_TYPE_LUCKY :

                                                MyTableItemInfo tableInfo = receivedRedPackageInfo.getTableInfo();
                                                CouponChildListItemInfo couponInfo = receivedRedPackageInfo.getCouponInfo();

                                                //  获得桌位
                                                if (null != tableInfo){

                                                    String title = "座位";
                                                    String detail = "恭喜您！\n成功抢到座位\n号码：" + tableInfo.getTableId();
                                                    String description = "领取座位后座位预留" + tableInfo.getRecieveTime() + "分钟";
                                                    MyDialogs.showGrapTableWinnerDialog(context, title, detail, description);
                                                }
                                                //  获得优惠券
                                                else if (null != couponInfo){

                                                    String title = "优惠券";
                                                    String detail = "恭喜您！\n抢到"+couponInfo.getCouponTitle()+"一张";
                                                    String description = "已放入您的优惠券卡包";
                                                    MyDialogs.showGrapTableWinnerDialog(context, title, detail, description);
                                                } else {
                                                    Toast.makeText(context, receivedRedPackageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                                }

                                                break;
                                            case ReceivedRedPackageInfo.RESULT_TYPE_UN_LUCKY :

                                                Toast.makeText(context, receivedRedPackageInfo.getMessage(), Toast.LENGTH_SHORT).show();
                                                MyDialogs.showGrabRedPacketResult(context);
                                                break;
                                            case ReceivedRedPackageInfo.RESULT_TYPE_TAKED :

                                                openRedPackageDetail(chatMsgItemInfo.getRedPackageId());
                                                break;
                                        }


                                    } else {
                                        ToastUtil.showToast(context,receivedRedPackageInfo.getResultMsg(),Toast.LENGTH_SHORT);
                                    }
                                    baseActivity.hideLoading();
                                }
                            }, new Consumer<Throwable>() {
                                @Override
                                public void accept(Throwable throwable) throws Exception {

                                    Log.d("aaaa", throwable.getMessage());
                                    throwable.printStackTrace();

                                    baseActivity.hideLoading();
                                    ToastUtil.showToast(context,"拆开红包失败",Toast.LENGTH_SHORT);
                                }
                            }, new Action() {
                                @Override
                                public void run() throws Exception {
                                }
                            });

                            chatMsgItemInfo.setRedPackageUsed(true);

                            ChatAdapter.this.notifyDataSetChanged();
                        }
                    });
                }

            } else {
                holder.leftRedPackageLayout.setVisibility(View.GONE);
                holder.leftMessageLayout.setVisibility(View.VISIBLE);

                holder.leftMsgTv.setText(chatMsgItemInfo.getMsg());
            }

            if (!TextUtils.isEmpty(chatMsgItemInfo.getIcon())) {
                Picasso.with(this.context)
                        .load(chatMsgItemInfo.getIcon())
                        .into(holder.leftIconIv);
            } else {
                holder.leftIconIv.setImageResource(R.mipmap.default_user_icon);
            }

        } else {

            holder.leftView.setVisibility(View.GONE);
            holder.rightView.setVisibility(View.VISIBLE);

            if (chatMsgItemInfo.isRedPackage()){

                holder.rightRedPackageLayout.setVisibility(View.VISIBLE);
                holder.rightMessageLayout.setVisibility(View.GONE);

                holder.rightRedPackageImage.setTag(R.id.red_package_id, chatMsgItemInfo.getRedPackageId());

                if (chatMsgItemInfo.isRedPackageUsed()){
                    holder.rightRedPackageImage.setImageResource(R.mipmap.red_package_png_used);
                } else {
                    holder.rightRedPackageImage.setImageResource(R.mipmap.red_package_png);
                }

            } else {
                holder.rightRedPackageLayout.setVisibility(View.GONE);
                holder.rightMessageLayout.setVisibility(View.VISIBLE);

                holder.rightMsgTv.setText(chatMsgItemInfo.getMsg());
            }


            if (!TextUtils.isEmpty(chatMsgItemInfo.getIcon())) {
                Picasso.with(this.context)
                        .load(chatMsgItemInfo.getIcon())
                        .into(holder.rightIconIv);
            } else {
                holder.rightIconIv.setImageResource(R.mipmap.default_user_icon);
            }
        }




        return convertView;
    }

    private void openRedPackageDetail(String redPackageId){

        Intent intent = new Intent(context, OpenedRedPackageActivity.class);
        intent.putExtra("redPackageId", redPackageId);
        context.startActivity(intent);
    }

    static class ViewHolder {
        TextView timeTv;
        ImageView leftIconIv;
        ImageView rightIconIv;
        TextView leftMsgTv;
        TextView  rightMsgTv;
        View leftView;
        View rightView;

        RelativeLayout leftRedPackageLayout;
        RelativeLayout leftMessageLayout;
        RelativeLayout rightRedPackageLayout;
        RelativeLayout rightMessageLayout;

        ImageView leftRedPackageImage;
        ImageView rightRedPackageImage;
    }
}
