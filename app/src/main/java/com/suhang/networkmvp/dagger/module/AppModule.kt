package com.suhang.networkmvp.dagger.module

import com.google.gson.Gson
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.function.AddCookiesInterceptor
import com.suhang.networkmvp.function.CacheInterceptor
import com.suhang.networkmvp.function.ReceivedCookiesInterceptor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


/**
 * Created by 苏杭 on 2017/1/22 17:32.
 * 用于提供单例对象
 */

@Singleton
@Module
class AppModule{
    private val mOkHttpClient: OkHttpClient
    private val mGson: Gson

    init {
        mOkHttpClient = initOkHttpClient()
        mGson = Gson()
    }

    /**
     * 初始化和设置OkHttpClient
     */
    private fun initOkHttpClient(): OkHttpClient {
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
        builder.addInterceptor(AddCookiesInterceptor())
        builder.addInterceptor(ReceivedCookiesInterceptor())
        builder.addInterceptor(CacheInterceptor())
        builder.addNetworkInterceptor(CacheInterceptor())
        return builder.build()
    }

    /**
     * 提供OkHttpClient(全局单例)
     */
    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return mOkHttpClient
    }

    /**
     * 提供Gson(全局单例)
     */
    @Singleton
    @Provides
    fun provideGson(): Gson {
        return mGson
    }
}
