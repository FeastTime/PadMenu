package com.feasttime.dishmap.activity;

import android.os.Bundle;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.utils.SoftHideKeyBoardUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2018/1/11.
 */

public class ScanSuccessActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_success);
        ButterKnife.bind(this);
        SoftHideKeyBoardUtil.assistActivity(this);
    }
}
