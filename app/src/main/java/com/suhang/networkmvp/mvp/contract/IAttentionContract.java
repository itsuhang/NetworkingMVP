package com.suhang.networkmvp.mvp.contract;

/**
 * Created by 苏杭 on 2017/1/21 16:09.
 */

public interface IAttentionContract {
    interface IAttentionView extends ICanNetworkContract.ICanNetworkView,ICanRefreshContract.ICanNetworkView {
        void log();
    }

    interface IAttentionPresenter extends ICanNetworkContract.ICanNetworkPresenter,ICanRefreshContract.ICanNetworkPresenter {
        void doLog();
    }
}
