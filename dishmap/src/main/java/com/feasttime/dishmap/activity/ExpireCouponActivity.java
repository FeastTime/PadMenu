package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 2018/2/2.
 */

public class ExpireCouponActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.activity_expire_coupon_content_elv)
    ExpandableListView contentElv;

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_expire_coupon);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("过期优惠券");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);
    }


    @OnClick({R.id.title_back_iv})
    @Override
    public void onClick(View v) {
        if (v == titleBarBackIv) {
            finish();
        }
    }
}
