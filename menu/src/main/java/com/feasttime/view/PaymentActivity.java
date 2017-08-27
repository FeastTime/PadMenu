package com.feasttime.view;

import android.os.Bundle;

import com.feasttime.presenter.IBasePresenter;

/**
 * Created by chen on 2017/8/26.
 */

public class PaymentActivity extends BaseActivity {
    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected int getLayoutResId() {
        return 0;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
}
