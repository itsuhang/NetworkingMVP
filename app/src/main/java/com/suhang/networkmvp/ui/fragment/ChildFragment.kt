package com.suhang.networkmvp.ui.fragment

import android.os.Bundle
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.AttentionPagerAdapter
import com.suhang.networkmvp.ui.BasicFragment
import kotlinx.android.synthetic.main.fragment_attention.*
import java.util.*

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
class ChildFragment : BasicFragment() {
    override fun initEvent() {
    }

    lateinit var adapter:AttentionPagerAdapter
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
        adapter = AttentionPagerAdapter(fragmentManager,pagers)
        vp_attention.adapter = adapter
    }
}