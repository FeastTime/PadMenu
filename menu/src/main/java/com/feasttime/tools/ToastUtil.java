/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.tools;

import android.content.Context;
import android.os.Handler;
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
}
