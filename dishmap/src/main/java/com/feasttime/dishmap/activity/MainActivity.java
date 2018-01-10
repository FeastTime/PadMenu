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
                topImageRelMaxFinalHeight = (int)MainActivity.this.getResources().getDimension(R.dimen.y960);
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


    //动画绘制部分====================================

    private int topImageRelFinalHeight = 0;
    private int topImageRelMaxFinalHeight = 0;
    private int topImageRelOnCreateHeight = 0;

    private int btmMenuRelFinalHeight = 0;
    private int btmMenuRelOnCreateHeight = 0;

    private int touchDownYPosition = 0;

    private ValueAnimator mAnimator;
    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (mAnimator == null || !mAnimator.isRunning()) {
            handleTouchEvent(event);
        }

        return super.onTouchEvent(event);
    }

    //处理动画逻辑
    private void handleTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            touchDownYPosition = (int)event.getY();
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            int topImageHeight = topImgWrapRel.getHeight();

            int currentY = (int)event.getY();

            int distance = currentY - touchDownYPosition;

            if (Math.abs(distance) > 20) {
                if (distance > 0) {
                    //动画移动到底部
                    startAnimator(topImageHeight,topImageRelMaxFinalHeight,1);
                } else {
                    //动画移动到顶部
                    startAnimator(topImageHeight,topImageRelOnCreateHeight,0);
                }
            }


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

            if (topHeight > topImageRelMaxFinalHeight) {
                topHeight = topImageRelMaxFinalHeight;
            }

            if (topHeight < topImageRelOnCreateHeight) {
                topHeight = topImageRelOnCreateHeight;
            }

            LogUtil.d(TAG,"the top height:" + topHeight);
            topParams.height = topHeight;
            topImgWrapRel.setLayoutParams(topParams);

            ViewGroup.LayoutParams btmParams = btmMenuWrapRel.getLayoutParams();
            int btmHeight = btmMenuRelFinalHeight - distance;
            if (btmHeight < 0) {
                btmHeight = 0;
            }

            if (btmHeight > btmMenuRelOnCreateHeight) {
                btmHeight = btmMenuRelOnCreateHeight;
            }

            btmParams.height = btmHeight;
            LogUtil.d(TAG,"the btm height:" + btmParams.height);
            btmMenuWrapRel.setLayoutParams(btmParams);
        }
    }

    //开始动画
    private void startAnimator(final int startY, final int endY, final int direction) {
         LogUtil.d(TAG,"the start x:" + startY + "-" + endY + "-" + direction);
         mAnimator = ValueAnimator.ofInt(startY, endY);
         mAnimator.setDuration(150);
         mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
             public void onAnimationUpdate(ValueAnimator animation) {
                Integer value = (Integer) animation.getAnimatedValue();
                LogUtil.d(TAG,"the animator height:" + value);

                //设置顶部图动画
                ViewGroup.LayoutParams topParams = topImgWrapRel.getLayoutParams();
                topParams.height = value;
                topImgWrapRel.setLayoutParams(topParams);

                //设置底部菜单动画
                ViewGroup.LayoutParams btmParams = btmMenuWrapRel.getLayoutParams();
                int distance = Math.abs(value - startY);
                int btmMenuHeight = btmMenuWrapRel.getHeight();

                if (direction == 0) {
                    //向上
                    btmParams.height = btmMenuHeight + distance;
                } else {
                    //向下
                    btmParams.height = btmMenuHeight - distance;
                }

                if (btmParams.height < 0) {
                    btmParams.height = 0;
                }

                if (btmParams.height > btmMenuRelOnCreateHeight) {
                    btmParams.height = btmMenuRelOnCreateHeight;
                }
                btmMenuWrapRel.setLayoutParams(btmParams);

                //动画结束
                if (value == endY) {
                     //动画结束
                     topImageRelFinalHeight = topImgWrapRel.getHeight();
                     btmMenuRelFinalHeight = btmMenuWrapRel.getHeight();
                }
            }
         });
        mAnimator.start();
     }

    //============================================================
}
