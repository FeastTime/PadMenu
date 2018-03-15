package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.model.bean.MyTableItemInfo;
import com.feasttime.dishmap.utils.FormatUtil;
import com.feasttime.dishmap.utils.QRCodeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 *
 * Created by chen on 2018/1/26.
 */

public class MySeatDetailActivity extends BaseActivity {
    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Bind(R.id.activity_my_seat_detail_qr_code_iv)
    ImageView qrCodeIv;

    @Bind(R.id.activity_my_seat_detail_store_name_tv)
    TextView storeNameTv;

    @Bind(R.id.activity_my_seat_detail_expire_tv)
    TextView expireTv;

    @Bind(R.id.activity_my_seat_detail_description_tv)
    TextView descriptionTv;

    @Bind(R.id.activity_my_seat_detail_number_tv)
    TextView numberTv;

    MyTableItemInfo myTableItemInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_seat_detail);
        ButterKnife.bind(this);
        myTableItemInfo = (MyTableItemInfo)this.getIntent().getSerializableExtra("tablesData");
        initViews();
    }

    private void initViews() {

        MyDialogs.showCheckMobileNODialog(MySeatDetailActivity.this, true, null);

        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("我的座位");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);
        qrCodeIv.setImageBitmap(QRCodeUtil.createQRCodeBitmap(myTableItemInfo.getTableId(),300));
        storeNameTv.setText(myTableItemInfo.getStoreName());
        descriptionTv.setText(myTableItemInfo.getDescription());

        long expireTime = myTableItemInfo.getTaketableTime() + myTableItemInfo.getRecieveTime()*60*1000;

        expireTv.setText(FormatUtil.formatDate(expireTime));
        numberTv.setText(myTableItemInfo.getTableId());
    }
}
