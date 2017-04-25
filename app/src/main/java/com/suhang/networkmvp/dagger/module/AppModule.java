package com.suhang.networkmvp.dagger.module;

import com.google.gson.Gson;

import android.app.Application;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.suhang.networkmvp.constants.Constants;
import com.suhang.networkmvp.function.AddCookiesInterceptor;
import com.suhang.networkmvp.function.CacheInterceptor;
import com.suhang.networkmvp.function.ReceivedCookiesInterceptor;
import com.suhang.networkmvp.interfaces.INetworkService;
import com.suhang.networkmvp.interfaces.IUploadService;
import com.suhang.networkmvp.utils.SystemUtil;

import java.io.File;
import java.io.IOException;
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
    public AppModule(Application application) {
        mApplication = application;
        initOkHttpClient();
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
     * @return
     */
    @Singleton
    @Provides
    OkHttpClient provideOkHttpClient() {
        return mOkHttpClient;
    }

    /**
     * 提供网络Service(全局单例)
     * @return
     */
    @Singleton
    @Provides
    INetworkService provideNetworkService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build();
        return retrofit.create(INetworkService.class);
    }

    /**
     * 提供上传Service(全局单例)
     * @return
     */
    @Singleton
    @Provides
    IUploadService provideUploadService() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(Constants.BASE_URL).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mOkHttpClient).build();
        return retrofit.create(IUploadService.class);
    }

    /**
     * 提供Gson(全局单例)
     * @return
     */
    @Singleton
    @Provides
    Gson provideGson() {
        return new Gson();
    }

    @Provides
    Application provideApplication() {
        return mApplication;
    }
}
