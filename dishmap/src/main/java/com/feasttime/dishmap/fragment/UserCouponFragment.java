package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.BaseActivity;
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
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2018/1/10.
 */

public class UserCouponFragment extends Fragment {

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

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_coupon, container, false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }



    private void initViews() {
//        ArrayList<CouponListItemInfo> myTestData = new ArrayList<CouponListItemInfo>();
//        for (int i = 0 ; i < 10 ; i++) {
//            ArrayList<CouponChildListItemInfo> childDatas = new ArrayList<CouponChildListItemInfo>();
//            CouponListItemInfo couponListItemInfo = new CouponListItemInfo();
//            for (int j = 0 ; j < 3 ; j++) {
//                CouponChildListItemInfo couponChildListItemInfo = new CouponChildListItemInfo();
//                couponChildListItemInfo.setCouponName(j * 10 + "");
//                couponChildListItemInfo.setCouponPrice(Math.random() + "");
//                childDatas.add(couponChildListItemInfo);
//                couponListItemInfo.setChildListItemInfos(childDatas);
//            }
//
//
//            couponListItemInfo.setName(i + "");
//            myTestData.add(couponListItemInfo);
//        }

        titleBarBackIv.setVisibility(View.GONE);
        titleBarRightIv.setVisibility(View.GONE);
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("优惠券");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));

//        FragmentCouponAdapter fragmentCouponAdapter = new FragmentCouponAdapter(this.getActivity(),myTestData);
//        mContentElv.setAdapter(fragmentCouponAdapter);

        requestNet();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void requestNet() {
        HashMap<String,Object> infoMap = new HashMap<String,Object>();
        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        infoMap.put("token",token);
        infoMap.put("userId",userId);
        infoMap.put("flag","2");
        final BaseActivity baseActivity = ((BaseActivity)this.getActivity());
        baseActivity.showLoading(null);
        RetrofitService.queryCouponList(infoMap).subscribe(new Consumer<CouponInfo>(){
            @Override
            public void accept(CouponInfo couponInfo) throws Exception {
                if (couponInfo.getResultCode() == 0) {
                    if (couponInfo.getCouponList().size() == 0) {
                        nodataView.setVisibility(View.VISIBLE);
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
}
