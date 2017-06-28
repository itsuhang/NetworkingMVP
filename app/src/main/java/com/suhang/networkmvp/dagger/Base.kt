package com.suhang.networkmvp.dagger

import com.suhang.layoutfinderannotation.GenSubComponent
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.constants.Constant
import com.suhang.networkmvp.dagger.module.BaseModule

/**
 * Created by 苏杭 on 2017/6/28 17:35.
 */
@GenSubComponent(tag = Constant.BASE_DAGGER_TAG,childTag = Constant.BASE_CHILD_DAGGER_TAG,modules = arrayOf(BaseModule::class),scope = BaseScope::class,shouldInject = false)
class Base