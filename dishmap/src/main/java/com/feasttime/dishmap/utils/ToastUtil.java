/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.dishmap.utils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.view.Gravity;
import android.widget.Toast;

public class ToastUtil {
	
	public static void showToast(Context mContext, String text, int duration) {
		Toast mToast = Toast.makeText(mContext, text, Toast.LENGTH_SHORT);
        mToast.setDuration(duration);
		mToast.show();
	}

	public static void showToast(Context mContext, int resId, int duration) {
		showToast(mContext, mContext.getResources().getString(resId), duration);
	}


	/**
	 * 在非UI线程中，这个方法可以将Toast显示在UI线程
	 * 原理，追加toast在消息队列中
	 * 千万别在UI线程中使用
	 * */
	public static void showToastOnUIThread(final Context context,final CharSequence text,final  int duration) {

		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(context, text, duration);
				toast.show();
			}
		});

	}


	/**
	 * 在非UI线程中，这个方法可以将Toast显示在UI线程
	 * 原理，追加toast在消息队列中
	 * 千万别在UI线程中使用
	 * */
	public static void showToastOnUIThreadForCenter(final Context context,final CharSequence text,final  int duration) {

		Handler handler = new Handler(Looper.getMainLooper());
		handler.post(new Runnable() {
			@Override
			public void run() {
				Toast toast = Toast.makeText(context, text, duration);
				toast.setGravity(Gravity.CENTER,0,0);
				toast.show();
			}
		});

	}
}
