package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.MyTableItemInfo;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;


/**
 *
 * Created by chen on 2018/1/26.
 */

public class MySeatAdapter extends BaseAdapter {
    private List<MyTableItemInfo> dataList;
//    private Context context;
    private LayoutInflater mLayoutInflater;
    private SimpleDateFormat simpleDateFormat;

    public MySeatAdapter(Context context,List<MyTableItemInfo> dataList) {

        mLayoutInflater = LayoutInflater.from(context);
        this.dataList = dataList;

        simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
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
        holder.storeNameTv.setText(myTableItemInfo.getStoreName());

        if (myTableItemInfo.getIsCome() == 1) {
            holder.statusTv.setText("未验证");
        } else if (myTableItemInfo.getIsCome() == 2) {
            holder.statusTv.setText("已验证");
        }

        long expireTime = myTableItemInfo.getTaketableTime() + myTableItemInfo.getRecieveTime()*60*1000;

        holder.timeTv.setText(simpleDateFormat.format(expireTime));

        return convertView;
    }


    static class ViewHolder {
        TextView storeNameTv;
        TextView nickNameTv;
        TextView timeTv;
        TextView statusTv;
    }
}
