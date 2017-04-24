package com.suhang.networkmvp.dagger.module;

import com.suhang.networkmvp.mvp.base.IBlankView;

import dagger.Module;

/**
 * Created by 苏杭 on 2017/4/24 15:39.
 */
@Module
public class BlankModule extends ParentModule<IBlankView>{
    public BlankModule(IBlankView view) {
        super(view);
    }
}
