package com.feasttime.tools;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

import com.feasttime.menu.R;
import com.feasttime.model.bean.MenuItemInfo;
import com.feasttime.view.MainActivity;

import java.net.URLDecoder;

/**
 * Created by Administrator on 2017/7/16.
 */

public class UtilTools {
    public static String decodeStr(String str) {
        try {
            String result = URLDecoder.decode(str,"UTF-8");
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }


    public static void addOneDishes(final Activity context, int startLocation[], int toLocation[],final MenuItemInfo menuItemInfo) {
        TranslateAnimation translateAnimation = new TranslateAnimation(startLocation[0], toLocation[0], startLocation[1], toLocation[1]);
        //设置动画效果
        translateAnimation.setDuration(1000);
        final ImageView redCycleIv = new ImageView(context);
        redCycleIv.setImageResource(R.drawable.red_cycle_shape);
        redCycleIv.setBackgroundResource(R.drawable.red_cycle_shape);
        redCycleIv.setAnimation(translateAnimation);
        ((Activity)context).addContentView(redCycleIv,new ViewGroup.LayoutParams(20,20));
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                ViewGroup parentVg = (ViewGroup) redCycleIv.getParent();
                parentVg.removeView(redCycleIv);
                ((MainActivity)context).refreshCartNum(menuItemInfo);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        //启动动画
        translateAnimation.start();
    }
}
