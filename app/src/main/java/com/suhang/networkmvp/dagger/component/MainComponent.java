package com.suhang.networkmvp.dagger.component;


import com.suhang.networkmvp.annotation.ActivityScope;
import com.suhang.networkmvp.dagger.module.MainModule;
import com.suhang.networkmvp.ui.activity.MainActivity;

import dagger.Subcomponent;

/**
 * Created by 苏杭 on 2017/1/20 17:31.
 */

@ActivityScope
@Subcomponent(modules = MainModule.class)
public interface MainComponent {
    void inject(MainActivity activity);
}
