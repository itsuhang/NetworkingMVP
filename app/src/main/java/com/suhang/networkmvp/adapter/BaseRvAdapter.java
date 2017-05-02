package com.suhang.networkmvp.adapter;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder;
import com.suhang.networkmvp.interfaces.IAdapterHelper;
import com.suhang.networkmvp.mvp.translator.BaseTranslator;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2016/11/9 21:50.
 */

public abstract class BaseRvAdapter<T extends BaseViewHolder,V extends BaseTranslator> extends RecyclerView.Adapter<T> implements IAdapterHelper {
    //基类内部错误tag
    private static final int ERROR_TAG = -1;
    @Inject
    Context mContext;
    @Inject
    Activity mActivity;

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    CompositeDisposable mDisposables;

    @Inject
     V mTranslator;

    private int mNetItemCount;
    private int mMaxCount = 1;

    public Activity getActivity() {
        return mActivity;
    }

    public Context getContext() {
        return mContext;
    }

    public V getBm() {
        return mTranslator;
    }

    public BaseRvAdapter() {
    }

    public abstract int getCount();

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent,viewType);
    }

    public abstract T onCreateHolder(ViewGroup parent, int viewType);

    @Override
    public int getNetItemCount() {
        return mNetItemCount;
    }

    @Override
    public int getCurrentCount() {
        return getItemCount();
    }

    @Override
    public int getItemCount() {
        return getCount();
    }

    @Override
    public int getMaxCount() {
        return mMaxCount;
    }

    public void destory() {
        mDisposables.dispose();
    }
}
