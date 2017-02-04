package com.suhang.networkmvp.dagger.component;


import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.dagger.module.AttentionModule;
import com.suhang.networkmvp.dagger.module.AttentionOnStartModule;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.dagger.module.HomeModule;
import com.suhang.networkmvp.dagger.module.MainModule;

import dagger.Subcomponent;

/**
 * Created by 苏杭 on 2017/1/20 16:26.
 */

@BaseScope
@Subcomponent(modules = BaseModule.class)
public interface BaseComponent {
    /**
     * 暴露子Component
     * @param module
     * @return
     */
    MainComponent getMainComponent(MainModule module);
    HomeComponent getHomeComponent(HomeModule module);
    AttentionComponent getAttentionComponent(AttentionModule module);
    AttentionOnStartComponent getAttentionOnStartComponent(AttentionOnStartModule module);
}
