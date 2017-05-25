package com.suhang.networkmvp.ui.activity

import android.os.Bundle
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.MainFragmentAdapter
import com.suhang.networkmvp.annotation.ActivityScope
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.mvp.model.BlankModel
import com.suhang.networkmvp.ui.fragment.AttentionFragment
import com.suhang.networkmvp.ui.fragment.BaseFragment
import com.suhang.networkmvp.ui.fragment.HomeFragment
import kotlinx.android.synthetic.main.activity_main.*

@ActivityScope
class MainActivity : BaseActivity<BlankModel>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.activity_main)
    }

    override fun subscribeEvent() {

    }

    override fun initData() {
        val fragments = ArrayList<BaseFragment<*>>()
        fragments.add(HomeFragment())
        fragments.add(AttentionFragment())
        vp_main.adapter = MainFragmentAdapter(supportFragmentManager, fragments)
    }


    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }
}
