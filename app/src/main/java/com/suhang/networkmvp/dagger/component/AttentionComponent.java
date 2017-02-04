package com.suhang.networkmvp.dagger.component;


import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.dagger.module.AttentionModule;
import com.suhang.networkmvp.ui.fragment.AttentionFragment;

import dagger.Subcomponent;

/**
 * Created by 苏杭 on 2017/1/20 17:31.
 */

@FragmentScope
@Subcomponent(modules = AttentionModule.class)
public interface AttentionComponent {
    void inject(AttentionFragment fragment);
}
