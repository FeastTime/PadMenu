package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.graphics.Color;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.UtilTools;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by chen on 2017/10/25.
 */

public class ChatAdapter extends BaseAdapter {

    private List<ChatMsgItemInfo> dataList = new ArrayList<ChatMsgItemInfo>();

    private LayoutInflater mLayoutInflater;
    private String storeId;

    private Context context;

    public ChatAdapter(Context context,List<ChatMsgItemInfo> datas, String storeId) {
        dataList = datas;
        mLayoutInflater = LayoutInflater.from(context);
        this.context = context;
        this.storeId = storeId;
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
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChatMsgItemInfo chatMsgItemInfo = dataList.get(position);

        holder.timeTv.setText(chatMsgItemInfo.getTime());

        if (chatMsgItemInfo.isLeft()) {

            holder.rightView.setVisibility(View.GONE);
            holder.leftView.setVisibility(View.VISIBLE);

            if (chatMsgItemInfo.isRedPackage()){

                holder.leftRedPackageLayout.setVisibility(View.VISIBLE);
                holder.leftMessageLayout.setVisibility(View.GONE);

                holder.leftRedPackageImage.setTag(R.id.red_package_id, chatMsgItemInfo.getRedPackageId());

                if (null != chatMsgItemInfo.getRedPackageId()){

                    // 红包点击事件
                    holder.leftRedPackageImage.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {

                            HashMap<String, String > requestData = new HashMap<>();
                            requestData.put("redPackageId", view.getTag(R.id.red_package_id).toString());
                            requestData.put("type", WebSocketEvent.OPEN_RED_PACKAGE+"");
                            requestData.put("storeId", storeId);

                            UtilTools.requestByWebSocket(context, requestData);
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
