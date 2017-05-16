package com.suhang.networkmvp.ui.pager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.suhang.layoutfinder.ContextProvider;
import com.suhang.layoutfinder.LayoutFinder;
import com.suhang.layoutfinderannotation.BindLayout;
import com.suhang.networkmvp.BR;
import com.suhang.networkmvp.application.App;
import com.suhang.networkmvp.binding.event.BaseData;
import com.suhang.networkmvp.constants.ErrorCode;
import com.suhang.networkmvp.dagger.component.BaseComponent;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.function.SubstribeManager;
import com.suhang.networkmvp.mvp.model.BaseModel;
import com.suhang.networkmvp.mvp.result.ErrorResult;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/1/24 15:12.
 * Fragment中不方便再嵌套Fragment时,用Pager页面
 */

public abstract class BasePager<T extends BaseModel, E extends ViewDataBinding> implements ContextProvider {
	//基类内部错误tag
	public static final int ERROR_TAG = -1;

	/**
	 * 基主件,用于注册子主件(dagger2)
	 */
	private BaseComponent mBaseComponent;

	//Rxjava事件集合，用于退出时取消事件
	@Inject
	CompositeDisposable mDisposables;

	//进度对话框
	@Inject
	Dialog mDialog;

	@Inject
	Activity mActivity;

	@Inject
	Context mContext;

	@Inject
	T mModel;

	@Inject
	SubstribeManager mManager;

	@BindLayout
	E mBinding;

	private boolean isRegisterEventBus;

	public BasePager(Activity activity) {
		mBaseComponent = ((App) activity.getApplication()).getAppComponent().baseComponent(new BaseModule(activity));
		injectDagger();
		subscribeEvent();
		bind(bindLayout());
		if (mActivity == null) {
			throw new RuntimeException("injectDagger()方法没有实现,或实现不正确");
		}
	}

	private void bind(int layout) {
		LayoutFinder.find(this, layout);
		setBindingEvent(mBinding);
		initEvent();
	}

	/**
	 * 绑定布局
	 *
	 * @return
	 */
	protected abstract int bindLayout();

	public T getModel() {
		return mModel;
	}

	/**
	 * 订阅事件
	 */
	protected abstract void subscribeEvent();

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
	 * 获取SubscribeManager,可进行订阅操作
	 */
	protected SubstribeManager getSM() {
		return mManager;
	}

	/**
	 * 获取对话框
	 */
	protected Dialog getDialog() {
		return mDialog;
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

	protected abstract BaseData getBindingData();

	/**
	 * 绑定事件类(暂不使用)
	 */
	protected void setBindingEvent(ViewDataBinding binding) {
		if (getBindingData() != null) {
			getBindingData().setManager(mManager);
			try {
				binding.setVariable(BR.event, getBindingData());
			} catch (Exception e) {
				ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
				mManager.post(new ErrorResult(errorBean, ERROR_TAG));
			}
		}
	}

	protected boolean isVisiable() {
		return getRootView().isShown();
	}

	public void setVisiable(boolean visiable) {
		getRootView().setVisibility(visiable ? View.VISIBLE : View.INVISIBLE);
	}

	public void destory() {
		mDisposables.dispose();
		//取消所有正在进行的网络任务
		if (mDialog != null) {
			mDialog.dismiss();
			mDialog = null;
		}
		if (isRegisterEventBus) {
			EventBus.getDefault().unregister(this);
		}

		mModel.destory();
	}

	@Override
	public Context providerContext() {
		return mContext;
	}
}
