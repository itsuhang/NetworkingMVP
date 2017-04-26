package com.suhang.networkmvp.ui.pager;

import android.app.Activity;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.AttentionOnStartModule;
import com.suhang.networkmvp.databinding.PagerAttentionTwoBinding;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.domain.GithubBean;
import com.suhang.networkmvp.event.SuccessResult;
import com.suhang.networkmvp.interfaces.INetworkOtherService;
import com.suhang.networkmvp.mvp.contract.IAttentionContract;
import com.suhang.networkmvp.mvp.model.NetworkModel2;
import com.suhang.networkmvp.mvp.presenter.AttentionPresenter;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

@PagerScope
public class AttentionTwoPager extends BasePager<AttentionPresenter,PagerAttentionTwoBinding> implements IAttentionContract.IAttentionView {
    @Inject
    NetworkModel2<INetworkOtherService> mModel2;

    public AttentionTwoPager(Activity activity) {
        super(activity);
        bind(R.layout.pager_attention_two);
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getAttentionOnStartComponent(new AttentionOnStartModule(this)).inject(this);
    }

    @Override
    public void initData() {
        addSubscribe(subscribe(SuccessResult.class).subscribe(successResult -> {
            getBinding().tv.setText(successResult.getResult(GithubBean.class).toString());
        }));
        mModel2.loadGetData(GithubBean.class,"2/1",null,100);
    }

    @Override
    public void showError(ErrorBean e, int tag) {

    }

    @Override
    public void showLoading(int tag) {

    }

    @Override
    public void hideLoading(int tag) {

    }

    @Override
    public void log() {

    }

    @Override
    public void setData(ErrorBean e, int tag) {

    }

    @Override
    public void progress(int precent, int tag) {

    }
}
