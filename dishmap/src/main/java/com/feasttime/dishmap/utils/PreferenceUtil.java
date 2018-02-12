/*
 * Copyright (c) 2017. sheng yan
 */

package com.feasttime.dishmap.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import com.feasttime.dishmap.application.MyApplication;


public class PreferenceUtil {


	public static final String MOBILE_NO = "mobileNO";
	public static final String TOKEN = "token";
	public static final String USER_TYPE = "userType";
	public static final String PERSON_NO = "personNO";
	public static final String WE_CHAT_OPENID = "openId";
	public static final String USER_ID = "userId";
	public static final String USER_ICON = "userIcon"; //用户头像
	public static final String USER_NICK_NAME = "userNickName"; //用户昵称

	private static SharedPreferences mPreference;
	private static void init(){
		mPreference = MyApplication.getInstance().getSharedPreferences("sheng_yan_reference", Activity.MODE_PRIVATE);
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
