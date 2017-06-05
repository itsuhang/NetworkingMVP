package com.suhang.test.dagger.component;

import com.suhang.test.MainActivity;
import com.suhang.test.dagger.module.MainModule;

import dagger.Subcomponent;
import dagger.android.AndroidInjector;

/**
 * Created by 苏杭 on 2017/6/5 20:06.
 */
@Subcomponent(modules = MainModule.class)
public interface MainComponent extends AndroidInjector<MainActivity>{
	@Subcomponent.Builder
	abstract class Builder extends AndroidInjector.Builder<MainActivity> {

	}
}
