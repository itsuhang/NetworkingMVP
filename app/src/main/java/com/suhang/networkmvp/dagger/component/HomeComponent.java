package com.suhang.networkmvp.dagger.component;


import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.dagger.module.HomeModule;
import com.suhang.networkmvp.ui.fragment.HomeFragment;

import dagger.Subcomponent;

/**
 * Created by 苏杭 on 2017/1/20 17:31.
 */

@FragmentScope
@Subcomponent(modules = HomeModule.class)
public interface HomeComponent {
    void inject(HomeFragment fragment);
}
