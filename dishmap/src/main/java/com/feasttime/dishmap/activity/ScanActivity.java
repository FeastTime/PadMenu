package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Vibrator;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.im.message.EnterStoreMessage;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.URLParser;
import com.feasttime.dishmap.utils.UtilTools;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;

import java.util.HashMap;
import com.google.zxing.Result;
import com.mylhyl.zxing.scanner.common.Scanner;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.rong.imlib.IRongCallback;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;
import io.rong.imlib.model.Message;
import io.rong.message.TextMessage;


public class ScanActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;


    private ScannerView mScannerView;
//    private Result mLastResult;


    private static final String TAG = "ScanActivity";

    private String storeId;
    private String storeName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);
        ButterKnife.bind(this);
        initTitleBar();

        mScannerView = (ScannerView) findViewById(R.id.scanner_view);

        mScannerView.setOnScannerCompletionListener(new OnScannerCompletionListener() {
            @Override
            public void onScannerCompletion(Result rawResult, ParsedResult parsedResult, Bitmap barcode) {

                vibrate();

                Log.d(TAG, rawResult.getText());

                String resultStr =  rawResult.getText();


                if (TextUtils.isEmpty(resultStr)){

                    mScannerView.onResume();
                    Toast.makeText(ScanActivity.this, "请扫优先吃二维码", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    storeId = URLParser.fromURL(resultStr).compile().getParameter("syStoreId");
                    storeName = URLParser.fromURL(resultStr).compile().getParameter("syStoreName");
                }
                catch (Exception e) {

                    e.printStackTrace();
                    mScannerView.onResume();
                    Toast.makeText(ScanActivity.this, "请扫优先吃二维码", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (TextUtils.isEmpty(storeId) || TextUtils.isEmpty(storeName)){
                    mScannerView.onResume();
                    Toast.makeText(ScanActivity.this, "请扫优先吃二维码", Toast.LENGTH_SHORT).show();
                    return;
                }

                //加入聊天群组
                RongIMClient.getInstance().joinGroup(storeId,storeName,new RongIMClient.OperationCallback(){
                    @Override
                    public void onCallback() {
                        super.onCallback();
                    }

                    @Override
                    public void onFail(int errorCode) {
                        super.onFail(errorCode);
                    }

                    @Override
                    public void onFail(RongIMClient.ErrorCode errorCode) {
                        super.onFail(errorCode);
                    }

                    @Override
                    public void onSuccess() {
                        LogUtil.d(TAG,"加入群组成功");

                        //发送进店消息
                        // 建立-修改 用户与商户的关系
                        HashMap<String, String > requestData = new HashMap<>();
                        requestData.put("storeId", storeId);
                        requestData.put("type", WebSocketEvent.ENTER_STORE+"");

                        EnterStoreMessage enterStoreMessage = EnterStoreMessage.obtain(System.currentTimeMillis(), JSON.toJSONString(requestData));
                        RongIMClient.getInstance().sendMessage(Conversation.ConversationType.GROUP, storeId,
                                enterStoreMessage, null, null, new IRongCallback.ISendMessageCallback() {
                                    @Override
                                    public void onAttached(Message message) {
                                        Log.d(TAG, "发送的文本消息已保存至本地数据库中");
                                    }

                                    @Override
                                    public void onSuccess(Message message) {
                                        LogUtil.d(TAG,"进店成功");

                                        // 打开聊天页面
                                        Intent intent = new Intent(ScanActivity.this, ChatActivity.class);
                                        intent.putExtra("STORE_ID", storeId);
                                        intent.putExtra("STORE_NAME",storeName);

                                        ScanActivity.this.startActivity(intent);
                                        ScanActivity.this.finish();

                                    }

                                    @Override
                                    public void onError(Message message, RongIMClient.ErrorCode errorCode) {
                                        Log.d(TAG, "发送消息失败，错误码: " + errorCode.getValue() + '\n');
                                        ToastUtil.showToast(ScanActivity.this,"进店失败",Toast.LENGTH_SHORT);
                                    }
                                });
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        ToastUtil.showToast(ScanActivity.this,"进店失败",Toast.LENGTH_SHORT);
                    }
                });

//
//                MyDialogs.PersonNumListener personNumListener = new MyDialogs.PersonNumListener() {
//
//                    @Override
//                    public void overInput(int personNum) {
//
//                        PreferenceUtil.setIntKey(PreferenceUtil.PERSION_NO, personNum);
//
//                        // 建立-修改 用户与商户的关系
//                        HashMap<String, String > requestData = new HashMap<>();
//                        requestData.put("storeId", storeId);
//                        requestData.put("type", WebSocketEvent.ENTER_STORE+"");
//
//                        UtilTools.requestByWebSocket(ScanActivity.this, requestData);
//
//
//                        Intent intent = new Intent(ScanActivity.this, ChatActivity.class);
//                        intent.putExtra("STORE_ID", storeId);
//
//                        ScanActivity.this.startActivity(intent);
//                        ScanActivity.this.finish();
//                    }
//                };
//
//                MyDialogs.showEatDishPersonNumDialog(ScanActivity.this, personNumListener,storeName);

            }
        });


        mScannerView.setScanMode(Scanner.ScanMode.QR_CODE_MODE);
        //全屏识别
        mScannerView.isScanFullScreen(false);

        mScannerView.setLaserLineResId(R.mipmap.custom_grid_scan_line);

    }

    // 震动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        if (vibrator != null) {
            vibrator.vibrate(200);
        }
    }

    private void initTitleBar() {
        titleBarOrangeBgIv.setVisibility(View.GONE);
        titleCenterTv.setText("扫码");
        titleCenterTv.setTextColor(this.getResources().getColor(R.color.text_gray_1));
        titleBarRightIv.setVisibility(View.GONE);
        titleBarBackIv.setImageResource(R.mipmap.gray_back_icon);
    }

        @Override
    protected void onResume() {
        mScannerView.onResume();
        super.onResume();

    }

    @OnClick({R.id.title_back_iv})
    @Override
    public void onClick(View v) {
        if (v == titleBarBackIv) {
            finish();
        }
    }

    @Override
    protected void onPause() {
        mScannerView.onPause();
        super.onPause();
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }



}
