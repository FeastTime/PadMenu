package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.HadEatedStoreItemInfo;
import com.feasttime.dishmap.model.bean.HistoryTableListItemInfo;

import java.util.ArrayList;

/**
 * Created by chen on 2018/1/15.
 */

public class HadEatedStoreLvAdapter extends BaseAdapter {

    private ArrayList<HadEatedStoreItemInfo> datasList;
    private Context context;
    private LayoutInflater mLayoutInflater;

    public HadEatedStoreLvAdapter(Context context,ArrayList<HadEatedStoreItemInfo> datasList) {
        this.context = context;
        this.datasList = datasList;
        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return datasList.size();
    }

    @Override
    public Object getItem(int position) {
        return datasList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = mLayoutInflater.inflate(R.layout.activity_had_eated_store_lv_item, parent, false);
            holder = new ViewHolder();
            holder.nameTv = (TextView)convertView.findViewById(R.id.activity_had_eated_store_lv_item_name_tv);
            holder.descTv = (TextView)convertView.findViewById(R.id.activity_had_eated_store_lv_item_desc_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        HadEatedStoreItemInfo hadEatedStoreItemInfo = datasList.get(position);
        holder.nameTv.setText(hadEatedStoreItemInfo.getName());
        holder.descTv.setText(hadEatedStoreItemInfo.getDescription());


        return convertView;
    }

    static class ViewHolder {
        TextView nameTv;
        TextView descTv;
    }
}
