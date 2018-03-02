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
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.MessageItemInfo;

import java.util.List;

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

    @Override
    public int getItemCount() {
        return mDataList == null ? 0 : mDataList.size();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(getInflater().inflate(R.layout.activity_message_lv_item, parent, false));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        MessageItemInfo messageItemInfo = mDataList.get(position);
        holder.setData(messageItemInfo.getName());
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView nameTv;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTv = (TextView) itemView.findViewById(R.id.activity_message_lv_item_name_tv);
        }

        public void setData(String title) {
            this.nameTv.setText(title);
        }
    }

}
