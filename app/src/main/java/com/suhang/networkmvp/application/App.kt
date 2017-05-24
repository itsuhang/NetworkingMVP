package com.suhang.networkmvp.application

import com.suhang.networkmvp.constants.Constants
import com.suhang.networkmvp.interfaces.INetworkService

/**
 * Created by 苏杭 on 2017/4/25 11:38.
 */

class App : BaseApp() {
    override fun inject() {
        appComponent.inject(this)
    }

    override fun onCreate() {
        super.onCreate()
    }

    override fun initRetrofitService() {
        setRetrofit(Constants.BASE_URL, INetworkService::class.java)
    }
}
