/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.tools;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.feasttime.MenuApplication;

public class PreferenceUtil {

	private static SharedPreferences mPreference;
	private static void init(){
		mPreference = MenuApplication.getInstance().getSharedPreferences("sheng_yan_reference", Activity.MODE_PRIVATE);
	}

	public static void setStringKey(String key ,String value){
		if(mPreference == null){
			init();
		}
		mPreference.edit().putString(key, value).commit();
	}
	
	public static String getStringKey(String key){
		if(mPreference == null){
			init();
		}
		return mPreference.getString(key, "");
	}
	
	public static void setBooleanKey(String key ,boolean value){
		if(mPreference == null){
			init();
		}
		mPreference.edit().putBoolean(key, value).commit();
	}
	
	
	public static void setIntKey(String key ,int value){
		if(mPreference == null){
			init();
		}
		mPreference.edit().putInt(key, value).commit();
	}
	
	
	public static int getIntKey(Context context,String key){
		if(mPreference == null){
			init();
		}
		return mPreference.getInt(key, 0);
	}
	
	public static boolean getBooleanKey(Context context,String key){
		if(mPreference == null){
			init();
		}
		return mPreference.getBoolean(key, false);
	}
	
}
