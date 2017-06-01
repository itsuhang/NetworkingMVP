package com.suhang.networkmvp.function


import com.suhang.networkmvp.sp.ConfigHelper

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

/**
 * Cookie添加拦截器
 */
class AddCookiesInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        val cookie = ConfigHelper.cookieOut()
        if (cookie != null) {
            builder.addHeader("Cookie", cookie)
        }
        return chain.proceed(builder.build())
    }
}