package com.suhang.networkmvp.mvp.presenter;


import com.suhang.networkmvp.annotation.ActivityScope;
import com.suhang.networkmvp.mvp.base.BasePresenter;
import com.suhang.networkmvp.mvp.contract.IMainContract;
import com.suhang.networkmvp.mvp.model.MainModel;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/21 16:13.
 */
@ActivityScope
public class MainPresenter extends BasePresenter<IMainContract.IMainView,MainModel> implements IMainContract.IMainPresenter{
    @Inject
    public MainPresenter(IMainContract.IMainView view) {
        super(view);
    }

    @Override
    public void doLog() {
        getModel().log();
        getView().log();
    }
}
