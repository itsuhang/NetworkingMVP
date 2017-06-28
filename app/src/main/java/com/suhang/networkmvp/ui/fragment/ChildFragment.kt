package com.suhang.networkmvp.ui.fragment

import android.os.Bundle
import com.suhang.layoutfinderannotation.GenSubComponent
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.AttentionPagerAdapter
import com.suhang.networkmvp.application.DaggerHelper
import com.suhang.networkmvp.constants.Constant
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.dagger.module.ChildModule
import com.suhang.networkmvp.mvp.model.BlankModel
import kotlinx.android.synthetic.main.fragment_attention.*
import java.util.*

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@GenSubComponent(tag = Constant.BASE_CHILD_DAGGER_TAG, modules = arrayOf(BlankModule::class))
class ChildFragment : BaseFragment<BlankModel>() {
    override fun initDagger() {
        DaggerHelper.getInstance().getChildFragmentComponent(this)
    }

    override fun initEvent() {
    }

    lateinit var adapter: AttentionPagerAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.fragment_attention)
    }

    override fun subscribeEvent() {

    }

    override fun initData() {
        val pagers = ArrayList<BaseFragment<*>>()
        pagers.add(GrandChildOneFragment())
        pagers.add(GrandChildTwoFragment())
        adapter = AttentionPagerAdapter(fragmentManager, pagers)
        vp_attention.adapter = adapter
    }
}