package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.SoftHideKeyBoardUtil;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 *
 * Created by li on 2018/1/15.
 */

public class UpdateMobileNOActivity extends BaseActivity {


    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Bind(R.id.activity_update_mobile_no_tv)
    TextView mobileNoTv;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_mobile_number);
        ButterKnife.bind(this);
        SoftHideKeyBoardUtil.assistActivity(this);
        initViews();

        this.setResult(1);
    }


    private void initViews() {

        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("手机号码");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);


        mobileNoTv.setText(PreferenceUtil.getStringKey(PreferenceUtil.MOBILE_NO));
    }


    MyDialogs.MobileNOChangeListener mobileNOChangeListener = new MyDialogs.MobileNOChangeListener() {
        @Override
        public void refresh() {
            UpdateMobileNOActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    mobileNoTv.setText(PreferenceUtil.getStringKey(PreferenceUtil.MOBILE_NO));
                }
            });
        }
    };

    @OnClick({R.id.activity_set_user_info_nick_save_btn})
    public void onClick(View v) {

        MyDialogs.showCheckMobileNODialog(UpdateMobileNOActivity.this, false, mobileNOChangeListener);
    }

}
