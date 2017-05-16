package com.suhang.networkmvp.function;


import com.suhang.networkmvp.utils.LogUtil;

import java.io.IOException;

import io.reactivex.Flowable;
import okhttp3.Interceptor;
import okhttp3.Response;

/**
 * Cookies拦截器
 */
public class ReceivedCookiesInterceptor implements Interceptor {
	@Override
	public Response intercept(Chain chain) throws IOException {

		Response originalResponse = chain.proceed(chain.request());
		//这里获取请求返回的cookie
		if (!originalResponse.headers("Set-Cookie").isEmpty()) {
			final StringBuffer cookieBuffer = new StringBuffer();
			Flowable.fromIterable(originalResponse.headers("Set-Cookie")).map(s -> {
				String[] cookieArray = s.split(";");
				return cookieArray[0];
			}).subscribe(s -> cookieBuffer.append(s).append(";"));
			SharedPrefUtil.putString("Cookie",cookieBuffer.toString());
		}

		return originalResponse;
	}
}