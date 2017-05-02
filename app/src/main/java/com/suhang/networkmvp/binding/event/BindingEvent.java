package com.suhang.networkmvp.binding.event;

import android.view.View;

import com.suhang.networkmvp.mvp.event.ClickEvent;
import com.suhang.networkmvp.mvp.translator.BaseTranslator;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/5/2 15:04.
 */
public class BindingEvent<T extends BaseTranslator> {
    @Inject
    T mTranslator;
    @Inject
    public BindingEvent() {
    }

    public void onClick(View view) {
        mTranslator.post(new ClickEvent(view.getId()));
    }
}
