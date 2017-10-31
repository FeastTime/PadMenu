package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
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
 * Created by chen on 2017/10/28.
 */

public class MerchantOpenTableFragment extends Fragment implements View.OnClickListener{
    private static final String TAG = "MerchantOpenTableFragment";

    @Bind(R.id.fragment_merchant_open_table_place_table_btn)
    Button placeTableBtn;

    @Bind(R.id.fragment_merchant_open_table_least_people_et)
    EditText leastPeopleEt;

    @Bind(R.id.fragment_merchant_open_table_most_people_et)
    EditText mostPeopleEt;

    @Bind(R.id.fragment_merchant_open_table_time_et)
    EditText timeEt;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_open_table, container,
                false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }

    private void init() {
        RxBus.getDefault().register(this, WebSocketEvent.class, new Consumer<WebSocketEvent>() {
            @Override
            public void accept(WebSocketEvent orderEvent) throws Exception {
                if (orderEvent.eventType == WebSocketEvent.RECEIVE_SERVER_DATA) {
                    LogUtil.d(TAG,"received data:" + orderEvent.jsonData);
                }
            }
        });
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }

    @OnClick({R.id.fragment_merchant_open_table_place_table_btn})
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
            requestData.put("storeID","");
            requestData.put("desc","very nice");
            UtilTools.requestByWebSocket(v.getContext(),requestData);
        }
    }
}
