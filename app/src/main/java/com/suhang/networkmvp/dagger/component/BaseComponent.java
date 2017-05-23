package com.suhang.networkmvp.dagger.component;


import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.dagger.module.BlankModule;

import dagger.Subcomponent;

/**
 * Created by 苏杭 on 2017/1/20 16:26.
 */

@BaseScope
@Subcomponent(modules = BaseModule.class)
public interface BaseComponent {
    BlankComponent providerBlankComponent(BlankModule blankModule);
}
