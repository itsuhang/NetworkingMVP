package com.suhang.test.dagger.component;

import com.suhang.test.dagger.module.AppModule;
import com.suhang.test.App;
import com.suhang.test.dagger.module.MainModule;

import javax.inject.Singleton;

import dagger.Component;
import dagger.android.AndroidInjectionModule;
import dagger.android.AndroidInjector;
import dagger.android.support.AndroidSupportInjectionModule;

/**
 * Created by 苏杭 on 2017/6/5 19:31.
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AndroidSupportInjectionModule.class, AppModule.class})
interface AppComponent extends AndroidInjector<App> {
	@Component.Builder
	abstract class Builder extends AndroidInjector.Builder<App> {

	}
}
