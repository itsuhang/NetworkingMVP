package com.suhang.networkmvp.dagger.component;

import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.ui.activity.MainActivity;
import com.suhang.networkmvp.ui.activity.SplashActivity;
import com.suhang.networkmvp.ui.fragment.AttentionFragment;
import com.suhang.networkmvp.ui.fragment.HomeFragment;
import com.suhang.networkmvp.ui.pager.AttentionOnePager;
import com.suhang.networkmvp.ui.pager.AttentionTwoPager;

import dagger.Subcomponent;

@Subcomponent(modules = BlankModule.class)
public interface BlankComponent {
    void inject(MainActivity activity);
    void inject(SplashActivity activity);
    void inject(AttentionFragment activity);
    void inject(HomeFragment activity);
    void inject(AttentionOnePager activity);
    void inject(AttentionTwoPager activity);
}