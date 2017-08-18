package com.feasttime.view;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.feasttime.menu.R;
import com.feasttime.widget.gifhelper.AlxGifHelper;
import com.pnikosis.materialishprogress.ProgressWheel;

import butterknife.ButterKnife;
import pl.droidsonroids.gif.GifImageView;

/**
 * 只是为了测试
 * Created by chen on 2017/7/22.
 */

public class TestActivity extends Activity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test_activity);
        ButterKnife.bind(this);

        View gifGroup1 = findViewById(R.id.gif_group_2);
        AlxGifHelper.displayImage("http://img4.duitang.com/uploads/blog/201310/26/20131026161531_WJfQN.gif",
                (GifImageView) gifGroup1.findViewById(R.id.gif_photo_view_giv),
                (ProgressWheel) gifGroup1.findViewById(R.id.gif_progress_wheel),
                (TextView) gifGroup1.findViewById(R.id.git_progress_tv),
                500
        );
    }
}
