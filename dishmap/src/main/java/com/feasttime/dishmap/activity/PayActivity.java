package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.PayMethodAdapter;
import com.feasttime.dishmap.customview.HorizontalListView;
import com.feasttime.dishmap.model.bean.PayMethodItemInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2018/1/14.
 */

public class PayActivity extends BaseActivity {

    @Bind(R.id.activity_pay_content_hlv)
    HorizontalListView paymethodHlv;

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay);
        ButterKnife.bind(this);
        initViews();
        initTestData();
    }

    private void initTestData() {
        ArrayList<PayMethodItemInfo> testDatas = new ArrayList<PayMethodItemInfo>();
        for (int i = 0 ; i < 10 ; i++) {
            PayMethodItemInfo payMethodItemInfo = new PayMethodItemInfo();
            payMethodItemInfo.setMoney(i + "");
            payMethodItemInfo.setRemain((int)(Math.random() * 100) + "");
            payMethodItemInfo.setTablePeopleNums(i * 10 + "");
            testDatas.add(payMethodItemInfo);
        }

        PayMethodAdapter payMethodAdapter = new PayMethodAdapter(this,testDatas);
        paymethodHlv.setAdapter(payMethodAdapter);
    }

    private void initViews() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("VIP通道");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);
    }
}
