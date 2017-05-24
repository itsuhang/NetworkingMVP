package com.suhang.networkmvp.ui.pager

import android.app.Activity

import com.suhang.networkmvp.R
import com.suhang.networkmvp.annotation.PagerScope
import com.suhang.networkmvp.binding.data.BaseData
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.databinding.PagerAttentionTwoBinding
import com.suhang.networkmvp.mvp.model.AttentionModel


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

@PagerScope
class AttentionTwoPager(activity: Activity) : BasePager<AttentionModel, PagerAttentionTwoBinding>(activity) {

    override fun bindLayout(): Int {
        return R.layout.pager_attention_two
    }

    override fun subscribeEvent() {}

    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }

    override fun initData() {}

    override fun initEvent() {}
}
