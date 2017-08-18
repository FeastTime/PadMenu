/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.view;

import android.annotation.TargetApi;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.danikula.videocache.HttpProxyCacheServer;
import com.feasttime.MenuApplication;
import com.feasttime.menu.R;
import com.feasttime.presenter.IBasePresenter;
import com.feasttime.tools.LogUtil;

import butterknife.Bind;

@TargetApi(17)
public class PlayVideoActivity extends BaseActivity {
    @Bind(R.id.play_video_activity_vv)
    VideoView videoView;

    @Bind(R.id.play_video_activity_loading_pb)
    ProgressBar loadingPb;

    @Override
    protected IBasePresenter[] getPresenters() {
        return new IBasePresenter[0];
    }

    @Override
    protected void onInitPresenters() {

    }

    @Override
    protected int getLayoutResId() {
        return R.layout.play_video_activity;
    }


    @Override
    protected void initViews() {

//        videoView.setMediaController(new MediaController(this));

        String url = this.getIntent().getStringExtra("url");

        if (!TextUtils.isEmpty(url)) {

            HttpProxyCacheServer proxy = MenuApplication.getProxy(this);
            String proxyUrl = proxy.getProxyUrl(url);

            videoView.setVideoPath(proxyUrl);
            videoView.start();
            videoView.requestFocus();
            showProgress();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showProgress() {
        videoView.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                LogUtil.d("result","the status:" + what);
                if(what == MediaPlayer.MEDIA_INFO_BUFFERING_START){
                    loadingPb.setVisibility(View.VISIBLE);
                } else if(what == MediaPlayer.MEDIA_INFO_BUFFERING_END){
                    //此接口每次回调完START就回调END,若不加上判断就会出现缓冲图标一闪一闪的卡顿现象
                    if(mp.isPlaying()){
                        loadingPb.setVisibility(View.GONE);
                    }
                } else if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    loadingPb.setVisibility(View.GONE);
                }
                return true;
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

}
