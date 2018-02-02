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
import com.feasttime.dishmap.model.bean.MyTableItemInfo;

import java.util.List;

import cn.nekocode.badge.BadgeDrawable;

/**
 * Created by chen on 2018/1/26.
 */

public class MySeatAdapter extends BaseAdapter {
    private List<MyTableItemInfo> dataList;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public MySeatAdapter(Context context,List<MyTableItemInfo> dataList) {
        mLayoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;
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
            convertView = mLayoutInflater.inflate(R.layout.activity_my_seat_lv_item, null);
            holder = new ViewHolder();
            holder.storeNameTv = (TextView)convertView.findViewById(R.id.activity_my_seat_lv_item_store_name_tv);
            holder.statusTv = (TextView) convertView.findViewById(R.id.activity_my_seat_lv_item_status_tv);
            holder.nickNameTv = (TextView)convertView.findViewById(R.id.activity_my_seat_lv_item_nick_name_tv);
            holder.timeTv = (TextView) convertView.findViewById(R.id.activity_my_seat_lv_item_time_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }


        MyTableItemInfo myTableItemInfo = dataList.get(position);
        holder.nickNameTv.setText(myTableItemInfo.getUserNickName());
        holder.storeNameTv.setText(myTableItemInfo.getStoreId());
        return convertView;
    }


    static class ViewHolder {
        TextView storeNameTv;
        TextView nickNameTv;
        TextView timeTv;
        TextView statusTv;
    }
}
