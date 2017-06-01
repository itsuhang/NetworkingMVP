package com.suhang.networkmvp.dagger.component


import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.module.AttentionModule
import com.suhang.networkmvp.dagger.module.BaseModule
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.dagger.module.HomeModule

import dagger.Subcomponent

/**
 * Created by 苏杭 on 2017/1/20 16:26.
 */

@BaseScope
@Subcomponent(modules = arrayOf(BaseModule::class))
interface BaseComponent {
    fun providerBlankComponent(blankModule: BlankModule): BlankComponent
    fun providerHomeComponent(homeModule: HomeModule): HomeComponent
    fun providerAttentionComponent(attentionModule: AttentionModule): AttentionComponent
}
