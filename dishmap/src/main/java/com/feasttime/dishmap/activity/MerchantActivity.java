package com.feasttime.dishmap.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.fragment.MerchantMineFragment;
import com.feasttime.dishmap.fragment.MerchantOpenTableFragment;
import com.feasttime.dishmap.fragment.MerchantReachStoreConfirmFragment;
import com.feasttime.dishmap.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 2017/10/28.
 */

public class MerchantActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.activity_merchant_content_fl)
    FrameLayout containerFl;

    @Bind(R.id.activity_merchant_open_table_tv)
    TextView opentTableTv;

    @Bind(R.id.activity_merchant_reach_store_confirm_tv)
    TextView reachStoreConfirmTv;

    @Bind(R.id.activity_merchant_mine_tv)
    TextView mineTv;


    private MerchantOpenTableFragment merchantOpenTableFragment;
    private MerchantMineFragment merchantMineFragment;
    private MerchantReachStoreConfirmFragment merchantReachStoreConfirmFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_merchant);
        ButterKnife.bind(this);
        initView();
    }


    private void initView() {
        int picSize = (int)this.getResources().getDimension(R.dimen.x40);
        int picSize2 = (int)this.getResources().getDimension(R.dimen.x36);
        int picSize3 = (int)this.getResources().getDimension(R.dimen.x35);
        int picSize4 = (int)this.getResources().getDimension(R.dimen.y44);
        UtilTools.chenageTextDrawableSize(opentTableTv,R.mipmap.setting,picSize,picSize);
        UtilTools.chenageTextDrawableSize(reachStoreConfirmTv,R.mipmap.confirm,picSize2,picSize2);
        UtilTools.chenageTextDrawableSize(mineTv,R.mipmap.mine,picSize3,picSize4);

        opentTableTv.performClick();

//        merchantOpenTableFragment = new MerchantOpenTableFragment();
//
//        mFragmentManager = getFragmentManager();
//        FragmentTransaction fragmentTransaction = mFragmentManager.beginTransaction();
//        fragmentTransaction.add(R.id.activity_merchant_content_fl,merchantOpenTableFragment).commit();
    }


    @OnClick({R.id.activity_merchant_open_table_tv,R.id.activity_merchant_reach_store_confirm_tv,R.id.activity_merchant_mine_tv})
    @Override
    public void onClick(View v) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        hideAllFragment(fragmentTransaction);
        if (v == opentTableTv) {
            if (merchantOpenTableFragment == null) {
                merchantOpenTableFragment = new MerchantOpenTableFragment();
            }

            if (merchantOpenTableFragment.isAdded()) {
                fragmentTransaction.show(merchantOpenTableFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(R.id.activity_merchant_content_fl, merchantOpenTableFragment);
                fragmentTransaction.show(merchantOpenTableFragment).commitAllowingStateLoss();
            }
        } else if (v == reachStoreConfirmTv) {
            if (merchantReachStoreConfirmFragment == null) {
                merchantReachStoreConfirmFragment = new MerchantReachStoreConfirmFragment();
            }

            if (merchantReachStoreConfirmFragment.isAdded()) {
                fragmentTransaction.show(merchantReachStoreConfirmFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(R.id.activity_merchant_content_fl, merchantReachStoreConfirmFragment);
                fragmentTransaction.show(merchantReachStoreConfirmFragment).commitAllowingStateLoss();
            }
        } else if (v == mineTv) {
            if (merchantMineFragment == null) {
                merchantMineFragment = new MerchantMineFragment();
            }

            if (merchantMineFragment.isAdded()) {
                fragmentTransaction.show(merchantMineFragment).commitAllowingStateLoss();
            } else {
                fragmentTransaction.add(R.id.activity_merchant_content_fl, merchantMineFragment);
                fragmentTransaction.show(merchantMineFragment).commitAllowingStateLoss();
            }
        }


    }

    private void hideAllFragment(FragmentTransaction fragmentTransaction) {
        if (merchantMineFragment != null && merchantMineFragment.isAdded()) {
            fragmentTransaction.hide(merchantMineFragment);
        }

        if (merchantOpenTableFragment != null && merchantOpenTableFragment.isAdded()) {
            fragmentTransaction.hide(merchantOpenTableFragment);
        }

        if (merchantReachStoreConfirmFragment != null && merchantReachStoreConfirmFragment.isAdded()) {
            fragmentTransaction.hide(merchantReachStoreConfirmFragment);
        }
    }
}
