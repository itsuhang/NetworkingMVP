package com.suhang.networkmvp.dagger.module

import android.support.v4.app.Fragment
import com.suhang.networkmvp.annotation.ChildScope
import com.suhang.networkmvp.dagger.component.GrandChildOneComponent
import com.suhang.networkmvp.ui.fragment.GrandChildOneFragment
import dagger.Binds
import dagger.Module
import dagger.android.AndroidInjector
import dagger.android.support.FragmentKey
import dagger.multibindings.IntoMap


/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@ChildScope
@Module(subcomponents = arrayOf(GrandChildOneComponent::class))
abstract class GrandChildOneModule :BaseModule(){
    @Binds
    @IntoMap
    @FragmentKey(GrandChildOneFragment::class)
    abstract fun build(builder: GrandChildOneComponent.Builder): AndroidInjector.Factory<out Fragment>
//    @Provides
//    fun prividerModel(): IAttentionModel {
//        return AttentionModel()
//    }
}
