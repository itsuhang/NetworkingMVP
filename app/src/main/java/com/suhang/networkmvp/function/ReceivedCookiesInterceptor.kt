package com.suhang.networkmvp.function


import java.io.IOException

import io.reactivex.Flowable
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Cookies拦截器
 */
class ReceivedCookiesInterceptor : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        val originalResponse = chain.proceed(chain.request())
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            val cookieBuffer = StringBuffer()
            Flowable.fromIterable(originalResponse.headers("Set-Cookie")).map<Any> { s ->
                val cookieArray = s.split(";".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
                cookieArray[0]
            }.subscribe { s -> cookieBuffer.append(s).append(";") }
            SharedPrefUtil.putString("Cookie", cookieBuffer.toString())
        }

        return originalResponse
    }
}