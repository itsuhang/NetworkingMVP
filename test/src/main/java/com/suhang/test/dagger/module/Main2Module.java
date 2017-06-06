package com.suhang.test.dagger.module;

import android.app.Activity;
import android.content.Context;

import com.suhang.test.Main2Activity;
import com.suhang.test.dagger.scope.BaseScope;

import dagger.Binds;
import dagger.Module;
import dagger.Provides;

/**
 * Created by 苏杭 on 2017/6/5 20:07.
 */
@BaseScope
@Module
public abstract class Main2Module {
	@Binds
	abstract Activity bindActivity(Main2Activity activity);
	private static Activity mActivity;

	public Main2Module(Activity activity) {
		mActivity = activity;
	}

	@BaseScope
	@Provides
	static Activity providerActivity() {
		return mActivity;
	}

	@BaseScope
	@Provides
	static Context providerContext() {
		return mActivity;
	}
}
