package com.suhang.networkmvp.application

import com.suhang.networkmvp.constants.URLS
import com.suhang.networkmvp.interfaces.IDownloadService
import com.suhang.networkmvp.interfaces.INetworkOtherService
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
        setRetrofit(URLS.URL_BASE, INetworkService::class.java)
        setRetrofit(URLS.URL_BASE_1, INetworkOtherService::class.java)
        setRetrofit(URLS.URL_BASE_DOWNLOAD, IDownloadService::class.java,null)
    }
}
