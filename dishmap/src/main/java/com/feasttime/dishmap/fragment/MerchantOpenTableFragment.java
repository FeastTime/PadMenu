package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;

import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/10/28.
 */

public class MerchantOpenTableFragment extends Fragment{
    private static final String TAG = "MerchantOpenTableFragment";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_open_table, container,
                false);
        ButterKnife.bind(this,view);
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
}
