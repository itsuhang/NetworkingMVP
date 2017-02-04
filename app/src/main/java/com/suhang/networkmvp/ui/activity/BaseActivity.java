package com.suhang.networkmvp.ui.activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.suhang.networkmvp.BR;
import com.suhang.networkmvp.application.App;
import com.suhang.networkmvp.dagger.component.BaseComponent;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.domain.ErrorCode;
import com.suhang.networkmvp.mvp.IView;
import com.suhang.networkmvp.mvp.base.BasePresenter;
import com.suhang.networkmvp.utils.DialogHelp;
import com.suhang.networkmvp.utils.InputLeakUtil;
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
public abstract class BaseActivity<T extends BasePresenter, E extends ViewDataBinding> extends AppCompatActivity implements IView {
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

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mDialog = DialogHelp.getWaitDialog(this);
        mBaseComponent = ((App)getApplication()).getAppComponent().baseComponent(new BaseModule(this));
        injectDagger();
        EventBus.getDefault().register(this);
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

    /**
     * 绑定布局，在onCreate()中调用
     */
    protected void bind(@LayoutRes int id) {
        mBinding = DataBindingUtil.setContentView(this, id);
        setBindingEvent();
        setBindingData();
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

    @Override
    public Activity provideActivity() {
        return this;
    }

    /**
     * 隐藏软键盘
     */
    protected void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    /**
     * 沉浸状态栏偏移
     */
    protected void immerseUI(View view) {
        view.setPadding(0, ScreenUtils.getStatusBarHeight(this), 0, 0);
    }


    /**
     * 显示软键盘
     */
    protected void showKeyboard(EditText et) {
        et.requestFocus();
        ((InputMethodManager) getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE)).showSoftInput(et, InputMethodManager.SHOW_IMPLICIT);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mDisposables != null) {
            mDisposables.dispose();
        }
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        EventBus.getDefault().unregister(this);
        //处理InputMethodManager导致的内存泄漏
        InputLeakUtil.fixInputMethodManager(this);
    }
}
