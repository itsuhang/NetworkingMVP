package com.suhang.networkmvp.dagger.component


import com.suhang.networkmvp.application.App
import com.suhang.networkmvp.dagger.module.*
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
        AppModule::class, ChildModule::class,
        HomeModule::class,
        GrandChildOneModule::class,
        GrandChildTwoModule::class
        ))
interface AppComponent :AndroidInjector<App>{
    abstract class Builder :AndroidInjector.Builder<App>()
}
