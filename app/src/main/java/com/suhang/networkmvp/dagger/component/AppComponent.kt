package com.suhang.networkmvp.dagger.component


import com.suhang.networkmvp.application.App
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.dagger.module.AppModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

/**
 * Created by 苏杭 on 2017/1/22 17:32.
 */
@Singleton
@Component(modules = arrayOf(AndroidInjectionModule::class,
        AndroidSupportInjectionModule::class,
        AppModule::class
        ))
interface AppComponent :AndroidInjector<BaseApp>{
    @Component.Builder
    abstract class Builder :AndroidInjector.Builder<BaseApp>()
}
