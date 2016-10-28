package com.suhang.networkmvp.util;

import android.webkit.MimeTypeMap;


import com.suhang.networkmvp.interfaces.IUploadService;
import com.suhang.networkmvp.interfaces.INetworkService;
import com.suhang.networkmvp.interfaces.ProgressListener;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Created by sh on 2016/10/25 16:06.
 */

public class RetrofitHelper {
	private static OkHttpClient sOkHttpClient;
	private INetworkService mNetWorkService;
	private IUploadService mUploadService;

	public RetrofitHelper() {
		initOkhttp();
		mNetWorkService = initMainService();
		mUploadService = initUploadService();
	}

	private static void initOkhttp() {
		OkHttpClient.Builder builder = new OkHttpClient.Builder();
		//设置超时
		builder.connectTimeout(10, TimeUnit.SECONDS);
		builder.readTimeout(20, TimeUnit.SECONDS);
		builder.writeTimeout(20, TimeUnit.SECONDS);
		//错误重连
		builder.retryOnConnectionFailure(true);
		sOkHttpClient = builder.build();
	}

	public String getMimeType(String path) {
		String type = null;
		String extension = MimeTypeMap.getFileExtensionFromUrl(path);
		if (extension != null) {
			MimeTypeMap mime = MimeTypeMap.getSingleton();
			type = mime.getMimeTypeFromExtension(extension);
		}
		return type;
	}

	private static INetworkService initMainService() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(INetworkService.BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(sOkHttpClient).build();
		return retrofit.create(INetworkService.class);
	}

	private static IUploadService initUploadService() {
		Retrofit retrofit = new Retrofit.Builder().baseUrl(IUploadService.BASE_URL).addCallAdapterFactory(RxJavaCallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(sOkHttpClient).build();
		return retrofit.create(IUploadService.class);
	}

//	public <T> Observable<T> uploadHead(Class<T> aClass, File file, Map<String, String> params, ProgressListener listener) {
//		Map<String, RequestBody> requestBodyMap = new HashMap<>();
//		UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, MediaType.parse(getMimeType(file.getAbsolutePath())), listener);
//		requestBodyMap.put("file\"; filename=\"" + file.getName(), fileRequestBody);
//		for (Map.Entry<String, String> entry : params.entrySet()) {
//			requestBodyMap.put(entry.getKey(), RequestBody.create(null, entry.getValue()));
//		}
//		return mUploadService.getUploadHead(requestBodyMap).ofType(aClass);
//	}
//
//
//	public <T> Observable<T> getGameInfo(Class<T> aClass, Map<String, String> params) {
//		return mNetWorkService.getGameInfo().ofType(aClass);
//	}
//
//	public <T> Observable<T> getHistoryInfo(Class<T> aClass, Map<String, String> params) {
//		return mNetWorkService.getHistoryInfo(params).ofType(aClass);
//	}
//
//	public <T> Observable<T> getPersonalInfo(Class<T> aClass, Map<String, String> params) {
//		return mNetWorkService.getPersonalInfo(params).ofType(aClass);
//	}
}
