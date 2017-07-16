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
import com.feasttime.model.bean.MyOrderListItemInfo;

import java.util.List;

/**
 * Created by chen on 2017/5/11.
 */

public class MyOrderAdapter extends RecyclerView.Adapter<MyOrderAdapter.MyViewHolder>{
    private List<MyOrderListItemInfo> datas;
    private Context context;
    private OrderModifyListener orderModifyListener;

    public MyOrderAdapter(List<MyOrderListItemInfo> datas, Activity activity){
        context = activity;
        this.datas = datas;
    }

    public void setOrderModifyListener(OrderModifyListener orderModifyListener) {
        this.orderModifyListener = orderModifyListener;
    }

    public void refreshList(List<MyOrderListItemInfo> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.my_order_list_item, parent,
                false));
        holder.orderModifyListener = this.orderModifyListener;
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        MyOrderListItemInfo myOrderListItemInfo = datas.get(position);
        holder.nameTv.setText(myOrderListItemInfo.getDishName());
        holder.addIv.setTag(myOrderListItemInfo.getDishID());
        holder.reduceIv.setTag(myOrderListItemInfo.getDishID());
        holder.amountTv.setText(myOrderListItemInfo.getAmount());
        holder.priceTv.setText(myOrderListItemInfo.getPrice());
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private OrderModifyListener orderModifyListener;
        public TextView nameTv;
        public ImageView addIv;
        public ImageView reduceIv;
        public TextView amountTv;
        public TextView priceTv;
        public MyViewHolder(View view)
        {
            super(view);
            nameTv = (TextView)view.findViewById(R.id.my_order_list_item_name_tv);
            reduceIv = (ImageView)view.findViewById(R.id.my_order_list_item_reduce_iv);
            addIv = (ImageView)view.findViewById(R.id.my_order_list_item_add_iv);
            amountTv = (TextView)view.findViewById(R.id.my_order_list_item_dishes_count_tv);
            priceTv = (TextView)view.findViewById(R.id.my_order_list_item_price_tv);
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
