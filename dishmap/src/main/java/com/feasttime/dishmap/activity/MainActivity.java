package com.feasttime.dishmap.activity;

import android.animation.ValueAnimator;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.fragment.MerchantOpenTableFragment;
import com.feasttime.dishmap.fragment.UserCouponFragment;
import com.feasttime.dishmap.fragment.UserMainFragment;
import com.feasttime.dishmap.fragment.UserMineFragment;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";

    @Bind(R.id.activity_main_home_tv)
    TextView homeTv;

    @Bind(R.id.activity_main_coupon_tv)
    TextView couponTv;

    @Bind(R.id.activity_main_mine_tv)
    TextView mineTv;


    private UserMainFragment mUserMainFragment;
    private UserCouponFragment mUserCouponFragment;
    private UserMineFragment mUserMineFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        homeTv.performClick();

        //修改底部drawable的图片
        initBtmBar(1);
    }

    private void initBtmBar(int selectIndex) {
        Resources resources = this.getResources();
        UtilTools.chenageTextDrawableSize(homeTv,R.mipmap.home_no_selected_icon,(int)resources.getDimension(R.dimen.x50),(int)resources.getDimension(R.dimen.x50),2);
        UtilTools.chenageTextDrawableSize(couponTv,R.mipmap.coupon_no_selected_icon,(int)resources.getDimension(R.dimen.x60),(int)resources.getDimension(R.dimen.x60),2);
        UtilTools.chenageTextDrawableSize(mineTv,R.mipmap.mine_no_select_icon,(int)resources.getDimension(R.dimen.x60),(int)resources.getDimension(R.dimen.x60),2);

        if (selectIndex == 1) {
            UtilTools.chenageTextDrawableSize(homeTv,R.mipmap.home_selected_icon,(int)resources.getDimension(R.dimen.x50),(int)resources.getDimension(R.dimen.x50),2);
        } else if (selectIndex == 2) {
            UtilTools.chenageTextDrawableSize(couponTv,R.mipmap.coupon_selected_icon,(int)resources.getDimension(R.dimen.x60),(int)resources.getDimension(R.dimen.x60),2);
        } else if (selectIndex == 3) {
            UtilTools.chenageTextDrawableSize(mineTv,R.mipmap.mine_selected_icon,(int)resources.getDimension(R.dimen.x60),(int)resources.getDimension(R.dimen.x60),2);
        }
    }

    @OnClick({R.id.activity_main_home_tv,R.id.activity_main_coupon_tv,R.id.activity_main_mine_tv})
    @Override
    public void onClick(View v) {




        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);

        //验证token
        if (v != homeTv) {
            if (!UtilTools.checkLoginStatusAndRelogin(this)) {
                return;
            }
        }

        if (v == homeTv) {
            initBtmBar(1);
            if (mUserMainFragment == null) {
                mUserMainFragment = new UserMainFragment();
            }

            if (mUserMainFragment.isAdded()) {
                fragmentTransaction.show(mUserMainFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(R.id.activity_main_content_fl, mUserMainFragment);
                fragmentTransaction.show(mUserMainFragment).commitAllowingStateLoss();
            }
        } else if (v == couponTv) {
            initBtmBar(2);
            if (mUserCouponFragment == null) {
                mUserCouponFragment = new UserCouponFragment();
            }



            if (mUserCouponFragment.isAdded()) {
                mUserCouponFragment.requestNet();
                fragmentTransaction.show(mUserCouponFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(R.id.activity_main_content_fl, mUserCouponFragment);
                fragmentTransaction.show(mUserCouponFragment).commitAllowingStateLoss();
            }


        } else if (v == mineTv) {
            initBtmBar(3);
            if (mUserMineFragment == null) {
                mUserMineFragment = new UserMineFragment();
            }

            if (mUserMineFragment.isAdded()) {
                mUserMineFragment.requestUserInfo();
                fragmentTransaction.show(mUserMineFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(R.id.activity_main_content_fl, mUserMineFragment);
                fragmentTransaction.show(mUserMineFragment).commitAllowingStateLoss();
            }
        }
    }

    public void toCouponFragment() {
        couponTv.post(new Runnable() {
            @Override
            public void run() {
                couponTv.performClick();
            }
        });
    }


    public void toMainFragment() {
        couponTv.post(new Runnable() {
            @Override
            public void run() {
                homeTv.performClick();
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mUserMainFragment != null && mUserMainFragment.isVisible()) {
            mUserMainFragment.handleTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (mUserMainFragment != null && mUserMainFragment.isAdded()) {
            fragmentTransaction.hide(mUserMainFragment);
        }

        if (mUserCouponFragment != null && mUserCouponFragment.isAdded()) {
            fragmentTransaction.hide(mUserCouponFragment);
        }

        if (mUserMineFragment != null && mUserMineFragment.isAdded()) {
            fragmentTransaction.hide(mUserMineFragment);
        }
    }




}
