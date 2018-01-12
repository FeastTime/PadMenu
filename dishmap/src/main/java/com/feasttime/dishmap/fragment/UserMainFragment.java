package com.feasttime.dishmap.fragment;

import android.animation.ValueAnimator;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.feasttime.dishmap.R;
import com.feasttime.dishmap.utils.LogUtil;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by chen on 2018/1/10.
 */

public class UserMainFragment extends Fragment{
    private static final String TAG = "UserMainFragment";

    @Bind(R.id.fragment_user_main_top_img_rel)
    RelativeLayout topImgWrapRel;

    @Bind(R.id.fragment_user_main_scan_items_ll)
    LinearLayout scanItemsLL;

    @Bind(R.id.fragment_user_main_slide_down_iv)
    ImageView slideDownIv;


    RelativeLayout btmMenuWrapRel;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_user_main, container, false);
        ButterKnife.bind(this,view);
        initViews();
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    private void initViews() {
        btmMenuWrapRel = (RelativeLayout)this.getActivity().findViewById(R.id.activity_main_btm_menu_rel);

        //设置距顶边距为负值
        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams)scanItemsLL.getLayoutParams();
        int marginTop = (int)this.getResources().getDimension(R.dimen.y175) * -1;
        params.setMargins(0,marginTop,0,0);
        scanItemsLL.setLayoutParams(params);

        topImgWrapRel.post(new Runnable() {
            @Override
            public void run() {
                topImageRelOnCreateHeight = topImgWrapRel.getHeight();
                topImageRelFinalHeight = topImageRelOnCreateHeight;
                topImageRelMaxFinalHeight = (int)UserMainFragment.this.getResources().getDimension(R.dimen.y960);
                alphaValue = 255f / Math.abs(topImageRelOnCreateHeight - topImageRelMaxFinalHeight);
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

    private float alphaValue = 0;

    private ValueAnimator mAnimator;


    //处理动画逻辑
    public void handleTouchEvent(MotionEvent event) {
        if (mAnimator == null || !mAnimator.isRunning()) {

        } else {
            return;
        }

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
            } else {
                //如果小于20的距离，那么就返回原来的状态
                if (distance > 0) {
                    //动画移动到顶部
                    startAnimator(topImageHeight,topImageRelOnCreateHeight,0);
                } else {
                    //动画移动到底部
                    startAnimator(topImageHeight,topImageRelMaxFinalHeight,1);
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
            setScanItemsAlpha(topHeight);
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
                    scanItemsLL.getBackground().setAlpha(200);
                } else {
                    //向下
                    btmParams.height = btmMenuHeight - distance;
                    scanItemsLL.getBackground().setAlpha(50);
                }

                if (btmParams.height < 0) {
                    btmParams.height = 0;
                }

                if (btmParams.height > btmMenuRelOnCreateHeight) {
                    btmParams.height = btmMenuRelOnCreateHeight;
                }
                btmMenuWrapRel.setLayoutParams(btmParams);

                setScanItemsAlpha(value);

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


    //设置扫描部分的透明值
    private void setScanItemsAlpha(int currY) {

        LogUtil.d(TAG,"setScanItemsAlpha  currY:" + currY);
        if (currY == topImageRelOnCreateHeight) {
            scanItemsLL.getBackground().setAlpha(255);
            slideDownIv.setAlpha(255);
        } else if (currY == topImageRelMaxFinalHeight) {
            scanItemsLL.getBackground().setAlpha(0);
            slideDownIv.setAlpha(0);
        } else {
            //根据距离设置扫描部分的透明度
            int distance = topImageRelMaxFinalHeight - topImageRelOnCreateHeight;
            int currDistance = (currY - topImageRelOnCreateHeight);
            int finalAlpha = (int)((distance - currDistance) * alphaValue);
            LogUtil.d(TAG,"setScanItemsAlpha  finalAlpha:" + finalAlpha);
            LogUtil.d(TAG,"setScanItemsAlpha  finalAlpha detail:" + distance + "-" + currDistance + "-" + topImageRelOnCreateHeight + "-" + alphaValue);
            scanItemsLL.getBackground().setAlpha(finalAlpha);
            slideDownIv.setAlpha(finalAlpha);
        }
    }

    //============================================================


    @Override
    public void onDestroy() {
        super.onDestroy();
        btmMenuWrapRel = null;
        topImgWrapRel = null;
        mAnimator = null;
    }
}
