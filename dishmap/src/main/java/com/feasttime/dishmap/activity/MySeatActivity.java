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
import com.feasttime.dishmap.adapter.MySeatAdapter;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.model.bean.MyTableInfo;
import com.feasttime.dishmap.model.bean.MyTableItemInfo;
import com.feasttime.dishmap.model.bean.QueryUserInfo;
import com.feasttime.dishmap.utils.CircleImageTransformation;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.squareup.picasso.Picasso;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;

/**
 *
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

    @Bind(R.id.no_data_layout)
    View nodataView;

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
                Intent intent = new Intent(view.getContext(), MySeatDetailActivity.class);
                intent.putExtra("tablesData",myTableItemInfo);
                startActivity(intent);
            }
        });

        HashMap<String,Object> infoMap = new HashMap<>();
        String userId = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);
        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
        infoMap.put("token",token);
        infoMap.put("userId",userId);

        showLoading(null);
        RetrofitService.queryMyTableList(infoMap).subscribe(new Consumer<MyTableInfo>(){
            @Override
            public void accept(MyTableInfo myTableInfo) throws Exception {
                if (myTableInfo.getResultCode() == 0) {
                    MySeatAdapter mySeatAdapter = new MySeatAdapter(MySeatActivity.this,myTableInfo.getTablesList());
                    contentLv.setAdapter(mySeatAdapter);
                } else {
                }
                hideLoading();
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                hideLoading();
            }
        }, new Action() {
            @Override
            public void run() throws Exception {
                hideLoading();
            }
        });
    }

}
