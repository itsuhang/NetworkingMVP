package com.suhang.networkmvp.ui.activity

import android.Manifest
import android.os.Bundle
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.MainFragmentAdapter
import com.suhang.networkmvp.annotation.ActivityScope
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.mvp.model.IBaseModel
import com.suhang.networkmvp.ui.fragment.AttentionFragment
import com.suhang.networkmvp.ui.fragment.BaseFragment
import com.suhang.networkmvp.ui.fragment.HomeFragment
import com.tbruyelle.rxpermissions2.RxPermissions
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : BaseActivity<IBaseModel>() {
    lateinit var adapter:MainFragmentAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.activity_main)
    }

    override fun subscribeEvent() {

    }

    override fun initData() {
        RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.READ_EXTERNAL_STORAGE).subscribe({

        })
        val fragments = ArrayList<BaseFragment<*>>()
        fragments.add(HomeFragment())
        fragments.add(AttentionFragment())
        adapter = MainFragmentAdapter(supportFragmentManager, fragments)
        vp_main.adapter = adapter
    }


    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        adapter.destroy()
    }
}
