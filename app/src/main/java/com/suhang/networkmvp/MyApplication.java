package com.suhang.networkmvp;

import android.app.Application;

/**
 * Created by sh on 2016/10/28 16:15.
 */

public class MyApplication extends Application {
	private static MyApplication mInstance;

	public static MyApplication getInstance() {
		return mInstance;
	}
	@Override
	public void onCreate() {
		super.onCreate();
		mInstance = this;
	}
}
