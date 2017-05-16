package com.suhang.networkmvp.binding.event;

import android.view.View;

import com.suhang.networkmvp.function.SubstribeManager;
import com.suhang.networkmvp.mvp.event.ClickEvent;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/5/2 15:04.
 */
public class BindingEvent {
    @Inject
    SubstribeManager mManager;
    @Inject
    public BindingEvent() {
    }

    public void onClick(View view) {
        mManager.post(new ClickEvent(view.getId()));
    }
}
