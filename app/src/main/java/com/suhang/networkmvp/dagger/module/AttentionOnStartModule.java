package com.suhang.networkmvp.dagger.module;


import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.constants.Constants;
import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.interfaces.INetworkService;
import com.suhang.networkmvp.mvp.contract.IAttentionContract;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by 苏杭 on 2017/1/20 17:03.
 */

@PagerScope
@Module
public class AttentionOnStartModule extends NetworkModule<IAttentionContract.IAttentionView>{
    public AttentionOnStartModule(IAttentionContract.IAttentionView view) {
        super(view);
    }
}
