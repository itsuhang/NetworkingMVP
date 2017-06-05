package com.suhang.networkmvp.dagger.component

import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.module.HomeModule
import com.suhang.networkmvp.ui.fragment.HomeFragment

import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@BaseScope
@Subcomponent(modules = arrayOf(HomeModule::class))
interface HomeComponent :AndroidInjector<HomeFragment>{
    abstract class Builder :AndroidInjector.Builder<HomeFragment>()
}
