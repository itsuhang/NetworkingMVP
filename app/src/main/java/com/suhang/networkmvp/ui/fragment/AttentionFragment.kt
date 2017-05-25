package com.suhang.networkmvp.ui.fragment

import android.os.Bundle
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.AttentionPagerAdapter
import com.suhang.networkmvp.annotation.FragmentScope
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.mvp.model.BlankModel
import com.suhang.networkmvp.ui.pager.AttentionOnePager
import com.suhang.networkmvp.ui.pager.AttentionTwoPager
import com.suhang.networkmvp.ui.pager.BasePager
import kotlinx.android.synthetic.main.fragment_attention.*
import java.util.*

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@FragmentScope
class AttentionFragment : BaseFragment<BlankModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.fragment_attention)
    }
    override fun subscribeEvent() {

    }

    override fun initData() {
        val pagers = ArrayList<BasePager<*>>()
        pagers.add(AttentionOnePager(activity))
        pagers.add(AttentionTwoPager(activity))
        vp_attention.adapter = AttentionPagerAdapter(pagers)
    }


    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }
}