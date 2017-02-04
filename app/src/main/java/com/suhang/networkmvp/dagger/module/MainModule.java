package com.suhang.networkmvp.dagger.module;


import com.suhang.networkmvp.annotation.ActivityScope;
import com.suhang.networkmvp.mvp.contract.IMainContract;

import dagger.Module;

/**
 * Created by 苏杭 on 2017/1/20 17:03.
 */

@ActivityScope
@Module
public class MainModule extends NetworkModule<IMainContract.IMainView>{
    public MainModule(IMainContract.IMainView view) {
        super(view);
    }
}
