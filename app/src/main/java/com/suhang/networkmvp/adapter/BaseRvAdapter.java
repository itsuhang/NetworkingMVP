package com.suhang.networkmvp.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;


import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.interfaces.IAdapterHelper;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2016/11/9 21:50.
 */

public abstract class BaseRvAdapter<T extends ViewDataBinding> extends RecyclerView.Adapter implements IAdapterHelper {
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
    RxBus mRxBus;

    private int mNetItemCount;
    private int mMaxCount = 1;

    public Activity getActivity() {
        return mActivity;
    }

    public Context getContext() {
        return mContext;
    }

    public RxBus getRxBus() {
        return mRxBus;
    }


    public BaseRvAdapter() {
    }

    @Override
    public int getNetItemCount() {
        return mNetItemCount;
    }

    @Override
    public int getCurrentCount() {
        return getItemCount();
    }

    protected MyViewHolder bind(@LayoutRes int id) {
        View view = View.inflate(mContext, id, null);
        T mBinding = DataBindingUtil.bind(view);
//		setBindingEvent();
//		setBindingData();
        return new MyViewHolder(view, mBinding);
    }

    @Override
    public int getMaxCount() {
        return mMaxCount;
    }

//    /**
//     * 绑定事件类(暂不使用)
//     */
//    protected void setBindingEvent() {
//        try {
//            Field mEvent = mBinding.getClass().getDeclaredField("mEvent");
//            mBinding.setVariable(BR.event, mEvent.getType().newInstance());
//        } catch (NoSuchFieldException | java.lang.InstantiationException | IllegalAccessException e) {
//            mView.showError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage()), ERROR_TAG);
//        }
//    }
//
//    /**
//     * 绑定数据类(暂不使用)
//     */
//    protected void setBindingData() {
//        try {
//            Field mData = mBinding.getClass().getDeclaredField("mData");
//            mBinding.setVariable(BR.data, mData.getType().newInstance());
//        } catch (NoSuchFieldException | java.lang.InstantiationException | IllegalAccessException e) {
//            mView.showError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage()), ERROR_TAG);
//        }
//    }


    protected abstract MyViewHolder onCreateHolder(ViewGroup parent, int viewType);

    protected abstract void onBindHolder(MyViewHolder holder, int position);

    @Override
    @SuppressWarnings("unchecked")
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        onBindHolder((MyViewHolder) holder, position);
        mNetItemCount = setNetItemCount();
        mMaxCount = setMaxCount();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public T mBinding;

        public MyViewHolder(View itemView, T binding) {
            super(itemView);
            this.mBinding = binding;
            this.mBinding.executePendingBindings();
        }


    }

    public void destory() {
        mDisposables.dispose();
    }
}
