package com.suhang.networkmvp.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.suhang.networkmvp.BR;
import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder;
import com.suhang.networkmvp.binding.event.BindingAdapterEvent;
import com.suhang.networkmvp.constants.ErrorCode;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.function.SubstribeManager;
import com.suhang.networkmvp.interfaces.IAdapterHelper;
import com.suhang.networkmvp.mvp.model.BaseModel;
import com.suhang.networkmvp.mvp.result.ErrorResult;

import java.lang.reflect.Field;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2016/11/9 21:50.
 */

public abstract class BaseRvAdapter<T extends BaseViewHolder,V extends BaseModel> extends RecyclerView.Adapter<T> implements IAdapterHelper {
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
     V mModel;

    @Inject
    SubstribeManager mManager;

    BindingAdapterEvent<T> mEvent;



    private int mNetItemCount;
    private int mMaxCount = 1;

    public Activity getActivity() {
        return mActivity;
    }

    public Context getContext() {
        return mContext;
    }

    public BaseRvAdapter() {
    }

    /**
     * 绑定事件类(暂不使用)
     */
    protected void setBindingEvent(T t) {
        mEvent = new BindingAdapterEvent<>();
        mEvent.setHolder(t);
        mEvent.setManager(mManager);
        t.mBinding.setVariable(BR.event, mEvent);
    }

    /**
     * 绑定数据类(暂不使用)
     */
    protected void setBindingData(ViewDataBinding binding) {
        try {
            Field mData = binding.getClass().getDeclaredField("mData");
            binding.setVariable(BR.data, mData.getType().newInstance());
        } catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
            mManager.post(new ErrorResult(errorBean, ERROR_TAG));
        }
    }

    public SubstribeManager getSM() {
        return mManager;
    }

    public abstract int getCount();

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    public abstract void onBindHolder(T holder, int position);

    public abstract T onCreateHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(T holder, int position) {
        setBindingEvent(holder);
        setBindingData(holder.mBinding);
        onBindHolder(holder,position);
    }

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
