package com.suhang.networkmvp.binding.event;

import android.view.View;

import com.suhang.networkmvp.function.SubstribeManager;
import com.suhang.networkmvp.mvp.event.BindingEvent;

/**
 * Created by 苏杭 on 2017/5/2 15:04.
 */
public class BaseData {
    SubstribeManager mManager;
    public BaseData() {
    }

    public SubstribeManager getManager() {
        return mManager;
    }

    public void setManager(SubstribeManager manager) {
        mManager = manager;
    }

    public void onClick(View view) {
        mManager.post(new BindingEvent(view.getId(),null));
    }

    public void onEvent(View v) {
        mManager.post(new BindingEvent(v.getId(),this));
    }
}
