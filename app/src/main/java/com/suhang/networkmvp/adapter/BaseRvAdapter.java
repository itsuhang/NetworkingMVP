package com.suhang.networkmvp.adapter;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;
import android.widget.Toast;

import com.suhang.networkmvp.BR;
import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder;
import com.suhang.networkmvp.binding.event.BindingAdapterEvent;
import com.suhang.networkmvp.constants.ErrorCode;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.function.SubstribeManager;
import com.suhang.networkmvp.interfaces.IAdapterHelper;
import com.suhang.networkmvp.mvp.result.ErrorResult;
import com.suhang.networkmvp.utils.LogUtil;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2016/11/9 21:50.
 */

public abstract class BaseRvAdapter<T extends BaseViewHolder, V> extends RecyclerView.Adapter<T> implements IAdapterHelper {
    //基类内部错误tag
    private static final int ERROR_TAG = -1;
    @Inject
    Context mContext;
    @Inject
    Activity mActivity;

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    CompositeDisposable mDisposables;

    List<V> mList;

    @Inject
    SubstribeManager mManager;

    BindingAdapterEvent<T> mEvent;

    private int mTotalCount = 0;

    private int mTotalPage = 0;

    public Activity getActivity() {
        return mActivity;
    }

    public Context getContext() {
        return mContext;
    }


    public BaseRvAdapter() {
        mList = new ArrayList<>();
    }

    public void notifyDataSetChanged(List<V> v) {
        mList.clear();
        if (v != null && v.size() > 0) {
            mList.addAll(v);
        }
        notifyDataSetChanged();
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

    @Override
    public T onCreateViewHolder(ViewGroup parent, int viewType) {
        return onCreateHolder(parent, viewType);
    }

    public abstract void onBindHolder(T holder, V v);

    public abstract T onCreateHolder(ViewGroup parent, int viewType);

    @Override
    public void onBindViewHolder(T holder, int position) {
        setBindingEvent(holder);
        setBindingData(holder.mBinding);
        try {
            onBindHolder(holder, mList.get(position));
        } catch (Exception e) {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_RVADAPTER_BIND, ErrorCode.ERROR_DESC_RVADAPTER_BIND + "\n" + e.getMessage());
            mManager.post(new ErrorResult(errorBean, ERROR_TAG));
        }
    }

    /**
     * 加载更多(合并数据)
     */
    public void loadMore(List<V> beans) {
        if (getNextPage() > mTotalPage) {
            Toast.makeText(getContext(), "没有更多了", Toast.LENGTH_SHORT).show();
        } else {
            if (mList != null && mList.size() > 0) {
                mList.addAll(beans);
            }
        }
        notifyDataSetChanged();
    }

    public void notifyDelete(int position, List<V> beans) {
        mList.clear();
        mList.addAll(beans);
        notifyItemRemoved(position);
        notifyItemChanged(beans.size() - 1);
    }

    public void notifyDelete(List<Integer> positions, List<V> beans) {
        int start = mList.size() - positions.size() - 1;
        mList.clear();
        mList.addAll(beans);
        Collections.sort(positions, (o1, o2) -> {
            if (o1 > o2) {
                return -1;
            } else if (Objects.equals(o1, o2)) {
                return 0;
            } else {
                return 1;
            }
        });
        for (Integer position : positions) {
            notifyItemRemoved(position);
        }
        LogUtil.i("啊啊啊" + start + "   " + positions.size());
        notifyItemRangeChanged(start, positions.size());
    }

    /**
     * 得到每页数据大小
     */
    public abstract int getPageSize();

    @Override
    public void setTotalCount(int count) {
        try {
            if (mTotalCount % getPageSize() == 0) {
                mTotalPage = mTotalCount / getPageSize();
            } else {
                mTotalPage = mTotalCount / getPageSize() + 1;
            }
        } catch (Exception e) {
        }
        mTotalCount = count;
    }

    public int getTotalCount() {
        return mTotalCount;
    }

    @Override
    public int getCurrentCount() {
        return getItemCount();
    }


    /**
     * 获取下一页页数
     */
    public int getNextPage() {
        return getCurrentPage() + 1;
    }

    /**
     * 获取当前所在页数
     */
    public int getCurrentPage() {
        int page;
        try {
            if (getItemCount() % getPageSize() == 0) {
                page = getItemCount() / getPageSize();
            } else {
                page = getItemCount() / getPageSize() + 1;
            }
        } catch (Exception e) {
            page = 0;
        }
        return page;
    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public int getMaxCount() {
        return getPageSize();
    }

    public void destory() {
        mDisposables.dispose();
    }
}
