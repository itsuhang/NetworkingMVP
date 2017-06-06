package com.suhang.networkmvp.dagger.component
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.module.BaseModule
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.dagger.module.ChildModule
import com.suhang.networkmvp.dagger.module.HomeModule
import dagger.Subcomponent

@BaseScope
@Subcomponent(modules = arrayOf(BaseModule::class))
interface BaseComponent {
    fun providerBlankComponent(blankModule: BlankModule): BlankComponent
    fun providerHomeComponent(homeModule: HomeModule): HomeComponent
    fun providerAttentionComponent(attentionModule: ChildModule): ChildComponent
}