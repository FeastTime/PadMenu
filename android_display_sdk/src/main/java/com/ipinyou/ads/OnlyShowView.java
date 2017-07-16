package com.ipinyou.ads;


import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ipinyou.ads.webView.ADWebView;
import com.ipinyou.ads.webView.PYWebViewClient;

import static com.ipinyou.ads.tools.HttpTool.isNetworkAvailable;
import static com.ipinyou.ads.tools.ScreenTools.dip2px;


public class OnlyShowView extends FrameLayout {


    private int ad_width, ad_height;

    private boolean isNeedAdapt = false;

    private ADWebView webView;

    private Context myContext;
    private String adUrl = "";

    private final String mimeType = "text/html";
    private final String encoding = "utf-8";



    public OnlyShowView(Context context, AttributeSet attrs) {

        super(context, attrs);

        myContext = context;

        getLayoutProperties(attrs);

        webView = new ADWebView(myContext, true, true);
        webView.setBackgroundColor(Color.TRANSPARENT);
    }

    public void loadAD(String adUrl) {

        showAD(adUrl);
    }

    @Override
    protected void onDetachedFromWindow() {

        destroyWebView();

        super.onDetachedFromWindow();
    }

    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();

        // 请求广告
//        showAD(adUrl);

        webView.setLayoutParams(new RelativeLayout.LayoutParams(new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, 1)));

        addView(webView);
    }


    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        adaptScreen();
    }

    private void showAD(String adUrl) {


        try {

            webView.getSettings().setJavaScriptEnabled(true);
            PYWebViewClient.openOnOutWebView = false;

            // 加载缓存

            if (isNetworkAvailable(webView.getContext().getApplicationContext()))
                webView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);

            else
                webView.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);

            Log.d("pypytest", adUrl);


            webView.loadUrl(adUrl);


            PYWebViewClient.openOnOutWebView = true;
        } catch (Exception e){
            Log.d("pytest", e.toString());
        }

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

//        placementID = attrs.getAttributeValue(VIEW_XMLNS, PLACEMENT_ID);

        String layout_width = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_width");
        String layout_height = attrs.getAttributeValue("http://schemas.android.com/apk/res/android", "layout_height");

        isNeedAdapt = layout_height.equals("" + MarginLayoutParams.WRAP_CONTENT);

        Log.d("pytest", "isNeedAdapt " + isNeedAdapt);
    }

    // banner size adapt screen
    private void adaptScreen() {


        ad_width = this.getWidth();

        if (!isNeedAdapt) {

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
