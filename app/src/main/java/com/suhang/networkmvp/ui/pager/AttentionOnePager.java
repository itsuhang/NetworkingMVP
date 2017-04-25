package com.suhang.networkmvp.ui.pager;

import android.app.Activity;
import android.util.ArrayMap;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.AttentionOnStartModule;
import com.suhang.networkmvp.databinding.PagerAttentionOneBinding;
import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.domain.ErrorResult;
import com.suhang.networkmvp.domain.SuccessResult;
import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.mvp.contract.IAttentionContract;
import com.suhang.networkmvp.mvp.model.NetworkModel2;
import com.suhang.networkmvp.mvp.presenter.AttentionPresenter;
import com.suhang.networkmvp.utils.LogUtil;
import com.suhang.networkmvp.utils.RxUtil;

import javax.inject.Inject;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

@PagerScope
public class AttentionOnePager extends BasePager<AttentionPresenter,PagerAttentionOneBinding> implements IAttentionContract.IAttentionView {
    @Inject
    NetworkModel2 mModel2;

    @Inject
    RxBus mRxBus;
    public AttentionOnePager(Activity activity) {
        super(activity);
        bind(R.layout.pager_attention_one);

    }

    @Override
    public void log() {

    }

    @Override
    protected void initEvent() {
        getBinding().data.setOnClickListener(v -> {
            getPresenter().doRefresh();
            getPresenter().doLoadMore();
        });
    }

    @Override
    public void setData(ErrorBean e, int tag) {
        AppMain b = (AppMain) e;
        getBinding().data.setText(b.toString());
    }

    @Override
    public void progress(int precent, int tag) {

    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getAttentionOnStartComponent(new AttentionOnStartModule(this)).inject(this);
    }

    @Override
    public void initData() {
//        getPresenter().doLog();
        mModel2.loadPostData(true,AppMain.class,new ArrayMap<>(),false,100);
        mDisposables.add(mRxBus.toFlowable(SuccessResult.class).subscribe(appMain -> {
            LogUtil.i("啊啊啊"+appMain.getResult(AppMain.class).getTotal());
        }));
        mDisposables.add(mRxBus.toFlowable(ErrorResult.class).subscribe(errorResult -> {
            LogUtil.i("啊啊啊"+errorResult.getResult());
        }));
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
}
