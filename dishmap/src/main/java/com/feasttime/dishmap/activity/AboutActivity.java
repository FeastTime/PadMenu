package com.feasttime.dishmap.activity;

import android.os.Bundle;

import com.feasttime.dishmap.R;

import butterknife.ButterKnife;

/**
 * Created by chen on 2018/1/19.
 */

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        ButterKnife.bind(this);
    }
}
