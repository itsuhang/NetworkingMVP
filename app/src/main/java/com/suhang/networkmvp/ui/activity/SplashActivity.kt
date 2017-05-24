package com.suhang.networkmvp.ui.activity

import android.os.Bundle

import com.suhang.networkmvp.R
import com.suhang.networkmvp.annotation.ActivityScope
import com.suhang.networkmvp.binding.data.BaseData
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.databinding.ActivitySplashBinding
import com.suhang.networkmvp.mvp.model.BlankModel

@ActivityScope
class SplashActivity : BaseActivity<BlankModel, ActivitySplashBinding>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_splash
    }

    override fun subscribeEvent() {

    }

    override fun initData() {}


    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }
}
