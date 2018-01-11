package com.feasttime.dishmap.activity;

import android.animation.ValueAnimator;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.fragment.MerchantOpenTableFragment;
import com.feasttime.dishmap.fragment.UserCouponFragment;
import com.feasttime.dishmap.fragment.UserMainFragment;
import com.feasttime.dishmap.fragment.UserMineFragment;
import com.feasttime.dishmap.utils.LogUtil;
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
        Resources resources = this.getResources();
        UtilTools.chenageTextDrawableSize(homeTv,R.mipmap.home_no_selet,(int)resources.getDimension(R.dimen.x45),(int)resources.getDimension(R.dimen.y39));
        UtilTools.chenageTextDrawableSize(couponTv,R.mipmap.coupon_no_select,(int)resources.getDimension(R.dimen.x50),(int)resources.getDimension(R.dimen.y34));
        UtilTools.chenageTextDrawableSize(mineTv,R.mipmap.mine_no_select_icon,(int)resources.getDimension(R.dimen.x35),(int)resources.getDimension(R.dimen.y44));

    }

    @OnClick({R.id.activity_main_home_tv,R.id.activity_main_coupon_tv,R.id.activity_main_mine_tv})
    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (v == homeTv) {
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
            if (mUserCouponFragment == null) {
                mUserCouponFragment = new UserCouponFragment();
            }

            if (mUserCouponFragment.isAdded()) {
                fragmentTransaction.show(mUserCouponFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(R.id.activity_main_content_fl, mUserCouponFragment);
                fragmentTransaction.show(mUserCouponFragment).commitAllowingStateLoss();
            }
        } else if (v == mineTv) {
            if (mUserMineFragment == null) {
                mUserMineFragment = new UserMineFragment();
            }

            if (mUserMineFragment.isAdded()) {
                fragmentTransaction.show(mUserMineFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(R.id.activity_main_content_fl, mUserMineFragment);
                fragmentTransaction.show(mUserMineFragment).commitAllowingStateLoss();
            }
        }
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
