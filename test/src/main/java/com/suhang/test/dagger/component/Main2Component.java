package com.suhang.test.dagger.component;

import android.app.Activity;

import com.suhang.test.Main2Activity;
import com.suhang.test.dagger.module.Main2Module;
import com.suhang.test.dagger.scope.BaseScope;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by 苏杭 on 2017/6/5 20:06.
 */
@BaseScope
@Subcomponent(modules = Main2Module.class)
public interface Main2Component extends AndroidInjector<Main2Activity>{
	@Subcomponent.Builder
	abstract class Builder extends AndroidInjector.Builder<Main2Activity> {
		@Override
		public void seedInstance(Main2Activity instance) {

		}
	}
}
