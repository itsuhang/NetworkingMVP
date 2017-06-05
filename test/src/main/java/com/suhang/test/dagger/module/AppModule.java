package com.suhang.test.dagger.module;

import android.app.Activity;

import com.suhang.test.MainActivity;
import com.suhang.test.dagger.component.MainComponent;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.android.ActivityKey;
import dagger.android.AndroidInjector;
import dagger.multibindings.IntoMap;

/**
 * Created by 苏杭 on 2017/6/5 20:02.
 */
@Singleton
@Module(subcomponents = MainComponent.class)
public abstract class AppModule {
	@Binds
	@IntoMap
	@ActivityKey(MainActivity.class)
	abstract AndroidInjector.Factory<? extends Activity> build(MainComponent.Builder builder);
}
