package com.suhang.networkmvp.ui.fragment

import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.AttentionPagerAdapter
import com.suhang.networkmvp.annotation.FragmentScope
import com.suhang.networkmvp.binding.data.BaseData
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.databinding.FragmentAttentionBinding
import com.suhang.networkmvp.mvp.model.BlankModel
import com.suhang.networkmvp.ui.pager.AttentionOnePager
import com.suhang.networkmvp.ui.pager.AttentionTwoPager
import com.suhang.networkmvp.ui.pager.BasePager

import java.util.ArrayList

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@FragmentScope
class AttentionFragment : BaseFragment<BlankModel, FragmentAttentionBinding>() {
    override fun bindLayout(): Int {
        return R.layout.fragment_attention
    }

    override fun subscribeEvent() {

    }

    override fun initData() {
        val pagers = ArrayList<BasePager<*, *>>()
        pagers.add(AttentionOnePager(activity))
        pagers.add(AttentionTwoPager(activity))
        mBinding.vpAttention.adapter = AttentionPagerAdapter(pagers)
    }


    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }
}