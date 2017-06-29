package com.suhang.networkmvp.ui.activity

import android.os.Bundle
import com.suhang.layoutfinderannotation.GenSubComponent
import com.suhang.networkmvp.R
import com.suhang.networkmvp.application.DaggerHelper
import com.suhang.networkmvp.constants.Constant
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.mvp.model.BlankModel

class SplashActivity : BaseActivity<BlankModel>() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.activity_splash)
    }


    override fun subscribeEvent() {

    }

    override fun initData() {}

}
