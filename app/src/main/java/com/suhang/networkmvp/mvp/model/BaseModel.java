package com.suhang.networkmvp.mvp.model;

import android.app.Activity;

import com.suhang.networkmvp.function.RxBus;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/1/21 15:11.
 */

public abstract class BaseModel  implements IBaseModel{
    @Inject
    protected RxBus mRxBus;
    @Inject
    CompositeDisposable mDisposables;
    @Inject
    Activity mActivity;
    public void destory() {
        mDisposables.dispose();
        mActivity = null;
    }
}
