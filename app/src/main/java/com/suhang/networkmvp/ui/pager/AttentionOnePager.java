package com.suhang.networkmvp.ui.pager;

import android.app.Activity;
import android.util.ArrayMap;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.AttentionOnStartModule;
import com.suhang.networkmvp.databinding.PagerAttentionOneBinding;
import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.mvp.contract.IAttentionContract;
import com.suhang.networkmvp.mvp.model.NetworkModel2;
import com.suhang.networkmvp.mvp.presenter.AttentionPresenter;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

@PagerScope
public class AttentionOnePager extends BasePager<AttentionPresenter, PagerAttentionOneBinding> implements IAttentionContract.IAttentionView {
	@Inject
	NetworkModel2 mModel2;

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
		addSubscribe(subscribeSuccess().subscribe(successResult -> {
			LogUtil.i("啊啊啊" + successResult.getResult(AppMain.class).getTotal() + "  " + Thread.currentThread());
		}));
		addSubscribe(subscribeError().subscribe(errorResult -> {
			LogUtil.i("啊啊啊" + errorResult.getResult() + " " + Thread.currentThread());
		}));
		addSubscribe(subscribLoading().subscribe(loadingResult -> {
			if (loadingResult.isLoading()) {
				LogUtil.i("啊啊啊" + "加载开始" + Thread.currentThread());
			} else {
				LogUtil.i("啊啊啊" + "加载结束" + Thread.currentThread());
			}
		}));
		mModel2.loadPostDataWrap(AppMain.class, new ArrayMap<>(), false, 100);
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
