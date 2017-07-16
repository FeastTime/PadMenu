package com.ipinyou.ads;


import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.location.Location;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.alibaba.fastjson.JSON;
import com.ipinyou.ads.bean.Ad;
import com.ipinyou.ads.bean.AdRequest;
import com.ipinyou.ads.bean.AdResponse;
import com.ipinyou.ads.bean.App;
import com.ipinyou.ads.bean.Device;
import com.ipinyou.ads.bean.Geo;
import com.ipinyou.ads.bean.ScreenInfo;
import com.ipinyou.ads.properties.ViewProperties;
import com.ipinyou.ads.tools.HttpTool;
import com.ipinyou.ads.tools.StringUtils;
import com.ipinyou.ads.webView.ADWebView;
import com.ipinyou.ads.webView.PYWebViewClient;

import java.math.BigDecimal;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ipinyou.ads.properties.HttpProperties.AD_URL;
import static com.ipinyou.ads.tools.DeviceTool.getAndroidVersion;
import static com.ipinyou.ads.tools.DeviceTool.getCarrierName;
import static com.ipinyou.ads.tools.DeviceTool.getDeviceBrand;
import static com.ipinyou.ads.tools.DeviceTool.getDeviceModel;
import static com.ipinyou.ads.tools.DeviceTool.getDeviceScreenInfo;
import static com.ipinyou.ads.tools.DeviceTool.getDeviceType;
import static com.ipinyou.ads.tools.DeviceTool.getIMEI;
import static com.ipinyou.ads.tools.DeviceTool.getIP;
import static com.ipinyou.ads.tools.DeviceTool.getLocation;
import static com.ipinyou.ads.tools.HttpTool.isNetworkAvailable;


public class IpinyouInterstitial {


    public void setPYOnTouchListener(View.OnTouchListener onTouchListener){

        webView.setPYOnTouchListener(onTouchListener);
    }

    public void removePYOnTouchListener(){
        webView.removePYOnTouchListener();
    }


    private int ad_width, ad_height;

    private long placementID;
    private long lastRefreshTime = 0L;
    private long refreshTime = 10000;
    private int border = 60;

//    private boolean isNeedAdapt = false;

    private ADWebView webView;

    private Activity mActivity;
    private WindowManager windowManager;
    private WindowManager.LayoutParams wmParams;
    

    private final String mimeType = "text/html";
    private final String encoding = "utf-8";
    private final CompositeDisposable disposables = new CompositeDisposable();

    private FrameLayout frameLayout;
    private ImageView closeImage;


    public IpinyouInterstitial(Activity activity, long placementID) {


        this.placementID = placementID;
        Log.d("ipinyoutest", placementID + "");

        initFloatWindowManage(activity);

        mActivity = activity;


        ScreenInfo screenInfo = getDeviceScreenInfo(mActivity);

        ad_width = screenInfo.getWidth();
        ad_height = screenInfo.getHeight();

        frameLayout = new FrameLayout(activity);
        frameLayout.setLayoutParams(new RelativeLayout.LayoutParams(new FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1)));


        webView = new ADWebView(mActivity, true, true);
        webView.setBackgroundColor(Color.TRANSPARENT);
        webView.setBackgroundColor(Color.BLUE);

        FrameLayout.LayoutParams webViewParams = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        webViewParams.gravity = Gravity.CENTER;
        webViewParams.setMargins(border /2 , border /2, border /2, border /2);

        frameLayout.addView(webView, webViewParams);


        closeImage = new ImageView(activity);
        closeImage.setImageDrawable(activity.getResources().getDrawable(R.drawable.close));

        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.height = border;// 设置图片的高度
        layoutParams.width = border; // 设置图片的宽度
        closeImage.setLayoutParams(layoutParams);

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(FrameLayout.LayoutParams.WRAP_CONTENT,FrameLayout.LayoutParams.WRAP_CONTENT);
        params.gravity = Gravity.RIGHT|Gravity.TOP;
        params.height= border;
        params.width= border;

        frameLayout.addView(closeImage, params);


        closeImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                windowManager.removeView(frameLayout);
//                destroyWebView();
            }
        });

