package com.ipinyou.ads;


import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.location.Location;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
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
import java.util.Timer;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

import static com.ipinyou.ads.properties.HttpProperties.AD_URL;
import static com.ipinyou.ads.properties.ViewProperties.PLACEMENT_ID;
import static com.ipinyou.ads.properties.ViewProperties.VIEW_XMLNS;
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
import static com.ipinyou.ads.tools.ScreenTools.dip2px;


public class IpinyouBanner extends FrameLayout {


    public void setPYOnTouchListener(OnTouchListener onTouchListener){

        webView.setPYOnTouchListener(onTouchListener);
    }

    public void removePYOnTouchListener(){
        webView.removePYOnTouchListener();
    }


    private Timer timer;

    private boolean isLoadAD = false;

    private int ad_width, ad_height;

    private String placementID;
    private long lastRefreshTime = 0L;
    private long refreshTime = 10000;

    private boolean isNeedAdapt = false;

    private ADWebView webView;

    private Context myContext;

    private final String mimeType = "text/html";
    private final String encoding = "utf-8";
    private final CompositeDisposable disposables = new CompositeDisposable();


    public IpinyouBanner(Context context, AttributeSet attrs) {

        super(context, attrs);

        myContext = context;

        getLayoutProperties(attrs);

        webView = new ADWebView(myContext, true, true);
        webView.setBackgroundColor(Color.TRANSPARENT);
    }

    public void loadAD() {

        isLoadAD = true;
    }

    @Override
    protected void onDetachedFromWindow() {

        if (null != timer) {

            timer.cancel();
        }

        destroyWebView();

        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();

        webView.setLayoutParams(new RelativeLayout.LayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1)));

        addView(webView);


        if (null == timer) {

            timer = new Timer(true);

            timer.schedule(new java.util.TimerTask() {

                @Override
                public void run() {


                    if (!isLoadAD || System.currentTimeMillis() - lastRefreshTime < refreshTime) {

                        return;
                    }

                    lastRefreshTime = System.currentTimeMillis();

                    if (IpinyouBanner.this.isShown()) {

                        // 请求广告
                        getAD();
                    }

                }

            }, 0L, 300L);

        }



    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        adaptScreen();
    }

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
                                if(StringUtils.isNotEmpty(placementID)){
                                    adRequest.setAdSlotID(Long.parseLong(placementID));
                                }

                                SharedPreferences sharedPreferences = myContext.getSharedPreferences("ipinyou", Context.MODE_APPEND);
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

                                SharedPreferences sharedPreferences = myContext.getSharedPreferences("ipinyou", Context.MODE_APPEND);

                                adRequest.getApp().setID(Double.parseDouble(sharedPreferences.getString(ViewProperties.APP_ID, "0")));
                                adRequest.getApp().setBundle(myContext.getPackageName());
                                adRequest.getApp().setVersion(myContext.getPackageManager().getPackageInfo(myContext.getPackageName(),0).versionCode + "");

                                return adRequest;
                            }
                        })

                        .map(new Function<AdRequest, AdRequest>() {
                            @Override
                            public AdRequest apply(AdRequest adRequest) throws Exception {

                                ScreenInfo screenInfo = getDeviceScreenInfo(myContext);

                                adRequest.getDevice().setBrand(getDeviceBrand());
                                adRequest.getDevice().setCarrier(getCarrierName(myContext));
                                adRequest.getDevice().setID(getIMEI(myContext));
                                adRequest.getDevice().setModel(getDeviceModel());
                                adRequest.getDevice().setOs("Android");
                                adRequest.getDevice().setOsVersion(getAndroidVersion());
                                adRequest.getDevice().setPxRatio(screenInfo.getResolution());
                                adRequest.getDevice().setScreenHeight(screenInfo.getHeight());
                                adRequest.getDevice().setScreenWidth(screenInfo.getWidth());
                                adRequest.getDevice().setType(getDeviceType(myContext));

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

                                adRequest.getGeo().setIP(getIP(myContext));

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

                                return HttpTool.post(IpinyouBanner.this.getContext().getApplicationContext(), AD_URL, request);

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
                                    webView.getSettings().setJavaScriptEnabled(true);
                                    PYWebViewClient.openOnOutWebView = true;

                                    // 加载缓存

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

    public void destroyWebView() {


        if (webView != null) {

            webView.getSettings().setJavaScriptEnabled(false);
            webView.clearHistory();

            webView.loadUrl("about:blank"); // clearVie() should be changed to loadUrl("about:blank"), since clearView() is deprecated now
            webView.freeMemory();
            webView.pauseTimers();
            webView = null; // Note that mWebView.destroy() and mWebView = null do the exact same thing
        }

        this.removeView(webView);
    }


    // get banner Layout properties
    private void getLayoutProperties(AttributeSet attrs) {

        placementID = attrs.getAttributeValue(VIEW_XMLNS, PLACEMENT_ID);

        String layout_width = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width");
        String layout_height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");

        isNeedAdapt = layout_height.equals("" + MarginLayoutParams.WRAP_CONTENT);

        Log.d("pytest", "isNeedAdapt " + isNeedAdapt);
    }

    // banner size adapt screen
    private void adaptScreen() {


//        int dip_width = px2dip(this.getContext(), this.getWidth());
//        int dip_height = px2dip(this.getContext(), this.getHeight());
//        ad_width = dip_width;

        ad_width = this.getWidth();

        if (!isNeedAdapt) {

//            ad_height = dip_height;
            ad_height = this.getHeight();
            return;
        }

        ad_height = getBestADHeight(ad_width);

        LinearLayout.LayoutParams linearParams = (LinearLayout.LayoutParams) this.getLayoutParams();
        linearParams.height = dip2px(this.getContext(), ad_height);
        this.setLayoutParams(linearParams);
    }

    private int getBestADHeight(int ad_width) {

        return ad_width > 468 ? 90 : 50;
    }

}
