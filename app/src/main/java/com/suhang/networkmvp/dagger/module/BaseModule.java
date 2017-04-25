package com.suhang.networkmvp.dagger.module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;


import com.bumptech.glide.disklrucache.DiskLruCache;
import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.constants.Constants;
import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.utils.DialogHelp;
import com.suhang.networkmvp.utils.SystemUtil;

import java.io.File;
import java.io.IOException;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/1/20 16:26.
 * 用于提供公共对象(公用但非单例)
 */

@BaseScope
@Module
public class BaseModule {
    private Activity mActivity;

    public BaseModule(Activity activity) {
        mActivity = activity;
    }

    @BaseScope
    @Provides
    Activity provideActivity() {
        return mActivity;
    }

    @BaseScope
    @Provides
    Context provideContext() {
        return mActivity;
    }

    @BaseScope
    @Provides
    Dialog provideDialog() {
        return DialogHelp.getWaitDialog(mActivity);
    }

    @Provides
    CompositeDisposable provideCD() {
        return new CompositeDisposable();
    }

    /**
     * 提供硬盘缓存工具
     * @return
     */
    @Provides
    DiskLruCache provideDiskLruCache() {
        DiskLruCache diskLruCache = null;
        try {
            diskLruCache = DiskLruCache.open(new File(Constants.CACHE_PATH), SystemUtil.getAppVersion(), 1, 1024 * 1024 * 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return diskLruCache;
    }

    @Provides
    @BaseScope
    RxBus getRxBus() {
        return new RxBus();
    }

}
