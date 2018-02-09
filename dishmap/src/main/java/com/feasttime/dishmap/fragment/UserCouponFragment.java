package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.BaseActivity;
import com.feasttime.dishmap.activity.ExpireCouponActivity;
import com.feasttime.dishmap.activity.SetUserInfoActivity;
import com.feasttime.dishmap.adapter.FragmentCouponAdapter;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.CouponChildListItemInfo;
import com.feasttime.dishmap.model.bean.CouponInfo;
import com.feasttime.dishmap.model.bean.CouponListItemInfo;
import com.feasttime.dishmap.model.bean.QueryUserInfo;
import com.feasttime.dishmap.utils.CircleImageTransformation;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2018/1/10.
 */

public class UserCouponFragment extends Fragment implements View.OnClickListener{

    @Bind(R.id.fragment_user_coupon_content_elv)
    ExpandableListView mContentElv;

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

    @Bind(R.id.fragment_user_coupon_title_no_used_rel)
    RelativeLayout noUsedCouponRel;

    @Bind(R.id.fragment_user_coupon_title_had_used_rel)
    RelativeLayout hadUsedCouponRel;

    @Bind(R.id.fragment_user_coupon_title_no_used_tv)
    TextView noUsedTv;

    @Bind(R.id.fragment_user_coupon_title_no_used_line)
    TextView noUsedLine;


    @Bind(R.id.fragment_user_coupon_title_had_used_tv)
    TextView hadUsedTv;

    @Bind(R.id.fragment_user_coupon_title_had_used_line)
    TextView hadUsedLine;

    Resources resources;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_coupon, container, false);
        ButterKnife.bind(this,view);
        resources = this.getActivity().getResources();
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    public void requestNoUsedCoupon() {
        noUsedCouponRel.performClick();
    }

    private void initViews() {
        titleBarBackIv.setVisibility(View.GONE);
        titleBarRightIv.setVisibility(View.GONE);
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("优惠券");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));

        View footerView = LayoutInflater.from(this.getActivity()).inflate(R.layout.fragment_user_coupon_list_footer_view,null);
        TextView expireCouponTv = (TextView) footerView.findViewById(R.id.fragment_user_coupon_list_footer_view_expire_coupon_tv);
        expireCouponTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(v.getContext(), ExpireCouponActivity.class));
            }
        });

        mContentElv.addFooterView(footerView);
        mContentElv.setGroupIndicator(null);

        noUsedCouponRel.performClick();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void requestNet(String flag) {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        infoMap.put("token",token);
        infoMap.put("userId",userId);
        infoMap.put("flag",flag);  //0未过期，1:已过期
        final BaseActivity baseActivity = ((BaseActivity)this.getActivity());
        baseActivity.showLoading(null);
        RetrofitService.queryCouponList(infoMap).subscribe(new Consumer<CouponInfo>(){
            @Override
            public void accept(CouponInfo couponInfo) throws Exception {
                if (couponInfo.getResultCode() == 0) {
                    if (couponInfo.getCouponList().size() == 0) {
                        nodataView.setVisibility(View.VISIBLE);
                    } else {
                        FragmentCouponAdapter fragmentCouponAdapter = new FragmentCouponAdapter(UserCouponFragment.this.getActivity(),couponInfo.getCouponList());
                        mContentElv.setAdapter(fragmentCouponAdapter);
                    }
                } else {

                }
                baseActivity.hideLoading();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                baseActivity.hideLoading();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {

            }
        });
    }

    @OnClick({R.id.fragment_user_coupon_title_no_used_rel,R.id.fragment_user_coupon_title_had_used_rel})
    @Override
    public void onClick(View v) {
        if (v == hadUsedCouponRel) {

            hadUsedTv.setTextColor(resources.getColor(R.color.orange_color));
            noUsedTv.setTextColor(resources.getColor(R.color.text_gray_design_2));

            hadUsedLine.setVisibility(View.VISIBLE);
            noUsedLine.setVisibility(View.INVISIBLE);
            requestNet("2");
        } else if (v == noUsedCouponRel) {
            requestNet("1");

            noUsedTv.setTextColor(resources.getColor(R.color.orange_color));
            hadUsedTv.setTextColor(resources.getColor(R.color.text_gray_design_2));

            noUsedLine.setVisibility(View.VISIBLE);
            hadUsedLine.setVisibility(View.INVISIBLE);
        }
    }
}
