package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2018/1/15.
 */

public class SetUserInfoActivity extends BaseActivity {

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
        setContentView(R.layout.activity_set_user_info);
        ButterKnife.bind(this);
        initViews();
    }


    private void initViews() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("设置");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);
    }
}
