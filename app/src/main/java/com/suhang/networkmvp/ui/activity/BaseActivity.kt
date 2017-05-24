package com.suhang.networkmvp.ui.activity

import android.app.Activity
import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.suhang.layoutfinder.ContextProvider
import com.suhang.layoutfinder.LayoutFinder
import com.suhang.layoutfinderannotation.BindLayout
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.dagger.component.BaseComponent
import com.suhang.networkmvp.dagger.module.BaseModule
import com.suhang.networkmvp.function.SubstribeManager
import com.suhang.networkmvp.mvp.model.BaseModel
import com.suhang.networkmvp.utils.InputLeakUtil
import com.suhang.networkmvp.utils.ScreenUtils
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/1/21 10:52.
 */
abstract class BaseActivity<T : BaseModel, E : ViewDataBinding> : AppCompatActivity(), ContextProvider,AnkoLogger{

    /**
     * 基主件,用于注册子主件(dagger2)
     */
    /**
     * 获取父Component(dagger2)
     */
    lateinit var baseComponent: BaseComponent

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    lateinit var mDisposables: CompositeDisposable

    @Inject
    lateinit var mActivity: Activity

    @Inject
    lateinit var mContext: Context

    @Inject
    lateinit var model: T

    @BindLayout
    lateinit var mBinding: E

    /**
     * 获取RxBus,可进行订阅操作
     */
    @Inject
    lateinit var sm: SubstribeManager

    private var isRegisterEventBus: Boolean = false

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        baseComponent = (application as BaseApp).appComponent.baseComponent(BaseModule(this))
        injectDagger()
        subscribeEvent()
        bind(bindLayout())
    }

    /**
     * 根据提供的布局id进行绑定

     * @return 布局id
     */
    protected abstract fun bindLayout(): Int

    protected fun bind(@LayoutRes layout: Int) {
        LayoutFinder.find(this, layout)
        setContentView(mBinding.root)
        initData()
        initEvent()
    }

    /**
     * 订阅事件
     */
    protected abstract fun subscribeEvent()

    /**
     * 注册事件总线
     */
    protected fun registerEventBus() {
        EventBus.getDefault().register(this)
        isRegisterEventBus = true
    }


    /**
     * EventBus事件(防崩溃,需要则重写)
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun event(i: Int?) {

    }

    /**
     * 初始化数据
     */
    protected abstract fun initData()


    /**
     * 初始化事件
     */
    protected fun initEvent() {

    }

    /**
     * dagger2绑定(需要则重写) ps: getBaseComponent().getMainComponent(new
     * MainModule()).inject(this);
     */
    protected abstract fun injectDagger()

    /**
     * 隐藏软键盘
     */
    protected fun hideKeyboard() {
        val view = currentFocus
        if (view != null) {
            (applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 沉浸状态栏偏移
     */
    protected fun immerseUI(view: View) {
        view.setPadding(0, ScreenUtils.getStatusBarHeight(this), 0, 0)
    }


    /**
     * 显示软键盘
     */
    protected fun showKeyboard(et: EditText) {
        et.requestFocus()
        (applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDisposables.dispose()
        if (isRegisterEventBus) {
            EventBus.getDefault().unregister(this)
        }
        //处理InputMethodManager导致的内存泄漏
        model.destory()
        InputLeakUtil.fixInputMethodManager(this)
    }

    override fun providerContext(): Context {
        return this
    }

    companion object {
        //基类内部错误tag
        val ERROR_TAG = -1
    }
}
