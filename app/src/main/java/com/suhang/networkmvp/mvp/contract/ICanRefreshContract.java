package com.suhang.networkmvp.mvp.contract;

import com.suhang.networkmvp.mvp.IPresenter;
import com.suhang.networkmvp.mvp.IView;

/**
 * Created by 苏杭 on 2017/4/25 10:30.
 */

public interface ICanRefreshContract {
    interface ICanNetworkView extends IView {

    }

    interface ICanNetworkPresenter extends IPresenter {
        void doRefresh();

        void doLoadMore();
    }
}
