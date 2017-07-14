package com.suhang.networkmvp.ui.activity

import android.Manifest
import android.os.Bundle
import android.support.v4.app.Fragment
import com.suhang.layoutfinderannotation.GenSubComponent
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.MainFragmentAdapter
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.application.DaggerHelper
import com.suhang.networkmvp.constants.Constant
import com.suhang.networkmvp.dagger.module.BaseModule
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.mvp.model.BlankModel
import com.suhang.networkmvp.ui.fragment.ChildFragment
import com.suhang.networkmvp.ui.fragment.HomeFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<BlankModel>() {

    lateinit var adapter: MainFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.activity_main)
    }

    override fun subscribeEvent() {

    }

    override fun initData() {
        val fragments = ArrayList<Fragment>()
        fragments.add(HomeFragment())
        fragments.add(ChildFragment())
        adapter = MainFragmentAdapter(supportFragmentManager, fragments)
        vp_main.adapter = adapter
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.destroy()
    }
}
