package com.suhang.networkmvp.binding.event;

import android.view.View;

import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder;
import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.mvp.event.ClickEvent;
import com.suhang.networkmvp.mvp.event.ItemClickEvent;
import com.suhang.networkmvp.mvp.translator.BaseTranslator;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/5/2 15:04.
 */
public class BindingAdapterEvent<T extends BaseTranslator,V extends BaseViewHolder> {
    T mTranslator;

    V mHolder;

    public void setTranslator(T translator) {
        mTranslator = translator;
    }

    public void setHolder(V v) {
        mHolder = v;
    }

    public void onClick(View view) {
        LogUtil.i("啊啊啊"+mHolder);
        ItemClickEvent clickEvent = new ItemClickEvent(mHolder.getAdapterPosition());
        mTranslator.post(clickEvent);
    }
}
