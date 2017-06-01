package com.suhang.networkmvp.dagger.module

import com.suhang.networkmvp.mvp.model.AttentionModel
import com.suhang.networkmvp.mvp.model.IAttentionModel
import dagger.Module
import dagger.Provides

/**
 * Created by 苏杭 on 2017/6/1 20:44.
 */
@Module
class AttentionModule{
    @Provides
    fun prividerModel(): IAttentionModel {
        return AttentionModel()
    }
}
