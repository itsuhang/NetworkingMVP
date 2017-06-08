package com.suhang.networkmvp.dagger.component

import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.ui.activity.MainActivity
import com.suhang.networkmvp.ui.activity.SplashActivity
import com.suhang.networkmvp.ui.fragment.ChildFragment
import dagger.Subcomponent

@Subcomponent(modules = arrayOf(BlankModule::class))
interface BlankComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)
    fun inject(fragment: ChildFragment)
}