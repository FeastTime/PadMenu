package com.feasttime.dishmap.activity;

import android.os.Bundle;

import com.feasttime.dishmap.R;

import butterknife.ButterKnife;

/**
 * Created by chen on 2018/1/14.
 */

public class PayActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);

    }
}
