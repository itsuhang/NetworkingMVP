package com.suhang.networkmvp.dagger.module

import android.support.v4.app.Fragment
import com.suhang.networkmvp.annotation.ChildScope
import com.suhang.networkmvp.dagger.component.GrandChildTwoComponent
import com.suhang.networkmvp.ui.fragment.GrandChildTwoFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap


/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@ChildScope
@Module(subcomponents = arrayOf(GrandChildTwoComponent::class))
abstract class GrandChildTwoModule :BaseModule(){
    @Binds
    @IntoMap
    @FragmentKey(GrandChildTwoFragment::class)
    abstract fun build(builder: GrandChildTwoComponent.Builder): AndroidInjector.Factory<out Fragment>
//    @Provides
//    fun prividerModel(): IAttentionModel {
//        return AttentionModel()
//    }
}
