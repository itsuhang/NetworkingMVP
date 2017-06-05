package com.suhang.networkmvp.dagger.module

import android.app.Activity
import android.support.v4.app.Fragment
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.component.ChildComponent
import com.suhang.networkmvp.ui.fragment.ChildFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap

/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@Module
@BaseScope
abstract class ChildModule(activity: Activity) :BaseModule(activity){
    @Binds
    @IntoMap
    @FragmentKey(ChildFragment::class)
    abstract fun bind(builder: ChildComponent.Builder): AndroidInjector.Factory<out Fragment>
//    @Provides
//    fun prividerModel(): IAttentionModel {
//        return AttentionModel()
//    }
}
