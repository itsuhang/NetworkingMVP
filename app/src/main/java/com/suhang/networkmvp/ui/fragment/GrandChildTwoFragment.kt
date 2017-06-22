package com.suhang.networkmvp.ui.fragment

import android.os.Bundle
import com.suhang.networkmvp.R
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.dagger.module.ChildModule
import com.suhang.networkmvp.mvp.model.AttentionModel
import com.suhang.networkmvp.mvp.model.IAttentionModel

/**
 * Created by 苏杭 on 2017/6/5 11:33.
 */
class GrandChildTwoFragment : BaseFragment<AttentionModel>() {
    override fun injectDagger() {
        baseComponent.providerAttentionComponent(ChildModule(activity)).inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.pager_attention_two)
    }

    override fun subscribeEvent() {
    }

    override fun initData() {
    }

    override fun initEvent() {
    }
}