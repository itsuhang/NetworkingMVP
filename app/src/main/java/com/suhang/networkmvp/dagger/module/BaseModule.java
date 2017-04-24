package com.suhang.networkmvp.dagger.module;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;


import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.utils.DialogHelp;

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

}
