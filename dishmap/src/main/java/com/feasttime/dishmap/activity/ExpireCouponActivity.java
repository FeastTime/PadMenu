package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.FragmentCouponAdapter;
import com.feasttime.dishmap.fragment.UserCouponFragment;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.CouponInfo;
import com.feasttime.dishmap.utils.PreferenceUtil;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

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

    @Bind(R.id.no_data_layout)
    View nodataView;

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
        contentElv.setGroupIndicator(null);
        requestCouponData();
    }


    @OnClick({R.id.title_back_iv})
    @Override
    public void onClick(View v) {
        if (v == titleBarBackIv) {
            finish();
        }
    }


    public void requestCouponData() {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        infoMap.put("token",token);
        infoMap.put("userId",userId);
        infoMap.put("flag","3");  //0:未过期，2:已过期
        showLoading(null);
        RetrofitService.queryCouponList(infoMap).subscribe(new Consumer<CouponInfo>(){
            @Override
            public void accept(CouponInfo couponInfo) throws Exception {
                if (couponInfo.getResultCode() == 0) {
                    if (couponInfo.getCouponList().size() == 0) {
                        nodataView.setVisibility(View.VISIBLE);
                    } else {
                        FragmentCouponAdapter fragmentCouponAdapter = new FragmentCouponAdapter(ExpireCouponActivity.this,couponInfo.getCouponList());
                        contentElv.setAdapter(fragmentCouponAdapter);
                    }
                } else {

                }
                hideLoading();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                hideLoading();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
    }
}
