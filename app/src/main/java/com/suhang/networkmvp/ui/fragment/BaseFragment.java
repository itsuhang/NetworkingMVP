package com.suhang.networkmvp.ui.fragment;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.application.BaseApp;
import com.suhang.networkmvp.dagger.component.BaseComponent;
import com.suhang.networkmvp.dagger.module.BaseModule;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.event.ErrorCode;
import com.suhang.networkmvp.event.ErrorResult;
import com.suhang.networkmvp.function.SubscribeManager;
import com.suhang.networkmvp.utils.LogUtil;
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
public abstract class BaseFragment extends Fragment {
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
    SubscribeManager mSm;

    //fragment布局缓存
    protected View cacheView;
    //是否为缓存布局
    protected boolean isCacheView;

    private boolean isRegisterEventBus;
    private View mRootView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBaseComponent = ((BaseApp) getActivity().getApplication()).getAppComponent().baseComponent(new BaseModule(getActivity()));
        injectDagger();
        subscribeEvent();
        setLayout();
        if (mActivity == null) {
            throw new RuntimeException("injectDagger()方法没有实现,或实现不正确");
        }
    }

    /**
     * 获得布局View,仅在onCreateView()方法中使用
     * @return
     */
    protected View getRootView() {
        if (cacheView == null) {
            cacheView = mRootView;
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
                } catch (IllegalAccessException e) {
                    ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_DATABINDING_FIELD, ErrorCode.ERROR_DESC_DATABINDING_FIELD);
                    mSm.post(new ErrorResult(errorBean, ERROR_TAG));
                }
            }
        }
        if (!isExist) {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_DATABINDING_NOFIELD, ErrorCode.ERROR_DESC_DATABINDING_NOFIELD);
            mSm.post(new ErrorResult(errorBean, ERROR_TAG));
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
    protected SubscribeManager getSm() {
        return mSm;
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

//    /**
//     * 绑定事件类(暂不使用)
//     */
//    protected void setBindingEvent() {
//        try {
//            Field mEvent = mBinding.getClass().getDeclaredField("mEvent");
//            mBinding.setVariable(BR.event, mEvent.getType().newInstance());
//        } catch (NoSuchFieldException | java.lang.InstantiationException | IllegalAccessException e) {
//            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
//            mRxBus.post(new ErrorResult(errorBean,ERROR_TAG));
//        }
//    }
//
//    /**
//     * 绑定数据类(暂不使用)
//     */
//    protected void setBindingData() {
//        try {
//            Field mData = mBinding.getClass().getDeclaredField("mData");
//            mBinding.setVariable(BR.data, mData.getType().newInstance());
//        } catch (NoSuchFieldException | java.lang.InstantiationException | IllegalAccessException e) {
//            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_BINDING, ErrorCode.ERROR_DESC_REFLECT_BINDING + "\n" + e.getMessage());
//            mRxBus.post(new ErrorResult(errorBean,ERROR_TAG));
//        }
//    }

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
