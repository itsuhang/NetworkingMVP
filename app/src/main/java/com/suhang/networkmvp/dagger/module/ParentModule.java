package com.suhang.networkmvp.dagger.module;


import com.suhang.networkmvp.mvp.IView;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 苏杭 on 2017/1/24 11:57.
 */
@Module
public abstract class ParentModule<T extends IView>  {
    protected T mView;

    public ParentModule(T view) {
        mView = view;
    }

    @Provides
    public T provideView() {
        return mView;
    }

    @Provides
    public IView provideIView() {
        return mView;
    }
}
