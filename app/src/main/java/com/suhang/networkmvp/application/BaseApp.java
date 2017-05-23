package com.suhang.networkmvp.application;

import android.app.Application;

import com.suhang.layoutfinder.MethodFinder;
import com.suhang.networkmvp.dagger.component.AppComponent;
import com.suhang.networkmvp.dagger.component.DaggerAppComponent;
import com.suhang.networkmvp.dagger.module.AppModule;
import com.suhang.networkmvp.utils.LogUtil;

import java.io.File;

import javax.inject.Inject;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


/**
 * Created by 苏杭 on 2017/1/20 15:01.
 */

public abstract class BaseApp extends Application {
    private static Application sApp;
    private AppComponent mAppComponent;
    private boolean isDebug = true;
    @Inject
    OkHttpClient mHttpClient;
    protected AppModule mModule;
    public static  String DATABINDING_DATA = "data";
    public static  String DATABINDING_BR ;
    public static String CACHE_PATH_OKHTTP;
    public static String CACHE_PATH;
    public static Application getInstance() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        DATABINDING_BR = getPackageName() + ".BR";
        CACHE_PATH_OKHTTP = getCacheDir().getAbsolutePath() + File.separator + "NetCache_OKHTTP";
        CACHE_PATH = getCacheDir().getAbsolutePath() + File.separator + "NetCache";
        mModule = new AppModule(this);
        sApp = this;
        changeForDebug();
        mAppComponent = DaggerAppComponent.builder().appModule(mModule).build();
    }

    /**
     * 初始化Retrofit的Service
     */
    protected abstract void initRetrofitService();

    /**
     * 设置Retrofit的Service
     * @param baseUrl 基础URL
     * @param aClass Service的字节码
     */
    protected void setRetrofit(String baseUrl,Class aClass) {
        Retrofit.Builder builder = new Retrofit.Builder();
        Retrofit retrofit = builder.baseUrl(baseUrl).addCallAdapterFactory(RxJava2CallAdapterFactory.create()).addConverterFactory(GsonConverterFactory.create()).client(mHttpClient).build();
        Object o = retrofit.create(aClass);
        MethodFinder.inject(o,aClass);
    }

    protected void changeForDebug() {
        if (isDebug) {
            LogUtil.changeLogSwitch(true);
        } else {
            LogUtil.changeLogSwitch(false);
        }
    }

    public void setDebug(boolean isDebug) {
        this.isDebug = isDebug;
        changeForDebug();
    }

    public boolean isDebug() {
        return isDebug;
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
