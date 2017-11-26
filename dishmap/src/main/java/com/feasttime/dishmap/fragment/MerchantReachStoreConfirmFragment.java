package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.adapter.HistoryTableListAdapter;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.feasttime.dishmap.model.bean.HistoryTableListInfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.UtilTools;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/10/28.
 */

public class MerchantReachStoreConfirmFragment extends Fragment{
    private static final String TAG = "MerchantReachStoreConfirmFragment";

    private String storeId;

    @Bind(R.id.fragment_merchant_reach_store_confirm_content_lv)
    ListView contentLv;

    private HistoryTableListAdapter merchantReachStoreConfirmAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_reach_store_confirm, container,
                false);
        ButterKnife.bind(this,view);
        init();
        return view;
    }


    private void init() {
        RxBus.getDefault().register(this, WebSocketEvent.class, new Consumer<WebSocketEvent>() {
            @Override
            public void accept(WebSocketEvent orderEvent) throws Exception {
                if (orderEvent.eventType == WebSocketEvent.BEFORE_TABLES_LIST) {
                    LogUtil.d(TAG,"received data:" + orderEvent.jsonData);
                    HistoryTableListInfo historyTableListInfo = JSON.parseObject(orderEvent.jsonData,HistoryTableListInfo.class);
                    if (merchantReachStoreConfirmAdapter != null) {
                        merchantReachStoreConfirmAdapter.addListData(historyTableListInfo.getDeskList());
                    }
                } else if (orderEvent.eventType == WebSocketEvent.WEBSOCKET_CONNECT_SERVER_SUCCESS) {
                    requestHistoryTableList();
                }
            }
        });

        merchantReachStoreConfirmAdapter = new HistoryTableListAdapter(this.getActivity());
        contentLv.setAdapter(merchantReachStoreConfirmAdapter);

        RxWebSocketUtil.getInstance().getWebSocket(WebSocketConfig.wsRequestUrl);

        if (WebSocketConfig.WEB_SOCKET_IS_CONNECTED) {
            requestHistoryTableList();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    private void requestHistoryTableList() {
        HashMap<String,String> requestData = new HashMap<String,String>();
        requestData.put("pageNo","1");
        requestData.put("PageNum","10");
        requestData.put("storeID",this.storeId);
        requestData.put("type", WebSocketEvent.REQUEST_BEFORE_TABLES_LIST + "");
        UtilTools.requestByWebSocket(this.getActivity(),requestData);
    }
}
