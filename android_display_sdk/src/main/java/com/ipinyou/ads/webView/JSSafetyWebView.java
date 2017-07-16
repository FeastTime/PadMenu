package com.ipinyou.ads.webView;

import android.content.Context;
import android.os.Build;
import android.webkit.WebView;


public class JSSafetyWebView extends WebView {

    public JSSafetyWebView(Context context) {
        super(context);
        makeJSSafe();
    }


    private void makeJSSafe()
    {
        try
        {
            if (Integer.parseInt(Build.VERSION.SDK) >= Build.VERSION_CODES.HONEYCOMB) {
                Class.forName("android.webkit.WebView").getDeclaredMethod("removeJavascriptInterface", new Class[] { String.class }).invoke(this, new Object[] { "searchBoxJavaBridge_" });
            }
            return;
        }

        catch (Exception ignored) {}
    }

    @Override
    public boolean overScrollBy(int deltaX, int deltaY, int scrollX, int scrollY,
                                int scrollRangeX, int scrollRangeY, int maxOverScrollX,
                                int maxOverScrollY, boolean isTouchEvent) {
        return false;
    }

    @Override
    public void scrollTo(int x, int y){
        super.scrollTo(0,0);
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(oldl, oldt, oldl, oldt);
    }


}
