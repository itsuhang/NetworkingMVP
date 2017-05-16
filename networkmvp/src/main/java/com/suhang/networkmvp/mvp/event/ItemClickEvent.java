package com.suhang.networkmvp.mvp.event;

import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder;

/**
 * Created by 苏杭 on 2017/5/2 10:56.
 */

public class ItemClickEvent extends BaseEvent {
    private BaseViewHolder mHolder;

    public BaseViewHolder getHolder() {
        return mHolder;
    }

    public void setHolder(BaseViewHolder holder) {
        this.mHolder = holder;
    }

    public ItemClickEvent(BaseViewHolder holder) {
        this.mHolder = holder;
    }
}
