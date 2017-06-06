package com.suhang.test.dagger.module;

import android.app.Activity;

import com.suhang.test.Dog;
import com.suhang.test.Main2Activity;
import com.suhang.test.MainActivity;
import com.suhang.test.dagger.component.Main2Component;
import com.suhang.test.dagger.component.MainComponent;

import java.util.Set;

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
@Module(subcomponents = {MainComponent.class,Main2Component.class})
public abstract class AppModule {
	@Binds
	@IntoMap
	@ActivityKey(value = MainActivity.class)
	abstract AndroidInjector.Factory<? extends Activity> buildMain(MainComponent.Builder builder);

	@Binds
	@IntoMap
	@ActivityKey(value = Main2Activity.class)
	abstract AndroidInjector.Factory<? extends Activity> buildMain2(Main2Component.Builder builder2);
}
