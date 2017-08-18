package com.feasttime.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.listener.OrderModifyListener;
import com.feasttime.menu.R;
import com.feasttime.model.bean.RecommendOrderListItemInfo;

import java.util.List;

/**
 * Created by chen on 2017/5/11.
 */
public class RecommendOrderAdapter extends RecyclerView.Adapter<RecommendOrderAdapter.MyViewHolder> {
    private List<RecommendOrderListItemInfo> datas;
    private Context context;
    private OrderModifyListener orderModifyListener;

    public void setListener(OrderModifyListener listener) {
        orderModifyListener = listener;
    }

    public RecommendOrderAdapter(List<RecommendOrderListItemInfo> datas, Activity activity) {
        context = activity;
        this.datas = datas;
    }

    public void refreshList(List<RecommendOrderListItemInfo> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.recommend_order_list_item, parent,
                false));
        holder.orderModifyListener = this.orderModifyListener;
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        RecommendOrderListItemInfo recommendOrderListItemInfo = datas.get(position);
        holder.nameTv.setText(recommendOrderListItemInfo.getDishName());
        holder.addIv.setTag(recommendOrderListItemInfo.getDishID());
        holder.reduceIv.setTag(recommendOrderListItemInfo.getDishID());
        holder.priceTv.setText(recommendOrderListItemInfo.getTodayPrice());
    }

    @Override
    public int getItemCount() {
        return datas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView addIv;
        public TextView nameTv;
        public ImageView reduceIv;
        public TextView priceTv;
        public OrderModifyListener orderModifyListener;

        public MyViewHolder(View view) {
            super(view);
            nameTv = (TextView) view.findViewById(R.id.recommend_order_list_item_name_tv);
            addIv = (ImageView) view.findViewById(R.id.add_order_num_iv);
            reduceIv = (ImageView) view.findViewById(R.id.reduce_order_num_iv);
            priceTv = (TextView)view.findViewById(R.id.recommend_order_list_item_specal_price_tv);
            addIv.setOnClickListener(this);
            reduceIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == addIv) {
                orderModifyListener.onAddClicked(v.getTag().toString());
            } else if (v == reduceIv) {
                orderModifyListener.onReduceClicked(v.getTag().toString());
            }
        }
    }
}