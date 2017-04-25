package com.suhang.networkmvp.ui.pager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.LayoutRes;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;


import com.suhang.networkmvp.BR;
import com.suhang.networkmvp.application.BaseApp;
import com.suhang.networkmvp.dagger.component.BaseComponent;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.domain.ErrorCode;
import com.suhang.networkmvp.domain.ErrorResult;
import com.suhang.networkmvp.domain.LoadingResult;
import com.suhang.networkmvp.domain.ProgressResult;
import com.suhang.networkmvp.domain.SuccessResult;
import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.mvp.IView;
import com.suhang.networkmvp.mvp.base.BasePresenter;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by 苏杭 on 2017/1/24 15:12.
 * Fragment中不方便再嵌套Fragment时,用Pager页面
 */

public abstract class BasePager<T extends BasePresenter, E extends ViewDataBinding> implements IView {
	//基类内部错误tag
	public static final int ERROR_TAG = -1;

	/**
	 * 基主件,用于注册子主件(dagger2)
	 */
	private BaseComponent mBaseComponent;

	//Rxjava事件集合，用于退出时取消事件
	@Inject
	CompositeDisposable mDisposables;

	//mvp中的presenter
	@Inject
	T mPresenter;

	//databing类
	private E mBinding;

	//进度对话框
	@Inject
	Dialog mDialog;

	@Inject
	Activity mActivity;

	@Inject
	Context mContext;

	@Inject
	RxBus mRxBus;

	private boolean isRegisterEventBus;

	public BasePager(Activity activity) {
		mBaseComponent = ((BaseApp) activity.getApplication()).getAppComponent().baseComponent(new BaseModule(activity));
		injectDagger();
		if (mActivity == null) {
			throw new RuntimeException("injectDagger()方法没有实现,或实现不正确");
		}
	}

	/**
	 * 注册事件总线
	 */
	protected void registerEventBus() {
		EventBus.getDefault().register(this);
		isRegisterEventBus = true;
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void event(Integer i) {

	}

	/**
	 * 获取RxBus,可进行订阅操作
	 *
	 * @return
	 */
	protected RxBus getRxBus() {
		return mRxBus;
	}

	/**
	 * 订阅成功事件(订阅后才可收到该事件,订阅要在获取数据之前进行)
	 *
	 * @return
	 */
	protected Flowable<SuccessResult> subscribeSuccess() {
		return mRxBus.toFlowable(SuccessResult.class).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop();
	}

	/**
	 * 订阅错误事件(订阅后才可收到该事件,订阅要在获取数据之前进行)
	 *
	 * @return
	 */
	protected Flowable<ErrorResult> subscribeError() {
		return mRxBus.toFlowable(ErrorResult.class).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop();
	}

	/**
	 * 订阅加载事件(订阅后才可收到该事件,订阅要在获取数据之前进行)
	 *
	 * @return
	 */
	protected Flowable<LoadingResult> subscribLoading() {
		return mRxBus.toFlowable(LoadingResult.class).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop();
	}

	/**
	 * 订阅进度事件(订阅后才可收到该事件,订阅要在获取数据之前进行)
	 *
	 * @return
	 */
	protected Flowable<ProgressResult> subscribeProgress() {
		return mRxBus.toFlowable(ProgressResult.class).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop();
	}


	/**
	 * 添加rx事件到回收集合中,请尽量使用该方法把所有的事件添加到该集合中
	 *
	 * @param disposable
	 */
	protected void addSubscribe(Disposable disposable) {
		mDisposables.add(disposable);
	}

	/**
	 * 获取Presenter
	 */
	public T getPresenter() {
		return mPresenter;
	}

	/**
	 * 获取Binding类
	 */
	protected E getBinding() {
		return mBinding;
	}

	/**
	 * 获取对话框
	 */
	protected Dialog getDialog() {
		return mDialog;
	}

	protected void bind(@LayoutRes int id) {
		View view = View.inflate(mContext, id, null);
		mBinding = DataBindingUtil.bind(view);
		setBindingEvent();
		setBindingData();
		initEvent();
	}

	/**
	 * 获取父Component(dagger2)
	 */
	protected BaseComponent getBaseComponent() {
		return mBaseComponent;
	}


	/**
	 * dagger2绑定(需要则重写) ps: getBaseComponent().getMainComponent(new
	 * MainModule()).inject(this);
	 */
	protected abstract void injectDagger();

	/**
	 * 获得根布局
	 */
	public View getRootView() {
		return mBinding.getRoot();
	}

	/**
	 * 初始化事件
	 */
	protected void initEvent() {

	}

	/**
	 * 初始化数据
	 */
	public abstract void initData();

	public Activity getActivity() {
		return mActivity;
	}

	public Context getContext() {
		return mContext;
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideKeyboard() {
		View view = getActivity().getCurrentFocus();
		if (view != null) {
			((InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
					hideSoftInputFromWindow(view.getWindowToken(), 0);
		}
	}


	private void showKeyboard(EditText et) {
		et.requestFocus();
		((InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(et, InputMethodManager.SHOW_FORCED);
	}

	/**
	 * 绑定事件类(暂不使用)
	 */
	protected void setBindingEvent() {
		try {
			Field mEvent = mBinding.getClass().getDeclaredField("mEvent");
			mBinding.setVariable(BR.event, mEvent.getType().newInstance());
		} catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
			showError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage()), ERROR_TAG);
		}
	}

	/**
	 * 绑定数据类(暂不使用)
	 */
	protected void setBindingData() {
		try {
			Field mData = mBinding.getClass().getDeclaredField("mData");
			mBinding.setVariable(BR.data, mData.getType().newInstance());
		} catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
			showError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage()), ERROR_TAG);
		}
	}

	@Override
	public Activity provideActivity() {
		return mActivity;
	}

	protected boolean isVisiable() {
		return getRootView().isShown();
	}

	public void setVisiable(boolean visiable) {
		getRootView().setVisibility(visiable ? View.VISIBLE : View.INVISIBLE);
	}

	public void destory() {
		mDisposables.dispose();
		if (mPresenter != null)
			mPresenter.detachView();
		//取消所有正在进行的网络任务
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		if (isRegisterEventBus) {
			EventBus.getDefault().unregister(this);
		}
	}

}
