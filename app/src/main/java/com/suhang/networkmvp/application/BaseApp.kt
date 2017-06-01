package com.suhang.networkmvp.application

import android.app.Activity
import android.app.Application
import android.os.Environment
import com.suhang.layoutfinder.MethodFinder
import com.suhang.layoutfinder.SharedPreferencesFinder
import com.suhang.networkmvp.constants.ErrorCode
import com.suhang.networkmvp.constants.errorMessage
import com.suhang.networkmvp.dagger.component.AppComponent
import com.suhang.networkmvp.dagger.component.DaggerAppComponent
import com.suhang.networkmvp.dagger.module.AppModule
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.function.download.DownloadProgressInterceptor
import com.suhang.networkmvp.interfaces.ErrorLogger
import com.suhang.networkmvp.utils.LogUtil
import okhttp3.Cache
import okhttp3.OkHttpClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.warn
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Inject


/**
 * Created by 苏杭 on 2017/1/20 15:01.
 */

abstract class BaseApp : Application(), AnkoLogger,ErrorLogger {
    lateinit var appComponent: AppComponent
    val activityStack:MutableList<Activity> = ArrayList()
    var isDebug = true
        set(isDebug) {
            field = isDebug
            changeForDebug()
        }
    @Inject
    lateinit var mHttpClient: OkHttpClient
    lateinit var mDownloadClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        CACHE_PATH_OKHTTP = cacheDir.absolutePath + File.separator + "NetCache_OKHTTP"
        CACHE_PATH = cacheDir.absolutePath + File.separator + "NetCache"
        APP_PATH = Environment.getExternalStorageDirectory().absolutePath + "/suhang"
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        mDownloadClient = initDownloadOkHttpClient()
        inject()
        instance = this
        changeForDebug()
        SharedPreferencesFinder.init(this)
        initRetrofitService()
    }

    /**
     * 设置Okhttp缓存路径
     */
    fun setCachePathOfOkhttp(path: String) {
        CACHE_PATH_OKHTTP = path
    }

    /**
     * 设置硬盘缓存路径
     */
    fun setCachePath(path: String) {
        CACHE_PATH = path
    }

    /**
     * 设置应用SD卡路径
     */
    fun setAppPath(path: String) {
        APP_PATH = path
    }

    /**
     * 注入当前Application
     */
    abstract fun inject()

    /**
     * 初始化Retrofit的Service
     */
    protected abstract fun initRetrofitService()

    /**
     * 设置Retrofit的Service
     * @param baseUrl 基础URL
     * *
     * @param aClass Service的字节码
     */
    protected fun setRetrofit(baseUrl: String, aClass: Class<*>,factory: Converter.Factory? = GsonConverterFactory.create(),isDownload:Boolean = false) {
        try {
            val builder = Retrofit.Builder()
            val build = builder.baseUrl(baseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            if (factory != null) {
                build.addConverterFactory(factory)
            }
            if (isDownload) {
                build.client(mDownloadClient)
            } else {
                build.client(mHttpClient)
            }
            val o = build.build().create(aClass)
            MethodFinder.inject(o, aClass)
        } catch (e: Exception) {
            warn(ErrorBean(ErrorCode.ERROR_CODE_BASEAPP_RETROFIT, ErrorCode.ERROR_DESC_BASEAPP_RETROFIT, errorMessage(e)))
        }
    }

    private fun initDownloadOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val cacheSize = 100 * 1024 * 1024 // 100 MiB
        val cache = Cache(File(BaseApp.CACHE_PATH_OKHTTP), cacheSize.toLong())
        //设置超时
        builder.cache(cache)
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.readTimeout(10, TimeUnit.SECONDS)
        builder.writeTimeout(10, TimeUnit.SECONDS)
        //错误重连
        builder.retryOnConnectionFailure(true)
        builder.addInterceptor(DownloadProgressInterceptor())
        return builder.build()
    }

    fun addToStack(activity: Activity) {
        activityStack.add(activity)
    }

    fun removeFromStack(activity: Activity) {
        activityStack.remove(activity)
    }

    fun cleanStack() {
        for (activity in activityStack) {
            activity.finish()
        }
        activityStack.clear()
    }

    protected fun changeForDebug() {
        if (this.isDebug) {
            LogUtil.changeLogSwitch(true)
        } else {
            LogUtil.changeLogSwitch(false)
        }
    }

    companion object {
        lateinit var instance: BaseApp
        lateinit var CACHE_PATH_OKHTTP: String
        lateinit var CACHE_PATH: String
        lateinit var APP_PATH: String
    }
}
