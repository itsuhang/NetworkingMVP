package com.suhang.networkmvp.dagger.component;


import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.AttentionOnStartModule;
import com.suhang.networkmvp.ui.pager.AttentionOnePager;
import com.suhang.networkmvp.ui.pager.AttentionTwoPager;

import dagger.Subcomponent;

/**
 * Created by 苏杭 on 2017/2/3 17:11.
 */
@PagerScope
@Subcomponent(modules = AttentionOnStartModule.class)
public interface AttentionOnStartComponent {
    void inject(AttentionOnePager pager);
    void inject(AttentionTwoPager pager);
}
