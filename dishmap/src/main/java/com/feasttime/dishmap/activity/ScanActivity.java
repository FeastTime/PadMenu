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

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.rxbus.event.WebSocketEvent;
import com.feasttime.dishmap.utils.PreferenceUtil;
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


public class ScanActivity extends BaseActivity implements View.OnClickListener{

    @Bind(R.id.title_back_iv)
    ImageView titleBarBackIv;

    @Bind(R.id.title_bar_right_iv)
    ImageView titleBarRightIv;

    @Bind(R.id.title_center_text_tv)
    TextView titleCenterTv;

    @Bind(R.id.title_bar_layout_orange_bg_iv)
    ImageView titleBarOrangeBgIv;

    public static final String EXTRA_LASER_LINE_MODE = "extra_laser_line_mode";
    public static final String EXTRA_SCAN_MODE = "extra_scan_mode";
    public static final String EXTRA_SHOW_THUMBNAIL = "EXTRA_SHOW_THUMBNAIL";
    public static final String EXTRA_SCAN_FULL_SCREEN = "EXTRA_SCAN_FULL_SCREEN";
    public static final String EXTRA_HIDE_LASER_FRAME = "EXTRA_HIDE_LASER_FRAME";

    public static final int EXTRA_LASER_LINE_MODE_0 = 0;
    public static final int EXTRA_LASER_LINE_MODE_1 = 1;
    public static final int EXTRA_LASER_LINE_MODE_2 = 2;

    public static final int EXTRA_SCAN_MODE_0 = 0;
    public static final int EXTRA_SCAN_MODE_1 = 1;
    public static final int EXTRA_SCAN_MODE_2 = 2;

    public static final int APPLY_READ_EXTERNAL_STORAGE = 0x111;


    private ScannerView mScannerView;
    private Result mLastResult;


    private static final String TAG = "ScanActivity";



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
                    return;
                }

                int start = resultStr.indexOf("storeId=")+ "storeId=".length();
                int end = resultStr.indexOf("&", start);
                final String storeId = resultStr.substring(start, end);


                MyDialogs.PersonNumListener personNumListener = new MyDialogs.PersonNumListener() {

                    @Override
                    public void overInput(int personNum) {

                        PreferenceUtil.setIntKey(PreferenceUtil.PERSION_NO, personNum);

                        // 建立-修改 用户与商户的关系
                        HashMap<String, String > requestData = new HashMap<>();
                        requestData.put("storeId", storeId);
                        requestData.put("type", WebSocketEvent.ENTER_STORE+"");

                        UtilTools.requestByWebSocket(ScanActivity.this, requestData);


                        Intent intent = new Intent(ScanActivity.this, ChatActivity.class);
                        intent.putExtra("STORE_ID", storeId);

                        ScanActivity.this.startActivity(intent);
                        ScanActivity.this.finish();
                    }
                };

                MyDialogs.showEatDishPersonNumDialog(ScanActivity.this, personNumListener);

            }
        });

        mScannerView.setScanMode(Scanner.ScanMode.QR_CODE_MODE);
        //全屏识别
        mScannerView.isScanFullScreen(false);

        mScannerView.setLaserLineResId(R.mipmap.custom_grid_scan_line);


//        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);

//        mQRCodeView.setDelegate(new QRCodeView.Delegate() {
//            @Override
//            public void onScanQRCodeSuccess(String result) {
//
//                Log.d(TAG, result);
//
//                int start = result.indexOf("storeId=")+ "storeId=".length();
//                int end = result.indexOf("&", start);
//                final String storeId = result.substring(start, end);
//
//                // 如果没有storeID 重新扫描
//                if (TextUtils.isEmpty(storeId)){
//
//                    vibrate();
//
//                    try {
//                        Thread.sleep(100);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//
//                    mQRCodeView.startSpotDelay(0);
//                }
//
//                Log.d(TAG, "已经获得storeID ： " + storeId);
//
//                MyDialogs.PersonNumListener personNumListener = new MyDialogs.PersonNumListener() {
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
//                MyDialogs.showEatDishPersonNumDialog(ScanActivity.this, personNumListener);
//            }
//
//            @Override
//            public void onScanQRCodeOpenCameraError() {
//                Log.d(TAG, "onScanQRCodeOpenCameraError");
//
//            }
//        });
    }

    // 震动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
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
        resetStatusView();

        super.onResume();

    }

    private void resetStatusView() {
        mLastResult = null;
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
