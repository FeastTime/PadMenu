package com.feasttime.dishmap.activity;

import android.app.Fragment;
import android.os.Bundle;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.fragment.MerchantMineFragment;
import com.feasttime.dishmap.fragment.MerchantOpenTableFragment;
import com.feasttime.dishmap.fragment.MerchantReachStoreConfirmFragment;
import com.feasttime.dishmap.utils.UtilTools;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2017/10/28.
 */

public class MerchantActivity extends BaseActivity {

    @Bind(R.id.activity_merchant_content_fl)
    FrameLayout containerFl;

    @Bind(R.id.activity_merchant_open_table_tv)
    TextView opentTableTv;

    @Bind(R.id.activity_merchant_reach_store_confirm_tv)
    TextView reachStoreConfirmTv;

    @Bind(R.id.activity_merchant_mine_tv)
    TextView mineTv;


    private FragmentManager mFragmentManager;
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


        merchantOpenTableFragment = new MerchantOpenTableFragment();
        merchantMineFragment = new MerchantMineFragment();
        merchantReachStoreConfirmFragment = new MerchantReachStoreConfirmFragment();

        mFragmentManager = getFragmentManager();
        FragmentTransaction mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.add(R.id.activity_merchant_content_fl,merchantOpenTableFragment).commit();
    }


}
