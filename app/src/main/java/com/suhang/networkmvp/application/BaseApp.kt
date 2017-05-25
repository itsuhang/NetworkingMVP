package com.suhang.networkmvp.application

import android.app.Application
import android.os.Handler

import com.suhang.layoutfinder.MethodFinder
import com.suhang.networkmvp.constants.ErrorCode
import com.suhang.networkmvp.constants.errorMessage
import com.suhang.networkmvp.dagger.component.AppComponent
import com.suhang.networkmvp.dagger.component.DaggerAppComponent
import com.suhang.networkmvp.dagger.module.AppModule
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.utils.LogUtil

import java.io.File

import javax.inject.Inject

import okhttp3.OkHttpClient
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * Created by 苏杭 on 2017/1/20 15:01.
 */

abstract class BaseApp : Application(), AnkoLogger {
    lateinit var appComponent: AppComponent
    var isDebug = true
        set(isDebug) {
            field = isDebug
            changeForDebug()
        }
    @Inject
    lateinit var mHttpClient: OkHttpClient

    override fun onCreate() {
        super.onCreate()
        DATABINDING_BR = packageName + ".BR"
        CACHE_PATH_OKHTTP = cacheDir.absolutePath + File.separator + "NetCache_OKHTTP"
        CACHE_PATH = cacheDir.absolutePath + File.separator + "NetCache"
        appComponent = DaggerAppComponent.builder().appModule(AppModule(this)).build()
        inject()
        instance = this
        changeForDebug()
        initRetrofitService()
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
    protected fun setRetrofit(baseUrl: String, aClass: Class<*>) {
        try {
            val builder = Retrofit.Builder()
            val retrofit = builder.baseUrl(baseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mHttpClient).build()
            val o = retrofit.create(aClass)
            MethodFinder.inject(o, aClass)
        } catch (e: Exception) {
            warn(ErrorBean(ErrorCode.ERROR_CODE_BASEAPP_RETROFIT,ErrorCode.ERROR_DESC_BASEAPP_RETROFIT,errorMessage(e)))
        }
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
        var DATABINDING_DATA = "data"
        var DATABINDING_BR: String? = null
        var CACHE_PATH_OKHTTP: String? = null
        var CACHE_PATH: String? = null
    }
}
