package com.suhang.networkmvp.dagger.module;


import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.mvp.contract.IHomeContract;

import dagger.Module;

/**
 * Created by 苏杭 on 2017/1/20 17:03.
 */

@FragmentScope
@Module
public class HomeModule extends NetworkModule<IHomeContract.IHomeView>{
    public HomeModule(IHomeContract.IHomeView view) {
        super(view);
    }
}
