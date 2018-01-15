package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.HadEatedStoreLvAdapter;
import com.feasttime.dishmap.model.bean.HadEatedStoreItemInfo;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2018/1/15.
 */

public class HadEatedStoreActivity extends BaseActivity {
    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    @Bind(R.id.activity_had_eated_store_content_lv)
    ListView contentLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_had_eated_store);
        ButterKnife.bind(this);
        initViews();
    }



    private void initViews() {
        titleCenterTv.setText("去过的店");
        titleBarRightIv.setVisibility(View.GONE);

        ArrayList<HadEatedStoreItemInfo> testDatas = new ArrayList<HadEatedStoreItemInfo>();
        for (int i = 0 ; i < 10 ; i++) {
            HadEatedStoreItemInfo hadEatedStoreItemInfo = new HadEatedStoreItemInfo();
            hadEatedStoreItemInfo.setName(i + "");
            hadEatedStoreItemInfo.setDescription(Math.random() + "");
            testDatas.add(hadEatedStoreItemInfo);
        }

        HadEatedStoreLvAdapter hadEatedStoreLvAdapter = new HadEatedStoreLvAdapter(this,testDatas);
        contentLv.setAdapter(hadEatedStoreLvAdapter);
    }
}
