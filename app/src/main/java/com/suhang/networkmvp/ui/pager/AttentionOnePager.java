package com.suhang.networkmvp.ui.pager;

import android.app.Activity;
import android.util.ArrayMap;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.AttentionOnStartModule;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.PagerAttentionOneBinding;
import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.event.ErrorResult;
import com.suhang.networkmvp.event.LoadingResult;
import com.suhang.networkmvp.event.SuccessResult;
import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.interfaces.INetworkOtherService;
import com.suhang.networkmvp.interfaces.INetworkService;
import com.suhang.networkmvp.mvp.base.BlankPresent;
import com.suhang.networkmvp.mvp.base.IBlankView;
import com.suhang.networkmvp.mvp.contract.IAttentionContract;
import com.suhang.networkmvp.mvp.model.NetworkModel2;
import com.suhang.networkmvp.mvp.presenter.AttentionPresenter;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

@PagerScope
public class AttentionOnePager extends BasePager<BlankPresent, PagerAttentionOneBinding> implements IBlankView {
	@Inject
	NetworkModel2<INetworkService> mModel2;

	public AttentionOnePager(Activity activity) {
		super(activity);
		bind(R.layout.pager_attention_one);
	}


	@Override
	protected void initEvent() {
		getBinding().data.setOnClickListener(v -> {
		});
	}

	@Override
	protected void injectDagger() {
		getBaseComponent().getBlankComponent(new BlankModule(this)).inject(this);
	}

	@Override
	public void initData() {
		getBS().subscribe(this);
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


	@Override
	public void onLoading(LoadingResult loadingResult) {
		LogUtil.i("啊啊啊"+loadingResult.isLoading());
	}

	@Override
	public void onError(ErrorResult errorResult) {
	}

	@Override
	public void onSuccess(SuccessResult successResult) {
		LogUtil.i("啊啊啊"+successResult.getResult(AppMain.class));
	}
}
