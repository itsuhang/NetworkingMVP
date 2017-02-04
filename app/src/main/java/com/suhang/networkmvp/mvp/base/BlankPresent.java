package com.suhang.networkmvp.mvp.base;


import com.suhang.networkmvp.mvp.IView;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 15:59.
 */
public class BlankPresent extends BasePresenter<IView,BlankModel> {
    @Inject
    public BlankPresent(IView iView) {
        super(iView);
    }
}
