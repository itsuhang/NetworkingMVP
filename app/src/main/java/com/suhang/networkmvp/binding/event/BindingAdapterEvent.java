package com.suhang.networkmvp.binding.event;

import android.view.View;

import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder;
import com.suhang.networkmvp.function.SubstribeManager;
import com.suhang.networkmvp.mvp.event.ItemClickEvent;

/**
 * Created by 苏杭 on 2017/5/2 15:04.
 */
public class BindingAdapterEvent<T extends BaseViewHolder> {
    SubstribeManager mManager;

    T mHolder;

    public void setManager(SubstribeManager manager) {
        mManager = manager;
    }

    public void setHolder(T t) {
        mHolder = t;
    }

    public void onClick(View view) {
        ItemClickEvent clickEvent = new ItemClickEvent(mHolder.getAdapterPosition());
        mManager.post(clickEvent);
    }
}
