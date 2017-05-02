package com.suhang.networkmvp.adapter.viewholder;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.utils.LogUtil;

public class BaseViewHolder<T extends ViewDataBinding> extends RecyclerView.ViewHolder {
        public T mBinding;

        public BaseViewHolder(View view) {
            super(view);
            mBinding = DataBindingUtil.bind(view);
            this.mBinding.executePendingBindings();
        }
    }