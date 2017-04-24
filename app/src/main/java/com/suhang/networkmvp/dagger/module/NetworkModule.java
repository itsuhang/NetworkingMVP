package com.suhang.networkmvp.dagger.module;


import com.suhang.networkmvp.mvp.contract.INetworkContract;

import dagger.Module;
import dagger.Provides;

/**
 * Created by 苏杭 on 2017/1/24 11:48.
 * 具有网络访问功能的基类module
 */

@Module
public abstract class NetworkModule<T extends INetworkContract.INetworkView> extends ParentModule<T>{

    public NetworkModule(T view) {
        super(view);
    }

    /**
     * 提供INetworkContract.INetworkView
     * @return
     */
    @Provides
    INetworkContract.INetworkView provideNetworkView() {
        return mView;
    }
}
