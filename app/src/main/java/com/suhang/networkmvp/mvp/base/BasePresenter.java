package com.suhang.networkmvp.mvp.base;


import com.suhang.networkmvp.mvp.IModel;
import com.suhang.networkmvp.mvp.IPresenter;
import com.suhang.networkmvp.mvp.IView;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/21 14:58.
 */

public abstract class BasePresenter<T extends IView, E extends IModel> implements IPresenter {
    public static final String TAG = "BasePresenter";
    @Inject
    protected E mModel;
    @Inject
    protected T mView;
    public BasePresenter(T t) {
        mView = t;
    }

    public E getModel() {
        return mModel;
    }

    public T getView() {
        return mView;
    }

    @Override
    public void detachView() {
        mView = null;
        if (mModel != null) {
            mModel.destory();
        }
    }
}
