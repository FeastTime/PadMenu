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
import com.feasttime.dishmap.adapter.MerchantReachStoreConfirmAdapter;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.feasttime.dishmap.model.bean.HistoryTableListInfo;
import com.feasttime.dishmap.model.bean.ReachStoreConfirmItemInfo;
import com.feasttime.dishmap.rxbus.RxBus;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.reactivex.functions.Consumer;

/**
 * Created by chen on 2017/10/28.
 */

public class MerchantReachStoreConfirmFragment extends Fragment{
    private static final String TAG = "MerchantReachStoreConfirmFragment";

    @Bind(R.id.fragment_merchant_reach_store_confirm_content_lv)
    ListView contentLv;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_merchant_reach_store_confirm, container,
                false);
        ButterKnife.bind(this,view);
        initView();
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
                    if (historyTableListAdapter != null) {
                        historyTableListAdapter.addListData(historyTableListInfo.getDeskList());
                    }
                } else if (orderEvent.eventType == WebSocketEvent.WEBSOCKET_CONNECT_SERVER_SUCCESS) {
                    requestHistoryTableList();
                }
            }
        });

        historyTableListAdapter = new HistoryTableListAdapter(this.getActivity());
        contentLv.setAdapter(historyTableListAdapter);

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

    private void initView() {
        ArrayList<ReachStoreConfirmItemInfo> dataList = new ArrayList<ReachStoreConfirmItemInfo>();
        ReachStoreConfirmItemInfo reachStoreConfirmItemInfo1 = new ReachStoreConfirmItemInfo();
        reachStoreConfirmItemInfo1.setName("小明");
        reachStoreConfirmItemInfo1.setType("5-7");
        reachStoreConfirmItemInfo1.setNumber("665");
        dataList.add(reachStoreConfirmItemInfo1);

        ReachStoreConfirmItemInfo reachStoreConfirmItemInfo2 = new ReachStoreConfirmItemInfo();
        reachStoreConfirmItemInfo2.setName("小红");
        reachStoreConfirmItemInfo2.setType("5-9");
        reachStoreConfirmItemInfo2.setNumber("665");
        dataList.add(reachStoreConfirmItemInfo2);

        ReachStoreConfirmItemInfo reachStoreConfirmItemInfo3 = new ReachStoreConfirmItemInfo();
        reachStoreConfirmItemInfo3.setName("猴塞雷");
        reachStoreConfirmItemInfo3.setType("8-9");
        reachStoreConfirmItemInfo3.setNumber("225");
        dataList.add(reachStoreConfirmItemInfo3);

        MerchantReachStoreConfirmAdapter merchantReachStoreConfirmAdapter = new MerchantReachStoreConfirmAdapter(this.getActivity(),dataList);
        contentLv.setAdapter(merchantReachStoreConfirmAdapter);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
