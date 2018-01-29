package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.customview.MyDialogs;
import com.feasttime.dishmap.utils.PreferenceUtil;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends BaseActivity {


    private static final String TAG = "ScanActivity";


    QRCodeView mQRCodeView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan);

        mQRCodeView = (ZBarView) findViewById(R.id.zbarview);

        mQRCodeView.setDelegate(new QRCodeView.Delegate() {
            @Override
            public void onScanQRCodeSuccess(String result) {
                Log.d(TAG, result);


                int start = result.indexOf("storeId=")+ "storeId=".length();
                int end = result.indexOf("&", start);
                final String storeID = result.substring(start, end);

                // 如果没有storeID 重新扫描
                if (TextUtils.isEmpty(storeID)){

                    vibrate();

                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    mQRCodeView.startSpotDelay(0);
                }

                Log.d(TAG, storeID);

                String userType = PreferenceUtil.getStringKey(PreferenceUtil.USER_TYPE);


                MyDialogs.PersonNumListener personNumListener = new MyDialogs.PersonNumListener() {
                    @Override
                    public void overInput(int personNum) {

                        PreferenceUtil.setIntKey(PreferenceUtil.PERSION_NO, personNum);

                        Intent intent = new Intent(ScanActivity.this, ChatActivity.class);
                        intent.putExtra("STORE_ID", storeID);

                        ScanActivity.this.startActivity(intent);
                        ScanActivity.this.finish();
                    }
                };

                MyDialogs.showEatDishPersonNumDialog(ScanActivity.this, personNumListener);

            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                Log.d(TAG, "onScanQRCodeOpenCameraError");

            }
        });
    }

    // 震动
    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }


    @Override
    protected void onResume() {
        super.onResume();


        mQRCodeView.startCamera();
        vibrate();
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        mQRCodeView.startSpotDelay(0);



    }


    @Override
    protected void onPause() {
        super.onPause();

        mQRCodeView.stopCamera();
    }


    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }



}
