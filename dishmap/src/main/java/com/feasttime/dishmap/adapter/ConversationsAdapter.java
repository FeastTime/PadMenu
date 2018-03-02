/*
 * Copyright 2016 Yan Zhenjie
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.ChatActivity;
import com.feasttime.dishmap.model.bean.MessageItemInfo;

import java.util.List;

import cn.nekocode.badge.BadgeDrawable;

/**
 * Created by YOLANDA on 2016/7/22.
 */
public class ConversationsAdapter extends RecyclerViewBaseAdapter<ConversationsAdapter.ViewHolder> {

    private List<MessageItemInfo> mDataList;

    public ConversationsAdapter(Context context) {
        super(context);
    }

    public void notifyDataSetChanged(List<MessageItemInfo> dataList) {
        this.mDataList = dataList;
        super.notifyDataSetChanged();
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
        holder.initData(messageItemInfo);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;
        TextView timeTv;
        TextView lastMsgTv;
        ImageView badgeIv;
        View itemView;

        public ViewHolder(View itemView) {
            super(itemView);
            this.itemView = itemView;
            nameTv = (TextView) itemView.findViewById(R.id.activity_message_lv_item_name_tv);
            timeTv = (TextView) itemView.findViewById(R.id.activity_message_lv_item_time_tv);
            lastMsgTv = (TextView) itemView.findViewById(R.id.activity_message_lv_item_desc_tv);
            badgeIv = (ImageView) itemView.findViewById(R.id.activity_message_lv_item_badge_iv);
        }

        public void initData(final MessageItemInfo messageItemInfo) {
            this.nameTv.setText(messageItemInfo.getName());
            this.lastMsgTv.setText(messageItemInfo.getMessage());
            this.timeTv.setText(messageItemInfo.getTime());
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
                    intent.putExtra("STORE_ID", messageItemInfo.getStoreId());
                    intent.putExtra("STORE_NAME",messageItemInfo.getName());
                    v.getContext().startActivity(intent);
                }
            });
        }
    }

}
