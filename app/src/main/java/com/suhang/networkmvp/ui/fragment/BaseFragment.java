package com.suhang.networkmvp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.suhang.networkmvp.BR;
import com.suhang.networkmvp.application.BaseApp;
import com.suhang.networkmvp.dagger.component.BaseComponent;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.event.BaseResult;
import com.suhang.networkmvp.event.ErrorCode;
import com.suhang.networkmvp.event.ErrorResult;
import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.utils.DialogHelp;
import com.suhang.networkmvp.utils.ScreenUtils;

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
 * Created by 苏杭 on 2017/1/21 10:52.
 */
public abstract class BaseFragment<T extends ViewDataBinding> extends Fragment {
    //基类内部错误tag
    public static final int ERROR_TAG = -1;

    /**
     * 基主件,用于注册子主件(dagger2)
     */
    private BaseComponent mBaseComponent;

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    CompositeDisposable mDisposables;

    //databing类
    private T mBinding;

    //进度对话框
    @Inject
    Dialog mDialog;

    @Inject
    Activity mActivity;

    @Inject
    Context mContext;

    @Inject
    RxBus mRxBus;

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
        if (mActivity == null) {
            throw new RuntimeException("injectDagger()方法没有实现,或实现不正确");
        }
    }

    /**
     * 订阅事件
     */
    protected abstract void  subscribeEvent();

    /**
     * 注册事件总线
     */
    protected void registerEventBus() {
        EventBus.getDefault().register(this);
        isRegisterEventBus = true;
    }

    /**
     * 获取Binding类
     */
    protected T getBinding() {
        return mBinding;
    }

    /**
     * 获取对话框
     */
    protected Dialog getDialog() {
        return mDialog;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initData();
        initEvent();
    }

    /**
     * 绑定布局，在onCreateView（）中调用
     */
    protected View bind(LayoutInflater inflater, @Nullable ViewGroup container, @LayoutRes int id) {
        View view = inflater.inflate(id, container, false);
        mBinding = DataBindingUtil.bind(view);
        setBindingEvent();
        setBindingData();
        if (cacheView == null) {
            cacheView = view;
        } else {
            isCacheView = true;
            ViewGroup parent = (ViewGroup) cacheView.getParent();
            if (parent != null) {
                parent.removeView(cacheView);
            }
        }
        return cacheView;
    }


    /**
     * EventBus事件(防崩溃,需要则重写)
     */
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
     * 获得根布局
     */
    public View getRootView() {
        return mBinding.getRoot();
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

    /**
     * 绑定事件类(暂不使用)
     */
    protected void setBindingEvent() {
        try {
            Field mEvent = mBinding.getClass().getDeclaredField("mEvent");
            mBinding.setVariable(BR.event, mEvent.getType().newInstance());
        } catch (NoSuchFieldException | java.lang.InstantiationException | IllegalAccessException e) {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
            mRxBus.post(new ErrorResult(errorBean,ERROR_TAG));
        }
    }

    /**
     * 绑定数据类(暂不使用)
     */
    protected void setBindingData() {
        try {
            Field mData = mBinding.getClass().getDeclaredField("mData");
            mBinding.setVariable(BR.data, mData.getType().newInstance());
        } catch (NoSuchFieldException | java.lang.InstantiationException | IllegalAccessException e) {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
            mRxBus.post(new ErrorResult(errorBean,ERROR_TAG));
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
        EventBus.getDefault().unregister(this);
    }


    /**
     * 有时会有Activity给关闭而内部Fragment不走onDestory()方法,则可手动调用此方法销毁数据
     */
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
