package com.feasttime.dishmap.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.feasttime.dishmap.R;

import java.util.List;


public class CommentListViewAdapter extends BaseAdapter {
    private List<String> data;
    private LayoutInflater mInflater;
    public CommentListViewAdapter(Context context, List<String> data) {
        this.data = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.activity_comment_listview_item, null);
            holder = new ViewHolder();
            holder.name = (TextView)convertView.findViewById(R.id.activity_comment_listview_item_name_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }
        holder.name.setText(data.get(position));
        return convertView;
    }


    public static class ViewHolder {
        public TextView name;
    }
}
