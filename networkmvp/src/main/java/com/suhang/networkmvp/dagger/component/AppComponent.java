package com.suhang.networkmvp.dagger.component;


import com.suhang.networkmvp.dagger.module.AppModule;
import com.suhang.networkmvp.dagger.module.BaseModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by 苏杭 on 2017/1/22 17:32.
 */
@Singleton
@Component(modules = AppModule.class)
public interface AppComponent {
    /**
     * 暴露子Component
     * @return
     */
    BaseComponent baseComponent(BaseModule baseModule);
}
