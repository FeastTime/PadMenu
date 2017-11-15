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
import com.feasttime.dishmap.model.bean.HistoryTableListItemInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chen on 2017/10/25.
 */

public class HistoryTableListAdapter extends BaseAdapter {

    private List<HistoryTableListItemInfo> dataList = new ArrayList<HistoryTableListItemInfo>();

    private LayoutInflater mLayoutInflater;

    public HistoryTableListAdapter(Context context) {
        mLayoutInflater = LayoutInflater.from(context);
    }

    public HistoryTableListAdapter(Context context, List<HistoryTableListItemInfo> datas) {
        dataList = datas;
        mLayoutInflater = LayoutInflater.from(context);
    }

    public void addListData(List<HistoryTableListItemInfo>  historyTableListItemInfosList) {
        dataList.addAll(historyTableListItemInfosList);
    }

    public void addData(HistoryTableListItemInfo historyTableListItemInfo) {
        dataList.add(historyTableListItemInfo);
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
            convertView = mLayoutInflater.inflate(R.layout.fragment_history_table_list_item,
                    parent, false);
            holder = new ViewHolder();
            holder.nameTv = (TextView) convertView.findViewById(R.id.fragment_history_table_list_item_name_tv);
            holder.priceTv = (TextView) convertView.findViewById(R.id.fragment_history_table_list_item_price_tv);
            holder.phoneTv = (TextView) convertView.findViewById(R.id.fragment_history_table_list_item_phone_tv);
            holder.maxPersonTv = (TextView) convertView.findViewById(R.id.fragment_history_table_list_item_max_person_tv);
            holder.minPersonTv = (TextView) convertView.findViewById(R.id.fragment_history_table_list_item_min_person_tv);
            holder.descTv = (TextView) convertView.findViewById(R.id.fragment_history_table_list_item_desc_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HistoryTableListItemInfo historyTableListItemInfo = dataList.get(position);

        holder.nameTv.setText(historyTableListItemInfo.getName());
        holder.priceTv.setText(historyTableListItemInfo.getPrice());
        holder.minPersonTv.setText(historyTableListItemInfo.getMinPerson());
        holder.maxPersonTv.setText(historyTableListItemInfo.getMaxPerson());
        holder.phoneTv.setText(historyTableListItemInfo.getMobileNo());
        holder.descTv.setText(historyTableListItemInfo.getDesc());

        return convertView;
    }


    static class ViewHolder {
        TextView nameTv;
        TextView priceTv;
        TextView  phoneTv;
        TextView maxPersonTv;
        TextView minPersonTv;
        TextView  descTv;

    }
}
