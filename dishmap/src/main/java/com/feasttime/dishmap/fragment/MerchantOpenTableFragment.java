package com.feasttime.dishmap.fragment;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.dhh.websocket.RxWebSocketUtil;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.activity.BossPlaceTableActivity;
import com.feasttime.dishmap.adapter.HistoryTableListAdapter;
import com.feasttime.dishmap.model.WebSocketConfig;
import com.feasttime.dishmap.model.bean.HistoryTableListInfo;
import com.feasttime.dishmap.model.bean.NewTableNofiticationinfo;
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

    @Bind(R.id.fragment_history_table_list_lv)
    ListView contentLv;

    @Bind(R.id.fragment_history_table_list_place_table_iv)
    ImageView placeTableIv;

    private String storeId = "";

    private HistoryTableListAdapter historyTableListAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_history_table_list, container, false);
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
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        RxBus.getDefault().unRegister(this);
    }

    @OnClick({R.id.fragment_history_table_list_place_table_iv})
    @Override
    public void onClick(View v) {
        if (v == placeTableIv) {
            Intent intent = new Intent(v.getContext(), BossPlaceTableActivity.class);
            intent.putExtra("STORE_ID",storeId);
            startActivity(intent);
        }
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
