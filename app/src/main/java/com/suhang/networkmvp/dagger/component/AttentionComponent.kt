package com.suhang.networkmvp.dagger.component

import com.suhang.networkmvp.dagger.module.AttentionModule
import com.suhang.networkmvp.dagger.module.HomeModule
import com.suhang.networkmvp.ui.pager.AttentionOnePager
import com.suhang.networkmvp.ui.pager.AttentionTwoPager

import dagger.Subcomponent

/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@Subcomponent(modules = arrayOf(AttentionModule::class))
interface AttentionComponent{
    fun inject(pager:AttentionOnePager)
    fun inject(pager:AttentionTwoPager)
}
