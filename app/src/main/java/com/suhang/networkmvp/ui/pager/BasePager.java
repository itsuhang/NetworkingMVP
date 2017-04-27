package com.suhang.networkmvp.ui.pager;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.application.App;
import com.suhang.networkmvp.dagger.component.BaseComponent;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.event.BaseResult;
import com.suhang.networkmvp.event.ErrorCode;
import com.suhang.networkmvp.event.ErrorResult;
import com.suhang.networkmvp.function.RxBus;

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

public abstract class BasePager{
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
    RxBus mRxBus;

	private boolean isRegisterEventBus;
	private View mRootView;

	public BasePager(Activity activity) {
		mBaseComponent = ((App) activity.getApplication()).getAppComponent().baseComponent(new BaseModule(activity));
		injectDagger();
		subscribeEvent();
		setLayout();
		if (mActivity == null) {
			throw new RuntimeException("injectDagger()方法没有实现,或实现不正确");
		}
	}

	/**
	 * 找到被@Binding注解的ViewDataBinding属性,并赋值
	 */
	//TODO 使用apt实现该功能
	private void setLayout() {
		boolean isExist = false;
		for (Field field : getClass().getFields()) {
			Binding annotation = field.getAnnotation(Binding.class);
			if (annotation != null) {
				isExist = true;
				int id = annotation.id();
				mRootView = View.inflate(mContext, id, null);
				ViewDataBinding viewDataBinding = DataBindingUtil.bind(mRootView);
				try {
					field.set(this, viewDataBinding);
					initEvent();
				} catch (IllegalAccessException e) {
					ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_DATABINDING_FIELD, ErrorCode.ERROR_DESC_DATABINDING_FIELD);
					mRxBus.post(new ErrorResult(errorBean, ERROR_TAG));
				}
			}
		}
		if (!isExist) {
			ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_DATABINDING_NOFIELD, ErrorCode.ERROR_DESC_DATABINDING_NOFIELD);
			mRxBus.post(new ErrorResult(errorBean, ERROR_TAG));
		}
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
	 * 获取RxBus,可进行订阅操作
	 *
	 * @return
	 */
	protected RxBus getRxBus() {
		return mRxBus;
	}

	/**
	 * 订阅成功事件(订阅后才可收到该事件,订阅要在获取数据之前进行)
	 * @param aClass 继承BaseResult的结果类的字节码
	 * @param <V>
	 * @return
	 */
	protected <V extends BaseResult> Flowable<V> subscribe(Class<V> aClass){
		return getRxBus().toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop();
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
		return mRootView;
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

//	/**
//	 * 绑定事件类(暂不使用)
//	 */
//	protected void setBindingEvent() {
//		try {
//			Field mEvent = mBinding.getClass().getDeclaredField("mEvent");
//			mBinding.setVariable(BR.event, mEvent.getType().newInstance());
//		} catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
//			ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
//			mRxBus.post(new ErrorResult(errorBean,ERROR_TAG));
//		}
//	}
//
//	/**
//	 * 绑定数据类(暂不使用)
//	 */
//	protected void setBindingData() {
//		try {
//			Field mData = mBinding.getClass().getDeclaredField("mData");
//			mBinding.setVariable(BR.data, mData.getType().newInstance());
//		} catch (NoSuchFieldException | InstantiationException | IllegalAccessException e) {
//			ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
//			mRxBus.post(new ErrorResult(errorBean,ERROR_TAG));
//		}
//	}

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
	}

}
