package com.feasttime.dishmap.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Vibrator;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import com.feasttime.dishmap.R;
import com.feasttime.dishmap.model.RetrofitService;
import com.feasttime.dishmap.model.bean.BaseResponseBean;
import com.feasttime.dishmap.utils.LogUtil;
import com.feasttime.dishmap.utils.PreferenceUtil;
import com.feasttime.dishmap.utils.ToastUtil;
import com.feasttime.dishmap.utils.URLParser;
import com.google.zxing.client.result.ParsedResult;
import com.mylhyl.zxing.scanner.OnScannerCompletionListener;
import com.mylhyl.zxing.scanner.ScannerView;
import java.util.HashMap;
import com.google.zxing.Result;
import com.mylhyl.zxing.scanner.common.Scanner;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.rong.imlib.RongIMClient;


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
                        String token = PreferenceUtil.getStringKey(PreferenceUtil.TOKEN);
                        String userID = PreferenceUtil.getStringKey(PreferenceUtil.USER_ID);

                        HashMap<String,Object> infoMap = new HashMap<String,Object>();
                        infoMap.put("token",token);
                        infoMap.put("userId",userID);
                        infoMap.put("storeId",storeId);

                        showLoading(null);
                        RetrofitService.userComeInProc(infoMap).subscribe(new Consumer<BaseResponseBean>(){
                            @Override
                            public void accept(BaseResponseBean baseResponseBean) throws Exception {
                                if (baseResponseBean.getResultCode() == 0) {
                                    LogUtil.d(TAG,"进店成功");

                                    // 打开聊天页面
                                    Intent intent = new Intent(ScanActivity.this, ChatActivity.class);
                                    intent.putExtra("STORE_ID", storeId);
                                    intent.putExtra("STORE_NAME",storeName);

                                    ScanActivity.this.startActivity(intent);
                                    ScanActivity.this.finish();
                                    finish();
                                } else {
                                    mScannerView.onResume();

                                }
                                hideLoading();
                            }
                        }, new Consumer<Throwable>() {
                            @Override
                            public void accept(Throwable throwable) throws Exception {
                                hideLoading();
                            }
                        }, new Action() {
                            @Override
                            public void run() throws Exception {

                            }
                        });
                    }

                    @Override
                    public void onError(RongIMClient.ErrorCode errorCode) {
                        ToastUtil.showToast(ScanActivity.this,"进店失败",Toast.LENGTH_SHORT);
                        mScannerView.onResume();
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

//        mScannerView.setLaserLineResId(R.mipmap.custom_grid_scan_line);

        mScannerView.setLaserColor(Color.argb(255,248,149,40));

        mScannerView.setLaserFrameBoundColor(Color.BLUE);



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
