package com.suhang.networkmvp.dagger.component

import com.suhang.networkmvp.dagger.module.HomeModule
import com.suhang.networkmvp.ui.fragment.HomeFragment
import dagger.Subcomponent

/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@Subcomponent(modules = arrayOf(HomeModule::class))
interface HomeComponent{
    fun inject(fragment:HomeFragment)
}
