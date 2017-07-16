package com.feasttime.adapter;


import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.feasttime.menu.R;
import com.ipinyou.ads.OnlyShowView;

import java.util.List;

public class WaitTimeAdAdapter extends BaseAdapter{

    private Context context = null;
    private List<String> list;
    private int height;

    public WaitTimeAdAdapter(Context context, List<String> list, int height){

        this.context = context;
        this.list = list;
        this.height = height;
    }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ViewHolder holder;

            if (convertView == null) {
                convertView = View.inflate(context, R.layout.wait_time_ad_item, null);

                holder = new ViewHolder();
                holder.adView = (OnlyShowView)convertView.findViewById(R.id.adView);
                convertView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));

                convertView.setTag(holder);

            } else {

                holder = (ViewHolder) convertView.getTag();
            }

            holder.adView.loadAD(list.get(position));

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

    private final class ViewHolder {

        OnlyShowView adView;
    }

}
