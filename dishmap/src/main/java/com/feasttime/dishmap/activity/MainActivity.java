package com.feasttime.dishmap.activity;

import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;


public class MainActivity extends BaseActivity{
    private static final String TAG = "MainActivity";

    @Bind(R.id.activity_main_top_img_rel)
    RelativeLayout topImgWrapRel;

    @Bind(R.id.activity_main_btm_menu_rel)
    RelativeLayout btmMenuWrapRel;

    private int topImageRelFinalHeight = 0;
    private int btmMenuRelFinalHeight = 0;

    private int topImageRelOnCreateHeight = 0;
    private int btmMenuRelOnCreateHeight = 0;

    private int touchDownYPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        topImgWrapRel.post(new Runnable() {
            @Override
            public void run() {
                topImageRelOnCreateHeight = topImgWrapRel.getHeight();
                topImageRelFinalHeight = topImageRelOnCreateHeight;
            }
        });

        btmMenuWrapRel.post(new Runnable() {
            @Override
            public void run() {
                btmMenuRelOnCreateHeight = btmMenuWrapRel.getHeight();
                btmMenuRelFinalHeight = btmMenuRelOnCreateHeight;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchDownYPosition = (int)event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {

            int tempY = (int)event.getY();
//            startAnimation(tempX,tempY,600,600);

            startAnimator(topImgWrapRel.getHeight(),topImageRelOnCreateHeight);
        } else {
            int currY = (int)event.getY();
            LogUtil.d("result","the current pos:" + currY);

            int distance = (int)event.getY() - touchDownYPosition;

            //设置控件高度
            ViewGroup.LayoutParams topParams = topImgWrapRel.getLayoutParams();
            int topHeight = topImageRelFinalHeight + distance;
            if (topHeight < 0) {
                topHeight = 0;
            }

            topParams.height = topHeight;
            topImgWrapRel.setLayoutParams(topParams);

            ViewGroup.LayoutParams btmParams = btmMenuWrapRel.getLayoutParams();
            int btmHeight = btmMenuRelFinalHeight - distance;
            if (btmHeight < 0) {
                btmHeight = 0;
            }

            btmParams.height = btmHeight;
            LogUtil.d(TAG,"the btm height:" + btmParams.height);
            btmMenuWrapRel.setLayoutParams(btmParams);
        }
        return super.onTouchEvent(event);
    }

    private void startAnimator(int startY, final int endY) {
        LogUtil.d(TAG,"the start x:" + startY + "-" + endY);
         final ValueAnimator animator = ValueAnimator.ofInt(startY, endY);
         animator.setDuration(150);
         animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
             public void onAnimationUpdate(ValueAnimator animation) {
                 Integer value = (Integer) animation.getAnimatedValue();
                LogUtil.d(TAG,"the animator height:" + value);

                //设置控件高度
                ViewGroup.LayoutParams topParams = topImgWrapRel.getLayoutParams();
                topParams.height = value;
                topImgWrapRel.setLayoutParams(topParams);

                ViewGroup.LayoutParams btmParams = btmMenuWrapRel.getLayoutParams();
                btmParams.height = value;

                btmMenuWrapRel.setLayoutParams(btmParams);

                if (value == endY) {
                     //动画结束
                     topImageRelFinalHeight = topImgWrapRel.getHeight();
                     btmMenuRelFinalHeight = btmMenuWrapRel.getHeight();
                }
            }
         });
        animator.start();
     }
}
