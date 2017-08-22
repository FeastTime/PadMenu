package com.feasttime.view;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.feasttime.menu.R;
import com.feasttime.model.bean.SilentAd;
import com.feasttime.presenter.selentad.SilentAdContract;
import com.feasttime.presenter.selentad.SilentPresenter;
import com.ipinyou.ads.OnlyShowView;
import com.ipinyou.ads.bean.Ad;
import com.r0adkll.slidr.Slidr;
import com.r0adkll.slidr.model.SlidrConfig;
import com.r0adkll.slidr.model.SlidrPosition;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SilentADActivity extends AppCompatActivity implements SilentAdContract.ISilentView{

    Timer timer;
    OnlyShowView adview;
    int adIndex = 0;

    static boolean isShow = false;

    private SilentPresenter silentPresenter = new SilentPresenter();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);//remove title bar  即隐藏标题栏
        this.getSupportActionBar().hide();// 隐藏ActionBar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);//remove notification bar  即全屏

        setContentView(R.layout.activity_silent_ad);

        silentPresenter.init(this);

        // 初始化广告
        initAD();

        // 初始化滑动组件
        initSlidr();

        hideOperateButton();


    }

    void hideOperateButton(){
        //for new api versions.
        View decorView = getWindow().getDecorView();
        int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
    }

    void initAD(){
        adview = (OnlyShowView)findViewById(R.id.bannerAD);
    }

    @Override
    protected void onResume() {
        super.onResume();

        isShow = true;
        silentPresenter.getSilentADUrl(1920, 1200, 4, "html");

    }

    @Override
    protected void onPause() {
        super.onPause();

        isShow = false;

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

    @Override
    public void getSilentADUrlComplete(SilentAd silentAd) {

        if (!isShow)
            return;

//        Log.d("test", silentAd.getID());

        final List<Ad> ads = silentAd.getAds();

//        for (Ad ad: ads) {
//            Log.d("test", ad.getAdm());
//            Log.d("test", ad.getAdmType());
//        }

        // 刷新上菜进度的定时器
        timer = new Timer(true);

        TimerTask task = new TimerTask() {

            public void run() {

                if (!isShow)
                    timer.cancel();

                SilentADActivity.this.runOnUiThread(new Runnable() {

                    @Override
                    public void run() {

                        adview.loadAD(ads.get(adIndex).getAdm());
                        hideOperateButton();
                    }

                });

                adIndex++;

                if (adIndex == ads.size())
                    adIndex = 0;
            }
        };

        timer.schedule(task, 0, 10000);
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void hideLoading() {

    }

    @Override
    public void showNetError() {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    public void showTransparentCoverView() {

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {


        if (keyCode == KeyEvent.KEYCODE_HOME) {
            Log.d("test", "++++KEYCODE_HOME");

        } else if (keyCode == KeyEvent.KEYCODE_BACK){
            Log.d("test", "++++KEYCODE_BACK");
        } else if (keyCode == KeyEvent.KEYCODE_MENU){
            Log.d("test", "++++KEYCODE_MENU");
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_DOWN){
            Log.d("test", "++++KEYCODE_VOLUME_DOWN");
        } else if (keyCode == KeyEvent.KEYCODE_VOLUME_UP){
            Log.d("test", "++++KEYCODE_VOLUME_UP");
        } else if (keyCode == KeyEvent.KEYCODE_POWER){
            Log.d("test", "++++KEYCODE_POWER");
        }


        return 1==1;

//        return super.onKeyDown(keyCode, event);
    }




}
