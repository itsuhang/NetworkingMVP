package com.suhang.networkmvp.dagger.module;


import com.suhang.networkmvp.mvp.contract.INetworkContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 苏杭 on 2017/1/24 11:48.
 */

@Module
public abstract class NetworkModule<T extends INetworkContract.INetworkView> extends ParentModule<T>{

    public NetworkModule(T view) {
        super(view);
    }

    @Provides
    INetworkContract.INetworkView provideNetworkView() {
        return mView;
    }
}
