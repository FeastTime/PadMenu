package com.feasttime.dishmap.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.RedPackageDetail;
import com.feasttime.dishmap.utils.CircleImageTransformation;
import com.feasttime.dishmap.utils.FormatUtil;
import com.feasttime.dishmap.utils.StringUtils;
import com.squareup.picasso.Picasso;
import java.util.List;

/**
 *
 * Created by li on 2018/1/19.
 */

public class RedPackageDetailAdapter extends BaseAdapter {

    private Context context;
    private List<RedPackageDetail> dataList;

    private LayoutInflater mLayoutInflater;

    public RedPackageDetailAdapter(Context context, List<RedPackageDetail> dataList) {

        this.dataList = dataList;
        this.context = context;

        mLayoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return dataList.size();
    }

    @Override
    public Object getItem(int position) {
        return dataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;

        if (convertView == null) {

            convertView = mLayoutInflater.inflate(R.layout.red_package_detail_item, null);
            holder = new ViewHolder();

            holder.userIconIv = (ImageView)convertView.findViewById(R.id.red_package_detail_user_icon_iv);
            holder.isBestIv = (ImageView)convertView.findViewById(R.id.red_package_detail_is_best_iv);
            holder.nickNameTv = (TextView)convertView.findViewById(R.id.red_package_detail_nick_name_tv);
            holder.unpackTimeTv = (TextView)convertView.findViewById(R.id.red_package_detail_time_tv);
            holder.redPackageTitleTv = (TextView)convertView.findViewById(R.id.red_package_detail_redpackage_title_tv);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder)convertView.getTag();
        }

        RedPackageDetail redPackageDetail = dataList.get(position);

        if (!StringUtils.isEmpty(redPackageDetail.getUserIcon())){
            Picasso.with(context).load(redPackageDetail.getUserIcon()).transform(new CircleImageTransformation()).into(holder.userIconIv);
        }

        if (redPackageDetail.getIsBestLuck() == RedPackageDetail.ISBESTLUCK_TRUE){

            holder.isBestIv.setVisibility(View.VISIBLE);
        } else {

            holder.isBestIv.setVisibility(View.GONE);
        }

        holder.nickNameTv.setText(redPackageDetail.getNickName());
        holder.unpackTimeTv.setText(FormatUtil.formatDate(redPackageDetail.getUnpackTime()));
        holder.redPackageTitleTv.setText(redPackageDetail.getRedPackageTitle());

        return convertView;
    }


    static class ViewHolder {

        ImageView userIconIv;
        ImageView isBestIv;
        TextView nickNameTv;
        TextView unpackTimeTv;
        TextView redPackageTitleTv;
    }
}
