package com.suhang.networkmvp.mvp.base;

import android.app.Activity;

import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.mvp.IModel;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/1/21 15:11.
 */

public abstract class BaseModel implements IModel {
    public static final String TAG = "BaseModel";
    @Inject
    protected RxBus mRxBus;
    @Inject
    CompositeDisposable mDisposables;
    @Inject
    Activity mActivity;

    @Override
    public void destory() {
        mDisposables.dispose();
        mActivity = null;
    }
}
