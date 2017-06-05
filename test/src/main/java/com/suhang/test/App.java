package com.suhang.test;


import com.suhang.test.dagger.component.DaggerAppComponent;

import dagger.android.AndroidInjector;
import dagger.android.DaggerApplication;

/**
 * Created by 苏杭 on 2017/6/5 19:32.
 */

public class App extends DaggerApplication{
	@Override
	public void onCreate() {
		super.onCreate();
	}

	@Override
	protected AndroidInjector<? extends DaggerApplication> applicationInjector() {
		return DaggerAppComponent.builder().create(this);
	}
}
