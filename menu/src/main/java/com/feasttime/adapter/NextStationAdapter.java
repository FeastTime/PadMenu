package com.feasttime.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.feasttime.menu.R;

import java.util.List;

/**
 * Created by chen on 2017/5/8.
 */

public class NextStationAdapter extends  RecyclerView.Adapter<NextStationAdapter.MyViewHolder>{

    private List<Integer> datas;
    private Context context;
    private RecommendViewHolderClicks mRecommendViewHolderClicks;

    public NextStationAdapter(List<Integer> datas, Activity activity){
        context = activity;
        this.datas = datas;
    }

    public void setItemListener(RecommendViewHolderClicks recommendViewHolderClicks) {
        mRecommendViewHolderClicks = recommendViewHolderClicks;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        MyViewHolder holder = new MyViewHolder(LayoutInflater.from(context).inflate(R.layout.recommend_activity_list_menu_item, parent, false));
        holder.mRecommendViewHolderClicks = mRecommendViewHolderClicks;
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position)
    {
        //holder.tv.setText(datas.get(position));
    }

    @Override
    public int getItemCount()
    {
        return datas.size();
    }

    class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        RecommendViewHolderClicks mRecommendViewHolderClicks;

        ImageView addIv;

        ImageView reduceIv;
        public MyViewHolder(View view)
        {
            super(view);
            addIv = (ImageView)view.findViewById(R.id.recommend_activity_list_menu_item_add_iv);
            reduceIv = (ImageView)view.findViewById(R.id.recommend_activity_list_menu_item_reduce_iv);

            addIv.setOnClickListener(this);
            reduceIv.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (v == addIv) {
                mRecommendViewHolderClicks.onAddClicked("");
            } else if (v == reduceIv) {
                mRecommendViewHolderClicks.onReduceClicked("");
            }
        }
    }

    public interface RecommendViewHolderClicks {
        //单击整个item跳转到用户界面，需要传递uid
        public void onAddClicked(String uid);
        public void onReduceClicked(String uid);
    }
}
