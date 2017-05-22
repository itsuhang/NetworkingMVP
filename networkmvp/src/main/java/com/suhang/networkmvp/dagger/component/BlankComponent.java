package com.suhang.networkmvp.dagger.component;

import com.suhang.networkmvp.dagger.module.BlankModule;

import dagger.Subcomponent;

@Subcomponent(modules = BlankModule.class)
public interface BlankComponent {
    void inject(Object o);
}