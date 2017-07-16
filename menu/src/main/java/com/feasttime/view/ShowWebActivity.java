/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.view;

import android.os.Bundle;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.feasttime.menu.R;
import com.feasttime.presenter.IBasePresenter;

import butterknife.Bind;

public class ShowWebActivity extends BaseActivity {
    @Bind(R.id.show_web_activity_wv)
    WebView webViewWv;

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.show_web_activity;
    }

    @Override
    protected void initViews() {

        WebSettings webSettings = webViewWv.getSettings();
        //设置WebView属性，能够执行Javascript脚本
        webSettings.setJavaScriptEnabled(true);
        //设置可以访问文件
        webSettings.setAllowFileAccess(true);
        //设置支持缩放
        webSettings.setBuiltInZoomControls(true);
        //加载需要显示的网页
        webViewWv.loadUrl("http://www.baidu.com");
        //设置Web视图
        webViewWv.setWebViewClient(new webViewClient ());


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    //Web视图
    private class webViewClient extends WebViewClient {
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }
}
