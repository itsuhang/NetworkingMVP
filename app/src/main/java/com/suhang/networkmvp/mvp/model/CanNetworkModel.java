package com.suhang.networkmvp.mvp.model;


import com.suhang.networkmvp.mvp.base.BaseModel;
import com.suhang.networkmvp.mvp.presenter.NetworkPresenter;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 11:30.
 * 若需要网络访问功能,则继承该Model
 */

public class CanNetworkModel extends BaseModel {
    @Inject
    NetworkPresenter mNetworkPresenter;

    @Override
    public void destory() {
        super.destory();
        mNetworkPresenter.detachView();
    }
}
