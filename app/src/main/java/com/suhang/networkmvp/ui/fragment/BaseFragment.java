package com.suhang.networkmvp.ui.fragment;

import android.app.Activity;
import android.content.Context;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.suhang.layoutfinder.ContextProvider;
import com.suhang.layoutfinder.LayoutFinder;
import com.suhang.layoutfinderannotation.BindLayout;
import com.suhang.networkmvp.application.BaseApp;
import com.suhang.networkmvp.binding.data.BaseData;
import com.suhang.networkmvp.constants.ErrorCode;
import com.suhang.networkmvp.dagger.component.BaseComponent;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.function.SubstribeManager;
import com.suhang.networkmvp.mvp.model.BaseModel;
import com.suhang.networkmvp.mvp.result.ErrorResult;
import com.suhang.networkmvp.utils.ScreenUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;

import javax.inject.Inject;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/1/21 10:52.
 */
public abstract class BaseFragment<T extends BaseModel, E extends ViewDataBinding> extends Fragment implements ContextProvider {
	//基类内部错误tag
	public static final int ERROR_TAG = -1;

	/**
	 * 基主件,用于注册子主件(dagger2)
	 */
	private BaseComponent mBaseComponent;

	//Rxjava事件集合，用于退出时取消事件
	@Inject
	CompositeDisposable mDisposables;

	@Inject
	Activity mActivity;

	@Inject
	Context mContext;

	@Inject
	T mModel;

	@BindLayout
	E mBinding;

	@Inject
	SubstribeManager mManager;

	//fragment布局缓存
	protected View cacheView;
	//是否为缓存布局
	protected boolean isCacheView;

	private boolean isRegisterEventBus;

	@Override
	public void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mBaseComponent = ((BaseApp) getActivity().getApplication()).getAppComponent().baseComponent(new BaseModule(getActivity()));
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
	}

	/**
	 * 绑定布局
	 *
	 * @return
	 */
	protected abstract int bindLayout();

	@Nullable
	@Override
	public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
		onViewCreate(inflater, container, savedInstanceState);
		return getRootView();
	}

	/**
	 * 需要在绑定布局之前(onCreateView之前)做处理则覆盖此方法
	 *
	 * @param inflater
	 * @param container
	 * @param savedInstanceState
	 */
	protected void onViewCreate(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
	}

	/**
	 * 缓存Fragment的布局
	 * 获得布局View,仅在onCreateView()方法中使用
	 */
	protected View getRootView() {
		if (cacheView == null) {
			cacheView = mBinding.getRoot();
		} else {
			isCacheView = true;
			ViewGroup parent = (ViewGroup) cacheView.getParent();
			if (parent != null) {
				parent.removeView(cacheView);
			}
		}
		return cacheView;
	}

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

	@Override
	public void onActivityCreated(@Nullable Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		initData();
		initEvent();
	}


	/**
	 * EventBus事件(防崩溃,需要则重写)
	 */
	@Subscribe(threadMode = ThreadMode.MAIN)
	public void event(Integer i) {

	}

	/**
	 * 获取RxBus,可进行订阅操作
	 */
	protected SubstribeManager getSm() {
		return mManager;
	}

	/**
	 * 初始化数据
	 */
	protected abstract void initData();


	/**
	 * 初始化事件
	 */
	protected void initEvent() {

	}

	protected abstract BaseData getBindingData();

	/**
	 * 绑定事件类(暂不使用)
	 */
	protected void setBindingEvent(ViewDataBinding binding) {
		BaseData bindingData = getBindingData();
		if (bindingData != null) {
			bindingData.setManager(mManager);
			try {
				Class<?> aClass = Class.forName(BaseApp.DATABINDING_BR);
				Field field = aClass.getField(BaseApp.DATABINDING_DATA);
				int id = (int) field.get(null);
				binding.setVariable(id, bindingData);
			} catch (Exception e) {
				ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
				mManager.post(new ErrorResult(errorBean, ERROR_TAG));
			}
		}
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
	 * 隐藏软键盘
	 */
	protected void hideKeyboard() {
		View view = getActivity().getCurrentFocus();
		if (view != null) {
			((InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
					hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
		}
	}

	/**
	 * 沉浸状态栏偏移
	 */
	protected void immerseUI(View view) {
		view.setPadding(0, ScreenUtils.getStatusBarHeight(getContext()), 0, 0);
	}


	/**
	 * 显示软键盘
	 */
	protected void showKeyboard(EditText et) {
		et.requestFocus();
		((InputMethodManager) getActivity().getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (mDisposables != null) {
			mDisposables.dispose();
		}
		mModel.destory();
		EventBus.getDefault().unregister(this);
	}


	/**
	 * 有时会有Activity给关闭而内部Fragment不走onDestory()方法,则可手动调用此方法销毁数据
	 */
	public void destory() {
		mDisposables.dispose();
		//取消所有正在进行的网络任务
		if (isRegisterEventBus) {
			EventBus.getDefault().unregister(this);
		}
		mModel.destory();
	}

	@Override
	public Context providerContext() {
		return getContext();
	}
}
