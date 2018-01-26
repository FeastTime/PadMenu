package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.bean.MyTableItemInfo;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by chen on 2018/1/12.
 */

public class MySeatActivity extends BaseActivity{
    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;


    @Bind(R.id.activity_my_seat_content_lv)
    ListView contentLv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_seat);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("我的座位");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);

        contentLv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyTableItemInfo myTableItemInfo = (MyTableItemInfo)parent.getAdapter().getItem(position);
                Intent intent = new Intent(view.getContext(),MySeatDetailActivity.class);
                intent.putExtra("tablesData",myTableItemInfo);
                startActivity(intent);
            }
        });
    }

}
