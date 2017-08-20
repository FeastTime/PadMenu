package com.ipinyou.ads.webView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.net.http.SslError;
import android.webkit.SslErrorHandler;
import android.webkit.WebView;
import android.webkit.WebViewClient;


public class PYWebViewClient extends WebViewClient {

    public static boolean openOnOutWebView = false;
    public static boolean outOpenResult = false;
    public static boolean innerOpenResult = false;

    @Override
    public void onReceivedSslError(WebView paramWebView, SslErrorHandler paramSslErrorHandler, SslError paramSslError)
    {
        paramSslErrorHandler.proceed();
    }

    @Override
    public boolean shouldOverrideUrlLoading(WebView webView, String url)
    {

        if (openOnOutWebView){

            if (url.startsWith("http") )
                outOpenResult = openWithBrowser(webView.getContext(), url);

            else
                outOpenResult = openWithSchema(webView.getContext(), url);

            // 反馈result;

        } else {

            innerOpenResult = openWithWebView(webView, url);
            // 反馈result;
        }

        return true;
    }

    private boolean openWithWebView(WebView webView, String url) {

        try {

            webView.loadUrl(url);

        } catch (Exception e) {

            e.printStackTrace();
            return false;
        }

        return true;
    }
    private boolean openWithBrowser(Context context, String url){

        try {

            Uri uri = Uri.parse(url); //url为你要链接的地址

            Intent intent = new Intent(Intent.ACTION_VIEW, uri);

            context.startActivity(intent);

        } catch (Exception e){

            e.printStackTrace();
            return false;
        }

        return true;
    }

    private boolean openWithSchema(Context context, String url){

        try {

            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            context.startActivity(intent);

        } catch (Exception e) {

            // 防止没有安装APP的情况
            e.printStackTrace();
            return false;
        }

        return true;
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        super.onPageFinished(view, url);
        PYWebViewClient.openOnOutWebView = true;
    }
}

