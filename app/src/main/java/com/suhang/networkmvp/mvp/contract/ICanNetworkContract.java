package com.suhang.networkmvp.mvp.contract;


import com.suhang.networkmvp.mvp.IPresenter;

/**
 * Created by 苏杭 on 2017/1/24 11:26.
 * 需要网络访问功能的继承这两个接口
 */

public interface ICanNetworkContract {
    interface ICanNetworkView extends INetworkContract.INetworkView {

    }

    interface ICanNetworkPresenter extends IPresenter {

    }

}
