package com.suhang.networkmvp.dagger.module;


import com.suhang.networkmvp.mvp.IView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 苏杭 on 2017/1/24 11:57.
 * 基于MVP,提供View层的父module
 */
@Module
public abstract class ParentModule<T extends IView>  {
    protected T mView;

    public ParentModule(T view) {
        mView = view;
    }

    /**
     * 提供View
     * @return
     */
    @Provides
    public T provideView() {
        return mView;
    }

    /**
     * 提供IView接口(因dagger无法将View识别成IView,所以单独提供一个IView)
     * @return
     */
    @Provides
    public IView provideIView() {
        return mView;
    }
}
