package com.suhang.networkmvp.dagger.module;

import com.suhang.networkmvp.adapter.HomeRvAdapter;
import com.suhang.networkmvp.databinding.ItemHomeBinding;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 苏杭 on 2017/4/27 13:58.
 */
@Module
public class BlankModule {
    public BlankModule() {
    }

    @Provides
    HomeRvAdapter getHomeAdapter() {
        return new HomeRvAdapter();
    }
}
