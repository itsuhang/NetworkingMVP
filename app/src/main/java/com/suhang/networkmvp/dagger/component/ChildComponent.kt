package com.suhang.networkmvp.dagger.component

import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.module.ChildModule
import com.suhang.networkmvp.ui.fragment.GrandChildOneFragment
import com.suhang.networkmvp.ui.fragment.GrandChildTwoFragment
import dagger.Subcomponent

/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@Subcomponent(modules = arrayOf(ChildModule::class))
interface ChildComponent{
    fun inject(fragment: GrandChildOneFragment)
    fun inject(fragment: GrandChildTwoFragment)
}
