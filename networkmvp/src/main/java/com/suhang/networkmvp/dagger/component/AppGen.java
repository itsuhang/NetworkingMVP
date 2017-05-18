package com.suhang.networkmvp.dagger.component;

import com.suhang.layoutfinderannotation.dagger.GenComponent;
import com.suhang.layoutfinderannotation.dagger.GenSub;
import com.suhang.networkmvp.dagger.module.AppModule;
import com.suhang.networkmvp.dagger.module.BaseModule;

import javax.inject.Singleton;

/**
 * Created by 苏杭 on 2017/5/18 14:58.
 */
@GenComponent(name = "AppComponent",scope = Singleton.class,modules = AppModule.class)
public interface AppGen {
    @GenSub(component = "AppComponent")
    BaseComponent providerBaseComponent(BaseModule module);
}
