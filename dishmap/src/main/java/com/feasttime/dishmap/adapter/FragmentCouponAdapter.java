package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.CouponDetailActivity;
import com.feasttime.dishmap.model.bean.CouponChildListItemInfo;
import com.feasttime.dishmap.model.bean.CouponListItemInfo;
import com.feasttime.dishmap.utils.UtilTools;

import java.util.ArrayList;

/**
 *
 * Created by chen on 2018/1/12.
 */

public class FragmentCouponAdapter extends BaseExpandableListAdapter {

    private ArrayList<CouponListItemInfo> dataList;
    private Context context;
    private Resources resources;
    public int expandGroupIndex = -1;

    public FragmentCouponAdapter(Context context,ArrayList<CouponListItemInfo> dataList) {
        this.dataList = dataList;
        this.context = context;
        resources = context.getResources();
    }

    public void initExpandGroupIndex() {
        expandGroupIndex = -1;
    }

    public void resetAllData(ArrayList<CouponListItemInfo> dataList) {
        this.dataList = dataList;
        this.notifyDataSetChanged();
    }

    public int getExpandGroupIndex() {
        return expandGroupIndex;
    }

    @Override
    public int getGroupCount() {
        return dataList.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return dataList.get(groupPosition).getDataList().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return dataList.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return dataList.get(groupPosition).getDataList().get(childPosition);
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
        groupViewHolder.nameTv.setText(dataList.get(groupPosition).getStoreName());

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
            expandGroupIndex = groupPosition;
            groupViewHolder.arrowIv.setImageResource(R.mipmap.expand_listview_up_arrow);
        } else {
            groupViewHolder.arrowIv.setImageResource(R.mipmap.expand_listview_down_arrow);
        }

        return convertView;
    }

    @Override
    public View getChildView(final int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {

        ChildViewHolder childViewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.fragment_user_coupon_child_item, parent, false);
            childViewHolder = new ChildViewHolder();
            childViewHolder.couponNameTv = (TextView) convertView.findViewById(R.id.fragment_user_coupon_child_item_coupon_name_tv);
            childViewHolder.couponPriceTv = (TextView)convertView.findViewById(R.id.fragment_user_coupon_child_item_price_tv);
            childViewHolder.couponTitleTv = (TextView)convertView.findViewById(R.id.fragment_user_coupon_child_item_coupon_title_tv);
            childViewHolder.expireTv = (TextView)convertView.findViewById(R.id.fragment_user_coupon_child_item_expire_tv);
            childViewHolder.noUsedIconIv = (ImageView)convertView.findViewById(R.id.fragment_user_coupon_child_item_flag_icon_Iv);
            convertView.setTag(childViewHolder);
        } else {
            childViewHolder = (ChildViewHolder) convertView.getTag();
        }
        final CouponChildListItemInfo couponChildListItemInfo = dataList.get(groupPosition).getDataList().get(childPosition);

        if (couponChildListItemInfo.getIsUse() == 1) {
            //未使用
            childViewHolder.noUsedIconIv.setVisibility(View.GONE);

            childViewHolder.couponNameTv.setTextColor(resources.getColor(R.color.orange_color));
            childViewHolder.couponPriceTv.setTextColor(resources.getColor(R.color.orange_color));
            childViewHolder.couponTitleTv.setTextColor(resources.getColor(R.color.text_dark_gray));
            childViewHolder.expireTv.setTextColor(resources.getColor(R.color.text_gray_design_1));

        } else {
            //已使用
            childViewHolder.noUsedIconIv.setVisibility(View.VISIBLE);

            childViewHolder.couponNameTv.setTextColor(resources.getColor(R.color.text_dark_gray));
            childViewHolder.couponPriceTv.setTextColor(resources.getColor(R.color.text_dark_gray));
            childViewHolder.couponTitleTv.setTextColor(resources.getColor(R.color.text_dark_gray));
            childViewHolder.expireTv.setTextColor(resources.getColor(R.color.text_dark_gray));
        }


        String couponType = couponChildListItemInfo.getCouponType();
        if (!TextUtils.isEmpty(couponType)) {
            childViewHolder.couponNameTv.setText(UtilTools.getCouponStrByType(Integer.parseInt(couponType)));
        }

        childViewHolder.couponPriceTv.setText(couponChildListItemInfo.getCouponTitle());
        childViewHolder.couponTitleTv.setText(couponChildListItemInfo.getCouponTitle());

        childViewHolder.expireTv.setText("距离现在仅剩" + UtilTools.getDaysFromOtherDate(couponChildListItemInfo.getCouponValidity()) + "天");

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CouponListItemInfo couponListItemInfo = dataList.get(groupPosition);

                Intent intent = new Intent(v.getContext(), CouponDetailActivity.class);
                intent.putExtra("couponData",couponChildListItemInfo);

                v.getContext().startActivity(intent);
            }
        });

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
        TextView couponTitleTv;
        TextView expireTv;
        ImageView noUsedIconIv;
    }
}
