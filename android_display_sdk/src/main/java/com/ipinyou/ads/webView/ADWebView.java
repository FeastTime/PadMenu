package com.ipinyou.ads.webView;

import android.content.Context;
import android.os.Build;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;


public class ADWebView extends JSSafetyWebView {

    private OnTouchListener outOnTouchListener;

    public void setPYOnTouchListener(OnTouchListener onTouchListener){

        this.outOnTouchListener = onTouchListener;
    }

    public void removePYOnTouchListener(){
        this.outOnTouchListener = null;
    }

    public ADWebView(Context context, boolean isSetBackgroundColor, boolean focusable) {
        super(context);

        setClickable(true);

        setWebViewClient(new PYWebViewClient());
        setWebChromeClient(new WebChromeClient());

        setFocusable(false);

        if (isSetBackgroundColor)

            setBackgroundColor(0);

        if (focusable)

            setFocusable(true);


        setScrollBarStyle(WebView.SCROLLBARS_INSIDE_OVERLAY);


        WebSettings webSettings = getSettings();

        // 设置启动缓存
        webSettings.setAppCacheEnabled(true);
        webSettings.setDatabaseEnabled(true);
        webSettings.setDomStorageEnabled(true);

        String appCacheDir = this.getContext().getApplicationContext().getDir("cache", Context.MODE_PRIVATE).getPath();
        webSettings.setAppCachePath(appCacheDir);


        webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        //支持通过js打开新的窗口
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        // 这样阻止焦点画那个长方形
        webSettings.setNeedInitialFocus(false);
        // 允许访问文件
        webSettings.setAllowFileAccess(true);
        // 设置编码
        webSettings.setDefaultTextEncodingName("utf-8");


        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);


        webSettings.setBlockNetworkImage(false);//去掉拦截图片的加载
        webSettings.setLoadsImagesAutomatically(true);


//        this.setInitialScale(25);

        //自适应屏幕(方法二)
        //适应内容大小
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NARROW_COLUMNS);
        webSettings.setLoadWithOverviewMode(true);

        // 支持缩放
        webSettings.setSupportZoom(true);
        //扩大比例的缩放
        webSettings.setUseWideViewPort(true);
        // 支持缩放控制
        webSettings.setBuiltInZoomControls(false);
        // 显示缩放按钮
        webSettings.setDisplayZoomControls(false);

        this.setHorizontalScrollBarEnabled(false);//水平不显示
        this.setVerticalScrollBarEnabled(false); //垂直不显示


//        this.setScrollContainer(false);
//        this.setOverScrollMode(WebView.OVER_SCROLL_NEVER);
//

        this.setOnTouchListener(new OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {


//                Log.d("feast", "webview ontouch");
                if (null != outOnTouchListener){
                    outOnTouchListener.onTouch(v, event);
                }

                if (event.getAction() == MotionEvent.ACTION_MOVE)
                    return false;
                return false;
            }
        });


        context = context.getApplicationContext();

        try {

            WebSettings.class.getMethod("setDatabasePath", new Class[] { String.class }).invoke(webSettings, new Object[] { context });
            WebSettings.class.getMethod("setDomStorageEnabled", new Class[] { Boolean.TYPE }).invoke(webSettings, new Object[] { Boolean.valueOf(true) });
            WebSettings.class.getMethod("setDatabaseEnabled", new Class[] { Boolean.TYPE }).invoke(webSettings, new Object[] { Boolean.valueOf(true) });

            try {

                WebSettings.class.getMethod("setAppCacheEnabled", new Class[] { Boolean.TYPE }).invoke(webSettings, new Object[] { Boolean.valueOf(true) });
                WebSettings.class.getMethod("setAppCachePath", new Class[] { String.class }).invoke(webSettings, new Object[] { context });
                WebSettings.class.getMethod("setAppCacheMaxSize", new Class[] { Long.TYPE }).invoke(webSettings, new Object[] { Long.valueOf(5242880L) });

                try {

                    WebSettings.class.getMethod("setGeolocationEnabled", new Class[] { Boolean.TYPE }).invoke(webSettings, new Object[] { Boolean.valueOf(true) });
                    WebSettings.class.getMethod("setGeolocationDatabasePath", new Class[] { String.class }).invoke(webSettings, new Object[] { context });


                    return;
                } catch (Exception e) {

                }
            } catch (Exception localException1) {

            }
        } catch (Exception localException2) {

        }

    }


}
