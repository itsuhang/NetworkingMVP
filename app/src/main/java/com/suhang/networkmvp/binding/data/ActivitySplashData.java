package com.suhang.networkmvp.binding.data;

import android.databinding.ObservableField;

/**
 * Created by 苏杭 on 2017/2/6 17:47.
 */

public class ActivitySplashData {
    public ObservableField<String> da = new ObservableField<>();

    public ActivitySplashData() {
        da.set("初始值");
    }
}
