package com.suhang.networkmvp.mvp.presenter;


import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.mvp.base.BasePresenter;
import com.suhang.networkmvp.mvp.contract.IAttentionContract;
import com.suhang.networkmvp.mvp.model.AttentionModel;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/21 16:13.
 */
@PagerScope
public class AttentionPresenter extends BasePresenter<IAttentionContract.IAttentionView,AttentionModel> implements IAttentionContract.IAttentionPresenter {
    @Inject
    public AttentionPresenter(IAttentionContract.IAttentionView iAttentionView) {
        super(iAttentionView);
    }

    @Override
    public void doLog() {
        getModel().log();
        getView().log();
    }
}
