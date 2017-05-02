package com.suhang.networkmvp.mvp.event;

import android.view.View;

/**
 * Created by 苏杭 on 2017/5/2 10:56.
 */

public class ClickEvent extends BaseEvent{
    private View mView;

    public ClickEvent(View view) {
        mView = view;
    }

    public View getView() {
        return mView;
    }

    public void setView(View view) {
        mView = view;
    }
}
