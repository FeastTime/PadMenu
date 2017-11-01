package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.utils.PreferenceUtil;

import cn.bingoogolapple.qrcode.core.QRCodeView;
import cn.bingoogolapple.qrcode.zbar.ZBarView;
import cn.bingoogolapple.qrcode.zxing.ZXingView;

public class ScanActivity extends AppCompatActivity {


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


                if (result.startsWith("优先吃：")){
                    String storeID = result.substring(4);
                    Log.d(TAG, storeID);

                    String storeType = PreferenceUtil.getStringKey(PreferenceUtil.USER_TYPE);
                    if (TextUtils.equals(storeType,"store")) {
                        Intent intent = new Intent(ScanActivity.this, MerchantActivity.class);
                        intent.putExtra("STORE_ID", storeID);
                        ScanActivity.this.startActivity(intent);
                    } else {
                        Intent intent = new Intent(ScanActivity.this, ChatActivity.class);
                        intent.putExtra("STORE_ID", storeID);
                        ScanActivity.this.startActivity(intent);
                    }


                    ScanActivity.this.finish();
                }

                mQRCodeView.startSpot();
            }

            @Override
            public void onScanQRCodeOpenCameraError() {
                Log.d(TAG, "onScanQRCodeOpenCameraError");
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();

        mQRCodeView.startCamera();
        mQRCodeView.showScanRect();
        mQRCodeView.startSpot();
    }


    @Override
    protected void onPause() {
        super.onPause();

        mQRCodeView.stopSpot();
        mQRCodeView.stopCamera();
    }


    @Override
    protected void onDestroy() {
        mQRCodeView.onDestroy();
        super.onDestroy();
    }



}
