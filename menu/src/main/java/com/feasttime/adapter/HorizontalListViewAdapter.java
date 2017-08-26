package com.feasttime.adapter;

/**
 * Created by Administrator on 2017/7/23.
 */


import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.menu.R;

public class HorizontalListViewAdapter extends BaseAdapter{
    private String[] mTitles;
    private Context mContext;
    private LayoutInflater mInflater;

    public HorizontalListViewAdapter(Context context, String[] titles){
        this.mContext = context;
        this.mTitles = titles;
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return mTitles.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.main_menu_recommend_listview_item, null);
//            holder.mImage=(ImageView)convertView.findViewById(R.id.img_list_item);
//            holder.mTitle=(TextView)convertView.findViewById(R.id.text_list_item);
            holder.priceTv = (TextView)convertView.findViewById(R.id.main_menu_recommend_listview_item_price_tv);
            holder.originlPriceTv = (TextView)convertView.findViewById(R.id.main_menu_recommend_listview_item_original_price_tv);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }

        holder.originlPriceTv.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        return convertView;
    }

    private static class ViewHolder {
        private TextView mTitle ;
        private ImageView mImage;
        private TextView priceTv;
        private TextView originlPriceTv;
    }
}