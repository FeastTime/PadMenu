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
import com.feasttime.dishmap.model.bean.MessageItemInfo;

import java.util.List;

import cn.nekocode.badge.BadgeDrawable;

/**
 * Created by chen on 2018/1/19.
 */

public class MessageAdapter extends BaseAdapter {

    private Context context;
    private List<MessageItemInfo> dataList;
    private LayoutInflater mLayoutInflater;

    public MessageAdapter(Context context,List<MessageItemInfo> dataList) {
        this.dataList = dataList;
        this.context = context;
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
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_message_lv_item, null);
            holder = new ViewHolder();
            holder.nameTv = (TextView)convertView.findViewById(R.id.activity_message_lv_item_name_tv);
            holder.badgeIv = (ImageView)convertView.findViewById(R.id.activity_message_lv_item_badge_iv);
            holder.timeTv = (TextView)convertView.findViewById(R.id.activity_message_lv_item_time_tv);
            holder.descTv = (TextView)convertView.findViewById(R.id.activity_message_lv_item_desc_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        MessageItemInfo messageItemInfo = dataList.get(position);

        int msgCount = messageItemInfo.getMsgCount();

        if (msgCount > 0 ) {
            holder.badgeIv.setVisibility(View.VISIBLE);
            final BadgeDrawable badgeBd =
                    new BadgeDrawable.Builder()
                            .type(BadgeDrawable.TYPE_NUMBER)
                            .badgeColor(Color.parseColor("#E7001E"))
                            .number(msgCount)
                            .build();
            holder.badgeIv.setImageDrawable(badgeBd);
        } else {
            holder.badgeIv.setVisibility(View.GONE);
        }


        holder.nameTv.setText(messageItemInfo.getName());

        holder.descTv.setText(messageItemInfo.getMessage());
        holder.timeTv.setText(messageItemInfo.getTime());
        return convertView;
    }


    static class ViewHolder {
        TextView nameTv;
        TextView descTv;
        ImageView badgeIv;
        TextView timeTv;
    }
}
