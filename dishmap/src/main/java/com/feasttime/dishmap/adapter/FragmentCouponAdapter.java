package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.CouponChildListItemInfo;
import com.feasttime.dishmap.model.bean.CouponListItemInfo;

import java.util.ArrayList;

/**
 * Created by chen on 2018/1/12.
 */

public class FragmentCouponAdapter extends BaseExpandableListAdapter {

    private ArrayList<CouponListItemInfo> datasList;

    private Context context;

    public FragmentCouponAdapter(Context context,ArrayList<CouponListItemInfo> datasList) {
        this.datasList = datasList;
        this.context = context;
    }

    @Override
    public int getGroupCount() {
        return datasList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return datasList.get(groupPosition).getChildListItemInfos().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return datasList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return datasList.get(groupPosition).getChildListItemInfos().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getGroupView(final int groupPosition, final boolean isExpanded, View convertView, final ViewGroup parent) {
        final GroupViewHolder groupViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_user_coupon_group_item, parent, false);
            groupViewHolder = new GroupViewHolder();
            groupViewHolder.nameTv = (TextView) convertView.findViewById(R.id.fragment_user_coupon_group_item_name_tv);
            groupViewHolder.arrowIv = (ImageView)convertView.findViewById(R.id.fragment_user_coupon_group_item_arrow_iv);
            convertView.setTag(groupViewHolder);
        } else {
            groupViewHolder = (GroupViewHolder) convertView.getTag();
        }
        groupViewHolder.nameTv.setText(datasList.get(groupPosition).getName());

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isExpanded) {
                    groupViewHolder.arrowIv.setImageResource(R.mipmap.expand_listview_down_arrow);
                    ((ExpandableListView)parent).collapseGroup(groupPosition);
                } else {
                    groupViewHolder.arrowIv.setImageResource(R.mipmap.expand_listview_up_arrow);
                    ((ExpandableListView)parent).expandGroup(groupPosition);
                }
            }
        });


        if (isExpanded) {
            groupViewHolder.arrowIv.setImageResource(R.mipmap.expand_listview_up_arrow);
        } else {
            groupViewHolder.arrowIv.setImageResource(R.mipmap.expand_listview_down_arrow);
        }

        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_user_coupon_child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.couponNameTv = (TextView) convertView.findViewById(R.id.fragment_user_coupon_child_item_coupon_name_tv);
            childViewHolder.couponPriceTv = (TextView)convertView.findViewById(R.id.fragment_user_coupon_child_item_price_tv);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        CouponChildListItemInfo couponChildListItemInfo = datasList.get(groupPosition).getChildListItemInfos().get(childPosition);
        childViewHolder.couponNameTv.setText(couponChildListItemInfo.getCouponTitle());
        childViewHolder.couponPriceTv.setText(couponChildListItemInfo.getCouponType());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }


    static class GroupViewHolder {
        TextView nameTv;
        ImageView arrowIv;
    }

    static class ChildViewHolder {
        TextView couponNameTv;
        TextView couponPriceTv;
    }
}
