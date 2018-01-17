package com.feasttime.dishmap.adapter;

/**
 * Created by chen on 2018/1/15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.PayMethodItemInfo;

import java.util.ArrayList;

public class PayMethodAdapter extends BaseAdapter {
    private ArrayList<PayMethodItemInfo> datasList;
    private Context mContext;
    private LayoutInflater mInflater;
    Bitmap iconBitmap;
    private int selectIndex = -1;

    public PayMethodAdapter(Context context, ArrayList<PayMethodItemInfo> datasList) {
        this.mContext = context;
        this.datasList = datasList;
        mInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return datasList.size();
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
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.activity_pay_lv_item, null);
            holder.tablePeoplesTv = (TextView) convertView.findViewById(R.id.activity_pay_lv_item_table_people_nums_tv);
            holder.remainTv = (TextView) convertView.findViewById(R.id.activity_pay_lv_item_remain_tv);
            holder.moneyTv = (TextView)convertView.findViewById(R.id.activity_pay_lv_item_money_tv);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == selectIndex) {
            convertView.setSelected(true);
        } else {
            convertView.setSelected(false);
        }

        PayMethodItemInfo  payMethodItemInfo = datasList.get(position);
        holder.moneyTv.setText(payMethodItemInfo.getMoney());
        holder.remainTv.setText(payMethodItemInfo.getRemain());
        holder.tablePeoplesTv.setText(payMethodItemInfo.getTablePeopleNums());
        return convertView;
    }

    private static class ViewHolder {
        private TextView tablePeoplesTv;
        private TextView remainTv;
        private TextView moneyTv;
    }

//    private Bitmap getPropThumnail(int id) {
//        Drawable d = mContext.getResources().getDrawable(id);
//        Bitmap b = BitmapUtil.drawableToBitmap(d);
////      Bitmap bb = BitmapUtil.getRoundedCornerBitmap(b, 100);
//        int w = mContext.getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width);
//        int h = mContext.getResources().getDimensionPixelSize(R.dimen.thumnail_default_height);
//
//        Bitmap thumBitmap = ThumbnailUtils.extractThumbnail(b, w, h);
//        return thumBitmap;
//    }

    public void setSelectIndex(int i) {
        selectIndex = i;
    }
}