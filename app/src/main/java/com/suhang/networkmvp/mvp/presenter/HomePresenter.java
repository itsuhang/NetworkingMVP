package com.suhang.networkmvp.mvp.presenter;


import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.mvp.base.BasePresenter;
import com.suhang.networkmvp.mvp.contract.IHomeContract;
import com.suhang.networkmvp.mvp.model.HomeModel;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/21 16:13.
 */
@FragmentScope
public class HomePresenter extends BasePresenter<IHomeContract.IHomeView,HomeModel> implements IHomeContract.IHomePresenter{
    @Inject
    public HomePresenter(IHomeContract.IHomeView iHomeView) {
        super(iHomeView);
    }

    @Override
    public void doLog() {
        getModel().log();
        getView().log();
    }
}
