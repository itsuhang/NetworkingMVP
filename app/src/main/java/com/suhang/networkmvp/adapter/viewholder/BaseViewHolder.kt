package com.suhang.networkmvp.adapter.viewholder

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView
import android.view.View

open class BaseViewHolder<T : ViewDataBinding>(view: View) : RecyclerView.ViewHolder(view) {
    var mBinding: T = DataBindingUtil.bind<T>(view)
    init {
        this.mBinding.executePendingBindings()
    }
}