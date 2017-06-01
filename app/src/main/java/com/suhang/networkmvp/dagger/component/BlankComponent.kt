package com.suhang.networkmvp.dagger.component

import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.ui.activity.MainActivity
import com.suhang.networkmvp.ui.activity.SplashActivity
import com.suhang.networkmvp.ui.fragment.AttentionFragment
import com.suhang.networkmvp.ui.fragment.HomeFragment
import com.suhang.networkmvp.ui.pager.AttentionOnePager
import com.suhang.networkmvp.ui.pager.AttentionTwoPager

import dagger.Subcomponent

@Subcomponent(modules = arrayOf(BlankModule::class))
interface BlankComponent {
    fun inject(activity: MainActivity)
    fun inject(activity: SplashActivity)
    fun inject(fragment: AttentionFragment)
}