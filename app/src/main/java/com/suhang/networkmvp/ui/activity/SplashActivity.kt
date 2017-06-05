package com.suhang.networkmvp.ui.activity

import android.os.Bundle
import com.suhang.networkmvp.R
import com.suhang.networkmvp.ui.BasicActivity

class SplashActivity : BasicActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.activity_splash)
    }


    override fun subscribeEvent() {

    }

    override fun initData() {}

}
