package com.suhang.networkmvp.mvp.contract;

/**
 * Created by 苏杭 on 2017/1/21 16:09.
 */

public interface IMainContract {
    interface IMainView extends ICanNetworkContract.ICanNetworkView {
        void log();
    }

    interface IMainPresenter extends ICanNetworkContract.ICanNetworkPresenter {
        void doLog();
    }
}
