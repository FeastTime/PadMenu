package com.feasttime.dishmap.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.VideoView;

/**
 * Created by lixiaoqing on 2017/9/18.
 */

public class MyVideoView extends VideoView {


    public MyVideoView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public MyVideoView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {

        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if(widthMode == MeasureSpec.EXACTLY && heightMode == MeasureSpec.EXACTLY)

            setMeasuredDimension(widthSize,heightSize);

        else

            super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }
}
