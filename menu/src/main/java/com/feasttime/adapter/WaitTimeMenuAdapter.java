/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.feasttime.menu.R;
import com.feasttime.model.bean.WaitTimeMenuItemInfo;

import java.util.List;

import static android.view.View.inflate;

public class WaitTimeMenuAdapter extends BaseAdapter {

    private Context context = null;
    private List<WaitTimeMenuItemInfo> list;

    public WaitTimeMenuAdapter(Context context, List<WaitTimeMenuItemInfo> list) {

        this.context = context;
        this.list = list;
    }

    public void updateData(List<WaitTimeMenuItemInfo> list){
        this.list = list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null){

            convertView = inflate(context, R.layout.wait_time_menu_item, null);
            holder = new ViewHolder();

            holder.dishName = (TextView)convertView.findViewById(R.id.dish_name);
            holder.isCooking = (ImageView)convertView.findViewById(R.id.is_cooking);
            holder.waitTimeText = (TextView)convertView.findViewById(R.id.waittime_text);
            holder.waitTimeProgressBar = (ProgressBar)convertView.findViewById(R.id.waittime_progress_bar);

            convertView.setTag(holder);

        } else
            holder = (ViewHolder) convertView.getTag();


        WaitTimeMenuItemInfo waitTimeMenuItemInfo = list.get(position);

        holder.dishName.setText(waitTimeMenuItemInfo.getDishName());


        // 等待时间
        long remainingTime = waitTimeMenuItemInfo.getNeedTime() - (System.currentTimeMillis() - waitTimeMenuItemInfo.getStartTime());

        if (remainingTime < 0){
            remainingTime = 0;
        }
        // 设置等待提示
        holder.waitTimeText.setText("剩余" + remainingTime/1000/60 + "分钟" + remainingTime%(60*1000)/1000 + "秒");

        // 设置进度条
        int  remainingProcess = (int)(100 * remainingTime/waitTimeMenuItemInfo.getNeedTime());
        holder.waitTimeProgressBar.setProgress(100 - remainingProcess);

        // 设置状态图标
        if (waitTimeMenuItemInfo.getState()==2 || remainingTime < 1000)
            holder.isCooking.setImageResource(R.mipmap.waittime_cooking);
        else
            holder.isCooking.setImageResource(R.mipmap.waittime_uncooking);

        return convertView;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /*存放控件 的ViewHolder*/
    private final class ViewHolder {

        TextView dishName;
        ImageView isCooking;
        TextView waitTimeText;
        ProgressBar waitTimeProgressBar;
    }

}
