package com.suhang.networkmvp.dagger.module

import com.suhang.networkmvp.mvp.model.BlankModel
import com.suhang.networkmvp.mvp.model.IBaseModel
import dagger.Module
import dagger.Provides

/**
 * Created by 苏杭 on 2017/4/27 13:58.
 */
@Module
class BlankModule{
    @Provides
    fun providerBlankModel(): IBaseModel {
        return BlankModel()
    }
}
