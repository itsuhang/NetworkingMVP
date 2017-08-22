package com.suhang.networkmvp.function


import com.suhang.networkmvp.utils.SystemUtil
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * Created by 苏杭 on 2017/2/3 16:48.
 */

class CacheInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response? {
        try {
            var request = chain.request()
            if (!SystemUtil.isNetworkAvailable()) {//没网强制从缓存读取(必须得写，不然断网状态下，退出应用，或者等待一分钟后，就获取不到缓存）
                request = request.newBuilder()
                        .cacheControl(CacheControl.FORCE_CACHE)
                        .build()
            }
            val response = chain.proceed(request)
            val responseLatest: Response
            if (SystemUtil.isNetworkAvailable()) {
                val maxAge = 60 //有网失效一分钟
                responseLatest = response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build()
            } else {
                val maxStale = 60 * 60 * 6 // 没网失效6小时
                responseLatest = response.newBuilder()
                        .removeHeader("Pragma")
                        .removeHeader("Cache-Control")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale)
                        .build()
            }
            return responseLatest
        } catch (e: Exception) {
            return null
        }
    }
}
