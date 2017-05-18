package com.suhang.networkmvp.dagger.component;

import com.suhang.layoutfinderannotation.dagger.GenSubComponent;
import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.dagger.module.BaseModule;

/**
 * Created by 苏杭 on 2017/5/18 14:58.
 */
@GenSubComponent(name = "BaseComponent",scope = BaseScope.class,modules = BaseModule.class)
public interface BaseGen {
}
