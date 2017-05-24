package com.suhang.networkmvp.ui.pager

import android.app.Activity
import android.content.Context
import android.databinding.ViewDataBinding
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

import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

import javax.inject.Inject

import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger

/**
 * Created by 苏杭 on 2017/1/24 15:12.
 * Fragment中不方便再嵌套Fragment时,用Pager页面
 */

abstract class BasePager<T : BaseModel, E : ViewDataBinding>(activity: Activity) : ContextProvider,AnkoLogger {

    /**
     * 基主件,用于注册子主件(dagger2)
     */
    /**
     * 获取父Component(dagger2)
     */
    val baseComponent: BaseComponent = (activity.application as BaseApp).appComponent.baseComponent(BaseModule(activity))

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    lateinit var mDisposables: CompositeDisposable

    @Inject
    lateinit var activity: Activity

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var model: T

    /**
     * 获取SubscribeManager,可进行订阅操作
     */
    @Inject
    lateinit var sm: SubstribeManager

    @BindLayout
    lateinit var mBinding: E

    private var isRegisterEventBus: Boolean = false

    init {
        injectDagger()
        subscribeEvent()
        bind(bindLayout())
    }

    private fun bind(layout: Int) {
        LayoutFinder.find(this, layout)
        initEvent()
    }

    /**
     * 绑定布局

     * @return
     */
    protected abstract fun bindLayout(): Int

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

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun event(i: Int?) {

    }


    /**
     * dagger2绑定(需要则重写) ps: getBaseComponent().getMainComponent(new
     * MainModule()).inject(this);
     */
    protected abstract fun injectDagger()

    /**
     * 获得根布局
     */
    val rootView: View
        get() = mBinding.root

    /**
     * 初始化事件
     */
    protected open fun initEvent() {

    }

    /**
     * 初始化数据
     */
    abstract fun initData()

    /**
     * 隐藏软键盘
     */
    fun hideKeyboard() {
        val view = activity.currentFocus
        if (view != null) {
            (activity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, 0)
        }
    }


    private fun showKeyboard(et: EditText) {
        et.requestFocus()
        (activity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(et, InputMethodManager.SHOW_FORCED)
    }


    protected var isVisiable: Boolean
        get() = rootView.isShown
        set(visiable) {
            rootView.visibility = if (visiable) View.VISIBLE else View.INVISIBLE
        }

    fun destory() {
        mDisposables.dispose()
        //取消所有正在进行的网络任务
        if (isRegisterEventBus) {
            EventBus.getDefault().unregister(this)
        }

        model.destory()
    }

    override fun providerContext(): Context {
        return context
    }

    companion object {
        //基类内部错误tag
        val ERROR_TAG = -1
    }
}
