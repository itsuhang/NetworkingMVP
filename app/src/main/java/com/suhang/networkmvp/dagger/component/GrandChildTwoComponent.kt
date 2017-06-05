package com.suhang.networkmvp.dagger.component

import com.suhang.networkmvp.annotation.ChildScope
import com.suhang.networkmvp.dagger.module.GrandChildTwoModule
import com.suhang.networkmvp.ui.fragment.GrandChildTwoFragment
import dagger.Subcomponent
import dagger.android.AndroidInjector

/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@ChildScope
@Subcomponent(modules = arrayOf(GrandChildTwoModule::class))
interface GrandChildTwoComponent :AndroidInjector<GrandChildTwoFragment>{
    @Subcomponent.Builder
    abstract class Builder:AndroidInjector.Builder<GrandChildTwoFragment>()
}
