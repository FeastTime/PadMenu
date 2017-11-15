package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.UtilTools;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;

/**
 * 老板放桌
 */

public class BossPlaceTableActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = "BossPlaceTableActivity";

    @Bind(R.id.fragment_merchant_open_table_place_table_btn)
    Button placeTableBtn;

    @Bind(R.id.fragment_merchant_open_table_least_people_et)
    EditText leastPeopleEt;

    @Bind(R.id.fragment_merchant_open_table_most_people_et)
    EditText mostPeopleEt;

    @Bind(R.id.fragment_merchant_open_table_time_et)
    EditText timeEt;

    @Bind(R.id.title_bar_share_iv)
    ImageView titleShareIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_back_iv)
    ImageView titleBackIv;


    private String storeId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.boss_place_table_activity);
        storeId = this.getIntent().getStringExtra("STORE_ID");
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        RxBus.getDefault().register(this, WebSocketEvent.class, new Consumer<WebSocketEvent>() {
            @Override
            public void accept(WebSocketEvent orderEvent) throws Exception {
                if (orderEvent.eventType == WebSocketEvent.BEFORE_TABLES_LIST) {
                    LogUtil.d(TAG,"received data:" + orderEvent.jsonData);
                }
            }
        });

        titleCenterTv.setText("老板放桌");
        titleShareIv.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }

    @OnClick({R.id.fragment_merchant_open_table_place_table_btn,R.id.title_back_iv})
    @Override
    public void onClick(View v) {
        if (v == placeTableBtn) {

            String minPerson = leastPeopleEt.getText().toString();
            String maxPerson = mostPeopleEt.getText().toString();
            String time = timeEt.getText().toString();

            if (TextUtils.equals(minPerson,"0") || TextUtils.equals(maxPerson,"0") || TextUtils.equals(time,"0")) {
                ToastUtil.showToast(v.getContext(),"参数填入错误", Toast.LENGTH_SHORT);
                return;
            }


            HashMap<String,String> requestData = new HashMap<String,String>();
            requestData.put("minPerson",minPerson);
            requestData.put("maxPerson",maxPerson);
            requestData.put("storeID",this.storeId);
            requestData.put("type", WebSocketEvent.BOSS_PLACE_TABLE + "");
            requestData.put("desc","very nice");
            UtilTools.requestByWebSocket(v.getContext(),requestData);
        } else if (v == titleBackIv) {
            finish();
        }
    }
}
