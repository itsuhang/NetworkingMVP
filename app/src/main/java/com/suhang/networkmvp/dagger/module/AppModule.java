package com.suhang.networkmvp.dagger.module;

import com.google.gson.Gson;

import android.app.Application;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.suhang.layoutfinder.MethodFinder;
import com.suhang.networkmvp.constants.Constants;
import com.suhang.networkmvp.function.AddCookiesInterceptor;
import com.suhang.networkmvp.function.CacheInterceptor;
import com.suhang.networkmvp.function.ReceivedCookiesInterceptor;
import com.suhang.networkmvp.interfaces.INetworkOtherService;
import com.suhang.networkmvp.interfaces.INetworkService;
import com.suhang.networkmvp.interfaces.IUploadService;
import com.suhang.networkmvp.utils.LogUtil;

import java.io.File;
import java.util.concurrent.TimeUnit;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 苏杭 on 2017/1/22 17:32.
 * 用于提供单例对象
 */

@Singleton
@Module
public class AppModule {
    private Application mApplication;
    private OkHttpClient mOkHttpClient;
    private final Retrofit.Builder mBuilder;
    private final Gson mGson;

    public AppModule(Application application) {
        mApplication = application;
        initOkHttpClient();
        mBuilder = new Retrofit.Builder();
        mGson = new Gson();
        initRetrofit();
    }

    private void initRetrofit() {
        Retrofit retrofit = mBuilder.baseUrl(Constants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build();
        INetworkService iNetworkService = retrofit.create(INetworkService.class);
        MethodFinder.inject(iNetworkService,INetworkService.class);
        retrofit = mBuilder.baseUrl(Constants.BASE_URL1).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build();
        INetworkOtherService iNetworkOtherService = retrofit.create(INetworkOtherService.class);
        MethodFinder.inject(iNetworkOtherService,INetworkOtherService.class);
        retrofit = mBuilder.baseUrl(Constants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build();
        IUploadService iUploadService = retrofit.create(IUploadService.class);
        MethodFinder.inject(iUploadService,IUploadService.class);
    }

    /**
     * 初始化和设置OkHttpClient
     */
    private void initOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        int cacheSize = 100 * 1024 * 1024; // 100 MiB
        Cache cache = new Cache(new File(Constants.CACHE_PATH_OKHTTP), cacheSize);
        //设置超时
        builder.cache(cache);
        builder.connectTimeout(10, TimeUnit.SECONDS);
        builder.readTimeout(20, TimeUnit.SECONDS);
        builder.writeTimeout(20, TimeUnit.SECONDS);
        //错误重连
        builder.retryOnConnectionFailure(true);
        builder.addInterceptor(new AddCookiesInterceptor());
        builder.addInterceptor(new ReceivedCookiesInterceptor());
        builder.addInterceptor(new CacheInterceptor());
        builder.addNetworkInterceptor(new CacheInterceptor());
        mOkHttpClient = builder.build();
    }

    /**
     * 提供OkHttpClient(全局单例)
     */
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return mOkHttpClient;
    }

//    /**
//     * 提供网络Service(全局单例)
//     */
//    @Singleton
//    @Provides
//    INetworkService provideNetworkService() {
//        Retrofit retrofit = mBuilder.baseUrl(Constants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build();
//        INetworkService iNetworkService = retrofit.create(INetworkService.class);
//        MethodFinder.inject(iNetworkService,INetworkService.class);
//        return iNetworkService;
//    }
//
//    /**
//     * 提供网络Service(全局单例)
//     */
//    @Singleton
//    @Provides
//    INetworkOtherService provideNetworkOtherService() {
//        Retrofit retrofit = mBuilder.baseUrl(Constants.BASE_URL1).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build();
//        INetworkOtherService iNetworkOtherService = retrofit.create(INetworkOtherService.class);
//        MethodFinder.inject(iNetworkOtherService,INetworkOtherService.class);
//        return iNetworkOtherService;
//    }
//
//    /**
//     * 提供上传Service(全局单例)
//     */
//    @Singleton
//    @Provides
//    IUploadService provideUploadService() {
//        Retrofit retrofit = mBuilder.baseUrl(Constants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build();
//        IUploadService iUploadService = retrofit.create(IUploadService.class);
//        MethodFinder.inject(iUploadService,IUploadService.class);
//        return iUploadService;
//    }

    /**
     * 提供Gson(全局单例)
     */
    @Singleton
    @Provides
    Gson provideGson() {
        return mGson;
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }
}
