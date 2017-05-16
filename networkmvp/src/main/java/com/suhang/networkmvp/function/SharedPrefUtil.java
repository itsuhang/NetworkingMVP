package com.suhang.networkmvp.function;

import android.content.Context;
import android.content.SharedPreferences;

import com.suhang.networkmvp.application.BaseApp;


/**
 * 本地存储SharedPreferences工具
 * Created by sh
 */
public class SharedPrefUtil {
	private static SharedPreferences mSp;
	private static String name = "config";

	private static SharedPreferences getSharedPref(Context context) {
		if (mSp == null) {
			mSp = context.getSharedPreferences(name, Context.MODE_PRIVATE);
		}
		return mSp;
	}

	/**
	 * 设置SharePrefrences文件名字
	 *
	 * @param name
	 */
	public static void setName(String name) {
		SharedPrefUtil.name = name;
	}


	//下面方法都是对不同数据类型进行保存,获取

	public static void putBoolean(String key, boolean value) {
		getSharedPref(BaseApp.getInstance()).edit().putBoolean(key, value).apply();
	}

	public static boolean getBoolean(String key, boolean defValue) {
		return getSharedPref(BaseApp.getInstance()).getBoolean(key, defValue);
	}

	public static void putString(String key, String value) {
		getSharedPref(BaseApp.getInstance()).edit().putString(key, value).apply();
	}

	public static String getString(String key, String defValue) {
		return getSharedPref(BaseApp.getInstance()).getString(key, defValue);
	}

	public static void putInt(String key, int value) {
		getSharedPref(BaseApp.getInstance()).edit().putInt(key, value).apply();
	}

	public static int getInt(String key, int defValue) {
		return getSharedPref(BaseApp.getInstance()).getInt(key, defValue);
	}

	public static void putFloat(String key, float value) {
		getSharedPref(BaseApp.getInstance()).edit().putFloat(key, value).apply();
	}

	public static float getFloat(String key, float defValue) {
		return getSharedPref(BaseApp.getInstance()).getFloat(key, defValue);
	}

	public static void putLong(String key, long value) {
		getSharedPref(BaseApp.getInstance()).edit().putLong(key, value).apply();
	}

	public static long getLong(String key, long defValue) {
		return getSharedPref(BaseApp.getInstance()).getLong(key, defValue);
	}


	/**
	 * 删除某个键值对
	 *
	 * @param context
	 * @param key
	 */
	public static void remove(Context context, String key) {
		getSharedPref(context).edit().remove(key).apply();
	}

}