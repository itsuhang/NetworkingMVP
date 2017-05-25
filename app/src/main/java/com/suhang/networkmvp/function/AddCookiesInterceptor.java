package com.suhang.networkmvp.function;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Cookie添加拦截器
 */
public class AddCookiesInterceptor implements Interceptor {

	@Override
	public Response intercept(Chain chain) throws IOException {
		final Request.Builder builder = chain.request().newBuilder();
//		String cookie = SharedPrefUtil.INSTANCE.getString("Cookie", null);
//		if (cookie != null) {
//			builder.addHeader("Cookie", cookie);
//		}
		return chain.proceed(builder.build());
	}
}