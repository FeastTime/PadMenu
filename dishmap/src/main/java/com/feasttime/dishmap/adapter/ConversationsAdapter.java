package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.ChatActivity;
import com.feasttime.dishmap.model.bean.MessageItemInfo;
import com.feasttime.dishmap.model.bean.StoreItemInfo;
import com.feasttime.dishmap.utils.CircleImageTransformation;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;

import cn.nekocode.badge.BadgeDrawable;

/**
 *
 * Created by YOLANDA on 2016/7/22.
 */
public class ConversationsAdapter extends RecyclerViewBaseAdapter<ConversationsAdapter.ViewHolder> {

    private List<MessageItemInfo> mDataList;

    private HashMap<String,StoreItemInfo> storesItemInfoMap = new HashMap<String,StoreItemInfo>();

    private Context context;

    public ConversationsAdapter(Context context) {
        super(context);
        this.context = context;
    }

    public void clearAllData() {
        if (this.mDataList != null) {
            this.mDataList.clear();
            this.notifyDataSetChanged();
        }
    }

    public void notifyDataSetChanged(List<MessageItemInfo> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
    }

    public void setStoresInfoMap(HashMap<String,StoreItemInfo> storesItemInfoMap) {
        this.storesItemInfoMap = storesItemInfoMap;
    }

    public MessageItemInfo getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View contentView = getInflater().inflate(R.layout.activity_message_lv_item, parent, false);
        return new ViewHolder(contentView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageItemInfo messageItemInfo = mDataList.get(position);
        StoreItemInfo storeItemInfo = storesItemInfoMap.get(messageItemInfo.getStoreId());

        holder.initData(messageItemInfo,storeItemInfo);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView timeTv;
        TextView lastMsgTv;
        ImageView badgeIv;
        ImageView iconIv;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            nameTv = (TextView) itemView.findViewById(R.id.activity_message_lv_item_name_tv);
            timeTv = (TextView) itemView.findViewById(R.id.activity_message_lv_item_time_tv);
            lastMsgTv = (TextView) itemView.findViewById(R.id.activity_message_lv_item_desc_tv);
            badgeIv = (ImageView) itemView.findViewById(R.id.activity_message_lv_item_badge_iv);
            iconIv = (ImageView) itemView.findViewById(R.id.activity_message_lv_item_icon_iv);
        }

        private void initData(final MessageItemInfo messageItemInfo,final StoreItemInfo storeItemInfo) {

            if (storeItemInfo != null)
                this.nameTv.setText(storeItemInfo.getStoreName());

            this.lastMsgTv.setText(messageItemInfo.getMessage());
            this.timeTv.setText(messageItemInfo.getTime());

            String storeIcon = null;
            if (storeItemInfo != null) {
                storeIcon = storeItemInfo.getStoreIcon();
            }

            if (!TextUtils.isEmpty(storeIcon)) {
                Picasso.with(itemView.getContext())
                        .load(storeIcon)
                        .transform(new CircleImageTransformation())
                        .into(this.iconIv);
            } else {
                this.iconIv.setImageResource(R.mipmap.default_user_icon);
            }

            int msgCount = messageItemInfo.getMsgCount();

            if (msgCount > 0 ) {
                this.badgeIv.setVisibility(View.VISIBLE);
                final BadgeDrawable badgeBd =
                        new BadgeDrawable.Builder()
                                .type(BadgeDrawable.TYPE_NUMBER)
                                .badgeColor(Color.parseColor("#E7001E"))
                                .number(msgCount)
                                .build();
                this.badgeIv.setImageDrawable(badgeBd);
            } else {
                this.badgeIv.setVisibility(View.GONE);
            }

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到聊天页面
                    Context context = v.getContext();
                    Intent intent = new Intent(context, ChatActivity.class);

                    if (null != storeItemInfo){

                        intent.putExtra("STORE_ID", storeItemInfo.getStoreId());
                        intent.putExtra("STORE_NAME",storeItemInfo.getStoreName());

                    } else {

                        intent.putExtra("STORE_ID", messageItemInfo.getStoreId());
//                        intent.putExtra("STORE_NAME",.getStoreName());
                    }

                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
