package com.feasttime.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.feasttime.menu.R;

import com.ipinyou.ads.IpinyouBanner;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

public class SilentADActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        this.getSupportActionBar().hide();// 隐藏ActionBar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar  即全屏

        setContentView(R.layout.activity_silent_ad);

        // 初始化广告
        initAD();

        // 初始化滑动组件
        initSlidr();

    }

    void initAD(){
        IpinyouBanner ipinyouBanner = (IpinyouBanner)findViewById(R.id.bannerAD);
        ipinyouBanner.loadAD();
    }

    void initSlidr(){

        SlidrConfig config = new SlidrConfig.Builder()
                .primaryColor(getResources().getColor(R.color.colorPrimary))
                .secondaryColor(getResources().getColor(R.color.colorPrimaryDark))
                .position(SlidrPosition.BOTTOM)
                .sensitivity(1f)
                .scrimColor(Color.BLACK)
                .scrimStartAlpha(0.8f)
                .scrimEndAlpha(0f)
                .velocityThreshold(5)
                .distanceThreshold(0.1f)
                .edge(false)
                .edgeSize(0.18f) // The % of the screen that counts as the edge, default 18%

                .build();

        Slidr.attach(this, config);
    }
}
