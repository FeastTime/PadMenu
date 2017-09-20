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

public class OutDishesAdapter extends RecyclerView.Adapter<OutDishesAdapter.MyViewHolder>{
    private List<MyOrderListItemInfo> datas;
    private Context context;
    private OrderModifyListener orderModifyListener;

    public OutDishesAdapter(List<MyOrderListItemInfo> datas, Activity activity){
        context = activity;
        this.datas = datas;
    }

    public void refreshList(List<MyOrderListItemInfo> datas) {
        this.datas = datas;
        this.notifyDataSetChanged();
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(
                context).inflate(R.layout.out_dishes_list_item, parent,
                false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        MyOrderListItemInfo myOrderListItemInfo = datas.get(position);
        holder.nameTv.setText(myOrderListItemInfo.getDishName());
        if (position % 2 == 0) {
            holder.dishesIconIv.setImageResource(R.mipmap.temp_icon1);
        } else {
            holder.dishesIconIv.setImageResource(R.mipmap.temp_dishes_2);
        }
        holder.flagTv.setText("双人份");
        holder.saleTv.setText("大热销");
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        public TextView nameTv;
        public ImageView addIv;
        public ImageView reduceIv;
        public ImageView dishesIconIv;
        public TextView flagTv;
        public TextView saleTv;
        public TextView numTv;
        public MyViewHolder(View view)
        {
            super(view);
            nameTv = (TextView)view.findViewById(R.id.out_dishes_list_item_dishes_name_tv);
            reduceIv = (ImageView)view.findViewById(R.id.big_modify_order_reduce_order_iv);
            addIv = (ImageView)view.findViewById(R.id.big_modify_order_add_order_iv);
            dishesIconIv = (ImageView)view.findViewById(R.id.out_dishes_list_item_dishes_icon_iv);
            flagTv = (TextView)view.findViewById(R.id.out_dishes_list_item_dishes_flag_tv);
            saleTv = (TextView)view.findViewById(R.id.out_dishes_list_item_sale_tv);
            numTv = (TextView)view.findViewById(R.id.big_modify_order_reduce_order_num_tv);
            addIv.setOnClickListener(this);
            reduceIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == addIv) {
            } else if (v == reduceIv) {
            }
        }
    }
}
