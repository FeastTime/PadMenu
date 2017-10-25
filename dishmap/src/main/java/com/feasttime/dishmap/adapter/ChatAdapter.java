package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/10/25.
 */

public class ChatAdapter extends BaseAdapter {

    private List<ChatMsgItemInfo> dataList = new ArrayList<ChatMsgItemInfo>();

    private LayoutInflater mLayoutInflater;

    public ChatAdapter(Context context,List<ChatMsgItemInfo> datas) {
        dataList = datas;
        mLayoutInflater = LayoutInflater.from(context);
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
            holder.timeTv = (TextView) convertView.findViewById(R.id.activity_chat_listview_item_layout_time_tv);
            holder.leftView = convertView.findViewById(R.id.activity_chat_listview_item_layout_left_ll);
            holder.rightView = convertView.findViewById(R.id.activity_chat_listview_item_layout_right_ll);

            holder.leftIconIv = (ImageView) convertView.findViewById(R.id.activity_chat_listview_item_layout_left_icon_iv);
            holder.leftMsgTv = (TextView) convertView.findViewById(R.id.activity_chat_listview_item_layout_left_msg_tv);

            holder.rightIconIv = (ImageView) convertView.findViewById(R.id.activity_chat_listview_item_layout_right_icon_iv);
            holder.rightMsgTv = (TextView) convertView.findViewById(R.id.activity_chat_listview_item_layout_right_msg_tv);


            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ChatMsgItemInfo chatMsgItemInfo = dataList.get(position);
        if (chatMsgItemInfo.isLeft()) {
            holder.rightView.setVisibility(View.GONE);
            holder.leftView.setVisibility(View.VISIBLE);

            holder.leftIconIv.setImageResource(chatMsgItemInfo.getIcon());
            holder.leftMsgTv.setText(chatMsgItemInfo.getMsg());
            holder.leftMsgTv.setTextColor(Color.WHITE);
        } else {
            holder.leftView.setVisibility(View.GONE);
            holder.rightView.setVisibility(View.VISIBLE);

            holder.rightIconIv.setImageResource(chatMsgItemInfo.getIcon());
            holder.rightMsgTv.setText(chatMsgItemInfo.getMsg());
            holder.rightMsgTv.setTextColor(Color.parseColor("#666666"));
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
    }
}