//        ViewGroup.LayoutParams closeImageLayoutParams = closeImage.getLayoutParams();
//        closeImageLayoutParams.width = 70;
//        closeImageLayoutParams.height = 70;
//        closeImage.setLayoutParams(closeImageLayoutParams);
    }

    public void loadAD() {

//        isLoadAD = true;

        // 请求广告
        getAD();
    }

    public void destory(){
        destroyWebView();
    }

    private void initFloatWindowManage(Activity activity) {

        windowManager = activity.getWindowManager();

        wmParams = new WindowManager.LayoutParams();

        wmParams.format = PixelFormat.RGBA_8888;
        wmParams.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;

      
        wmParams.width = WindowManager.LayoutParams.WRAP_CONTENT;
        wmParams.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }



    public void close(){
        destroyWebView();
    }

//    @Override
//    protected void onDetachedFromWindow() {
//
//
//        destroyWebView();
//
//        super.onDetachedFromWindow();
//    }

//    @Override
//    protected void onAttachedToWindow() {
//
//        super.onAttachedToWindow();
//
//
//
//        if (null == timer) {
//
//            timer = new Timer(true);
//
//            timer.schedule(new java.util.TimerTask() {
//
//                @Override
//                public void run() {
//
//
//                    if (!isLoadAD || System.currentTimeMillis() - lastRefreshTime < refreshTime) {
//
//                        return;
//                    }
//
//                    lastRefreshTime = System.currentTimeMillis();
//
//                    if (IpinyouInterstitial.this.isShown()) {
//
//                        // 请求广告
//                        getAD();
//                    }
//
//                }
//
//            }, 0L, 300L);
//
//        }
//
//        // 请求广告
//        getAD();
//
//    }



    private void getAD() {


        disposables.add(

                Observable
                        .just("e")
                        .map(new Function<String, AdRequest>() {
                            @Override
                            public AdRequest apply(String s) throws Exception {

                                AdRequest adRequest = new AdRequest();
                                adRequest.setAdHeight(ad_height);
                                adRequest.setAdWith(ad_width);

                                adRequest.setAdRequired(true);

                                adRequest.setAdSlotID(placementID);


                                SharedPreferences sharedPreferences = mActivity.getApplicationContext().getSharedPreferences("ipinyou", Context.MODE_APPEND);
                                String app_id = sharedPreferences.getString(ViewProperties.APP_ID, "");
                                if (StringUtils.isNotEmpty(app_id)){
                                    adRequest.setSdkID(Long.parseLong(app_id));
                                }
                                adRequest.setApp(new App());
                                adRequest.setDevice(new Device());
                                adRequest.setGeo(new Geo());
                                adRequest.setSecure(false);
                                adRequest.setTest(false);

                                return adRequest;
                            }
                        })

                        .map(new Function<AdRequest, AdRequest>() {
                            @Override
                            public AdRequest apply(AdRequest adRequest) throws Exception {

                                SharedPreferences sharedPreferences = mActivity.getApplicationContext().getSharedPreferences("ipinyou", Context.MODE_APPEND);

                                adRequest.getApp().setID(Double.parseDouble(sharedPreferences.getString(ViewProperties.APP_ID, "0")));
                                adRequest.getApp().setBundle(mActivity.getApplicationContext().getPackageName());
                                adRequest.getApp().setVersion(mActivity.getApplicationContext().getPackageManager().getPackageInfo(mActivity.getApplicationContext().getPackageName(),0).versionCode + "");

                                return adRequest;
                            }
                        })

                        .map(new Function<AdRequest, AdRequest>() {
                            @Override
                            public AdRequest apply(AdRequest adRequest) throws Exception {

                                ScreenInfo screenInfo = getDeviceScreenInfo(mActivity.getApplicationContext());

                                adRequest.getDevice().setBrand(getDeviceBrand());
                                adRequest.getDevice().setCarrier(getCarrierName(mActivity.getApplicationContext()));
                                adRequest.getDevice().setID(getIMEI(mActivity.getApplicationContext()));
                                adRequest.getDevice().setModel(getDeviceModel());
                                adRequest.getDevice().setOs("Android");
                                adRequest.getDevice().setOsVersion(getAndroidVersion());
                                adRequest.getDevice().setPxRatio(screenInfo.getResolution());
                                adRequest.getDevice().setScreenHeight(screenInfo.getHeight());
                                adRequest.getDevice().setScreenWidth(screenInfo.getWidth());
                                adRequest.getDevice().setType(getDeviceType(mActivity.getApplicationContext()));

                                return adRequest;
                            }
                        })

                        .map(new Function<AdRequest, AdRequest>() {
                            @Override
                            public AdRequest apply(AdRequest adRequest) throws Exception {

                                Location location = getLocation();

                                if (null != location){

                                    BigDecimal bigDecimal;

                                    adRequest.getGeo().setGPSEnable(true);
                                    bigDecimal   =   new   BigDecimal(location.getLatitude());
                                    adRequest.getGeo().setLatitude( bigDecimal.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue());
                                    bigDecimal   =   new   BigDecimal(location.getLongitude());
                                    adRequest.getGeo().setLongitude( bigDecimal.setScale(4,   BigDecimal.ROUND_HALF_UP).doubleValue());
                                } else {
                                    adRequest.getGeo().setGPSEnable(false);
                                }

                                adRequest.getGeo().setIP(getIP(mActivity.getApplicationContext()));

                                return adRequest;
                            }
                        })

                        .map(new Function<AdRequest, String>() {
                            @Override
                            public String apply(AdRequest adRequest) throws Exception {


                                String result = JSON.toJSONString(adRequest);

                                return result;
                            }
                        })
                        .map(new Function<String, String>() {

                            @Override
                            public String apply(String request) throws Exception {

                                return HttpTool.post(mActivity.getApplicationContext(), AD_URL, request);

                            }
                        })
                        .subscribeOn(Schedulers.io()
                        )

                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeWith(new DisposableObserver<String>() {
                            @Override
                            public void onNext(String result) {

                                try {

                                    AdResponse adResponse = JSON.parseObject(result, AdResponse.class);

                                    adaptScreen(adResponse.getAd().getWidth(), adResponse.getAd().getHeight());


                                    windowManager.addView(frameLayout, wmParams);
//                                    addView(webView);

                                    webView.getSettings().setJavaScriptEnabled(true);
                                    PYWebViewClient.openOnOutWebView = true;

                                    // 加载缓存策略

                                    if (isNetworkAvailable(webView.getContext().getApplicationContext()))
                                        webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

                                    else
                                        webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);


                                    Log.d("pypytest", adResponse.getAd().getAdm());

                                    if (adResponse.getAd().getAdmType().equals(Ad.AdmTypeHTML))
                                        webView.loadData(adResponse.getAd().getAdm(), mimeType, encoding);

                                    else if(adResponse.getAd().getAdmType().equals(Ad.AdmTypeDynamic))
                                        webView.loadUrl(adResponse.getAd().getAdm());


                                    PYWebViewClient.openOnOutWebView = true;
                                } catch (Exception e){
                                    Log.d("pytest", e.toString());
                                }

                            }

                            @Override
                            public void onError(Throwable t) {

                                Log.d("pytest", t.toString());

                            }

                            @Override
                            public void onComplete() {

                            }
                        }));
    }

    private void destroyWebView() {


        if (webView != null) {

            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();
//            webView.clearCache(true);
            webView.loadUrl("about:blank"); // clearVie() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
//            webView.freeMemory();
            webView.pauseTimers();
            webView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }

    }


    // get banner Layout properties
//    private void getLayoutProperties(AttributeSet attrs) {
//
//        placementID = attrs.getAttributeValue(VIEW_XMLNS, PLACEMENT_ID);
//
//        String layout_width = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width");
//        String layout_height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");
//
//        isNeedAdapt = layout_height.equals("" + MarginLayoutParams.WRAP_CONTENT);
//        Log.d("pytest", "isNeedAdapt " + isNeedAdapt);
//    }

    // banner size adapt screen
    private void adaptScreen(int realAdWidth, int realAdHeight) {

//        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) this.getLayoutParams();
//        linearParams.height = realAdHeight;
//        linearParams.height = realAdWidth;
//
//        this.setLayoutParams(linearParams);

        // 设置webView 尺寸属性
//        webView.setLayoutParams(new RelativeLayout.LayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1)));

        wmParams.width = realAdWidth + border;
        wmParams.height = realAdHeight + border;

    }

}
