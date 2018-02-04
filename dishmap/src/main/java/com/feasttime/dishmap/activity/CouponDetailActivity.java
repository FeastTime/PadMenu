package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.CouponChildListItemInfo;
import com.feasttime.dishmap.model.bean.CouponListItemInfo;
import com.feasttime.dishmap.utils.QRCodeUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2018/2/4.
 */

public class CouponDetailActivity extends BaseActivity {

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Bind(R.id.activity_coupon_detail_qr_code_iv)
    ImageView qrCodeIv;

    @Bind(R.id.activity_coupon_detail_store_name_tv)
    TextView storeNameTv;

    @Bind(R.id.activity_coupon_detail_expire_tv)
    TextView expireTv;

    @Bind(R.id.activity_coupon_detail_description_tv)
    TextView descriptionTv;

    @Bind(R.id.activity_coupon_detail_number_tv)
    TextView numberTv;

    private CouponChildListItemInfo couponChildListItemInfo;

    private String storeName = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon_detail);
        ButterKnife.bind(this);

        storeName = this.getIntent().getStringExtra("storeName");
        couponChildListItemInfo = (CouponChildListItemInfo)this.getIntent().getSerializableExtra("couponData");

        initViews();
    }


    private void initViews() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("我的座位");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);
        qrCodeIv.setImageBitmap(QRCodeUtil.createQRCodeBitmap(couponChildListItemInfo.getCouponCode(),300));

        storeNameTv.setText(storeName);
        descriptionTv.setText(couponChildListItemInfo.getPermissionsDescribed());
        expireTv.setText(couponChildListItemInfo.getCouponValidity());
        numberTv.setText(couponChildListItemInfo.getCouponCode());
    }
}
