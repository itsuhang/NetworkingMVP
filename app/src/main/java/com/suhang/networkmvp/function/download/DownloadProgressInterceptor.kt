package com.suhang.networkmvp.function.download

import com.suhang.networkmvp.function.RxBus
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

class DownloadProgressInterceptor(var mRxBus: RxBus) : Interceptor {
    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())

        return originalResponse.newBuilder()
                .body(DownloadProgressResponseBody(originalResponse.body()!!, mRxBus))
                .build()
    }
}