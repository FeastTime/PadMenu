package com.feasttime.dishmap.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.alibaba.fastjson.JSONObject;
import com.dhh.websocket.RxWebSocketUtil;
import com.dhh.websocket.WebSocketInfo;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.utils.DeviceTool;
import com.feasttime.dishmap.utils.ToastUtil;
import com.alibaba.fastjson.JSON;

import java.util.HashMap;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Consumer;
import okhttp3.OkHttpClient;

/**
 * Created by chen on 2017/10/22.
 */

public class TestActivtiy extends BaseActivity implements View.OnClickListener{
    @Bind(R.id.activity_test_join_store_btn)
    Button joinStoreBtn;

    private static String imei = "";
    private static String androidID = "";
    private static String ipv4 = "";
    private static String mac = "";
    private static String mobileNO = "";

    //String wsUrl = "ws://47.94.16.58:9798/feast-web/websocket?token=thisishaha";
    String wsUrl = "ws://192.168.1.101:8081/websocket";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        ButterKnife.bind(this);

        imei = DeviceTool.getIMEI(this);
        androidID = DeviceTool.getAndroidId(this);
        ipv4 = DeviceTool.getIP(this);
        mobileNO = DeviceTool.getPhoneNumber(this);
        mac = DeviceTool.getLocalMacAddress(this);

//        testWebSocket();

        MyDialogs.showEatDishPersonNumDialog(this);
    }


    private void testWebSocket() {
        OkHttpClient okHttpClient = new OkHttpClient();

        RxWebSocketUtil.getInstance().setClient(okHttpClient);
        // show log,default false
        RxWebSocketUtil.getInstance().setShowLog(true);
        //get StringMsg
        RxWebSocketUtil.getInstance().getWebSocketString(wsUrl + "/2")
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        //连接成功后收到消息
                        ToastUtil.showToast(TestActivtiy.this,"receive:" + s, Toast.LENGTH_SHORT);
                    }
                });
    }


    @OnClick({R.id.activity_test_join_store_btn})
    @Override
    public void onClick(View v) {
        if (v == joinStoreBtn) {
            HashMap<String,String> requestData = new HashMap<String,String>();
            requestData.put("imei",imei);
            requestData.put("androidID",androidID);
            requestData.put("mac",mac);
            requestData.put("ipv4",ipv4);
            requestData.put("mobileNo","15810697038");
            requestData.put("storeID","00010001");
            requestData.put("type","1");

            String requestJson = JSON.toJSONString(requestData);

            RxWebSocketUtil.getInstance().asyncSend(wsUrl + "/2", requestJson);
        }
    }
}
