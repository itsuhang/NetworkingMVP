package com.suhang.networkmvp.dagger.module

import android.support.v4.app.Fragment
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.component.HomeComponent
import com.suhang.networkmvp.ui.fragment.HomeFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap


/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@Module(subcomponents = arrayOf(HomeComponent::class))
@BaseScope
abstract class HomeModule :BaseModule(){
    @Binds
    @IntoMap
    @FragmentKey(HomeFragment::class)
    abstract fun build(builder: HomeComponent.Builder): AndroidInjector.Factory<out Fragment>
}
