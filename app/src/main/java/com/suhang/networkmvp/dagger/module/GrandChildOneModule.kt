package com.suhang.networkmvp.dagger.module

import android.app.Activity
import android.support.v4.app.Fragment
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.component.GrandChildOneComponent
import com.suhang.networkmvp.dagger.component.GrandChildTwoComponent
import com.suhang.networkmvp.ui.fragment.GrandChildOneFragment
import com.suhang.networkmvp.ui.fragment.GrandChildTwoFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap


/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@BaseScope
@Module
abstract class GrandChildOneModule(activity: Activity) :BaseModule(activity){
    @Binds
    @IntoMap
    @FragmentKey(GrandChildOneFragment::class)
    abstract fun bind(builder: GrandChildOneComponent.Builder): AndroidInjector.Factory<out Fragment>

    @Binds
    @IntoMap
    @FragmentKey(GrandChildTwoFragment::class)
    abstract fun bind(builder: GrandChildTwoComponent.Builder): AndroidInjector.Factory<out Fragment>
//    @Provides
//    fun prividerModel(): IAttentionModel {
//        return AttentionModel()
//    }
}
