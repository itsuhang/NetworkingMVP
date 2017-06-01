package com.suhang.networkmvp.dagger.module

import com.suhang.networkmvp.mvp.model.HomeModel
import com.suhang.networkmvp.mvp.model.IHomeModel

import dagger.Module
import dagger.Provides

/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@Module
class HomeModule {
    @Provides
    fun providerHomeModel(): IHomeModel {
        return HomeModel()
    }
}
