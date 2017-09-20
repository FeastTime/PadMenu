package com.feasttime.dishmap;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.MediaController;

import com.feasttime.dishmap.customview.MyVideoView;

import java.io.File;

import static com.feasttime.dishmap.R.id.videoView;

public class VideoActivity extends BaseActivity {


    com.feasttime.dishmap.customview.MyVideoView myyVideoView;
    MediaController mediaController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        // 设置全透明title
        super.setTitleBarFullTransparents();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        myyVideoView = (MyVideoView)findViewById(videoView);

        mediaController = new MediaController(this);

        File file = new File("");

        myyVideoView.setVideoURI(Uri.parse("android.resource://" + getPackageName() + "/raw/test"));


        mediaController.setMediaPlayer(myyVideoView);

        myyVideoView.requestFocus();



        myyVideoView.start();

        myyVideoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

            @Override
            public void onPrepared(MediaPlayer mp) {
                mp.start();
                mp.setLooping(true);

            }
        });

    }


}
