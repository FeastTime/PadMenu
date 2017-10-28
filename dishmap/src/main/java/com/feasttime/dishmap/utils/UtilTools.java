package com.feasttime.dishmap.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by chen on 2017/9/20.
 */

public class UtilTools {
    public static int[] getImageWidthHeight(Context context, int resid){
        BitmapFactory.Options options = new BitmapFactory.Options();

        /**
         * 最关键在此，把options.inJustDecodeBounds = true;
         * 这里再decodeFile()，返回的bitmap为空，但此时调用options.outHeight时，已经包含了图片的高了
         */
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(context.getResources(),resid,options); // 此时返回的bitmap为null
        /**
         *options.outHeight为原始图片的高
         */
        return new int[]{options.outWidth,options.outHeight};
    }


    public static void chenageTextDrawableSize(TextView textView, int drawableId, int width,int height) {

        Drawable drawable =  textView.getContext().getResources().getDrawable(drawableId);
        drawable.setBounds(0, 0, width, height);
        textView.setCompoundDrawables(null,drawable, null, null);
    }

}
