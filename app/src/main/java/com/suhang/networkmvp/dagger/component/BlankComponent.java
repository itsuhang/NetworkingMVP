package com.suhang.networkmvp.dagger.component;


import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.ui.activity.SplashActivity;
import com.suhang.networkmvp.ui.pager.AttentionTwoPager;

import dagger.Subcomponent;

/**
 * Created by 苏杭 on 2017/1/20 17:31.
 */
@Subcomponent(modules = BlankModule.class)
public interface BlankComponent {
    void inject(SplashActivity activity);
}
