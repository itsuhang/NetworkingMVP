package com.suhang.networkmvp.application

import com.squareup.leakcanary.LeakCanary
import com.suhang.networkmvp.constants.URLS
import com.suhang.networkmvp.dagger.component.DaggerAppComponent
import com.suhang.networkmvp.interfaces.IDownloadService
import com.suhang.networkmvp.interfaces.INetworkOtherService
import com.suhang.networkmvp.interfaces.INetworkService
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

/**
 * Created by 苏杭 on 2017/4/25 11:38.
 */

class App : BaseApp() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().create(this)
    }

    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return
        }
        LeakCanary.install(this)
    }

    override fun initRetrofitService() {
        setRetrofit(URLS.URL_BASE, INetworkService::class.java)
        setRetrofit(URLS.URL_BASE_1, INetworkOtherService::class.java)
        setRetrofit(URLS.URL_BASE_DOWNLOAD, IDownloadService::class.java,null,true)
    }
}
