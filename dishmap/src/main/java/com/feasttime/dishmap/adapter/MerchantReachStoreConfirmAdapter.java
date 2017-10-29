package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.ChatMsgItemInfo;
import com.feasttime.dishmap.model.bean.ReachStoreConfirmItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/10/29.
 */

public class MerchantReachStoreConfirmAdapter extends BaseAdapter {
    private List<ReachStoreConfirmItemInfo> dataList = new ArrayList<ReachStoreConfirmItemInfo>();

    private LayoutInflater mLayoutInflater;

    public MerchantReachStoreConfirmAdapter(Context context, List<ReachStoreConfirmItemInfo> datas) {
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
            convertView = mLayoutInflater.inflate(R.layout.fragment_merchant_reach_store_confirm_lv_item,
                    parent, false);
            holder = new ViewHolder();
            holder.nameTv = (TextView)convertView.findViewById(R.id.fragment_merchant_reach_store_confirm_lv_item_name_tv);
            holder.typeTv = (TextView)convertView.findViewById(R.id.fragment_merchant_reach_store_confirm_lv_item_type_tv);
            holder.numberTv = (TextView)convertView.findViewById(R.id.fragment_merchant_reach_store_confirm_lv_item_number_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }


        ReachStoreConfirmItemInfo reachStoreConfirmItemInfo = dataList.get(position);

        holder.nameTv.setText(reachStoreConfirmItemInfo.getName());
        holder.numberTv.setText("号码:" + reachStoreConfirmItemInfo.getNumber());
        holder.typeTv.setText("类型:" + reachStoreConfirmItemInfo.getType());

        return convertView;
    }


    static class ViewHolder {
        ImageView iconIv;
        TextView numberTv;
        TextView typeTv;
        TextView timeTv;
        TextView nameTv;
        Button cancelTv;
        Button  delayTv;
        Button reachStoreTv;
    }
}
