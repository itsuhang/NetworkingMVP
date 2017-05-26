package com.suhang.networkmvp.ui.pager

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.dagger.component.BaseComponent
import com.suhang.networkmvp.dagger.module.BaseModule
import com.suhang.networkmvp.function.rx.SubstribeManager
import com.suhang.networkmvp.mvp.model.BaseModel
import io.reactivex.disposables.CompositeDisposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/1/24 15:12.
 * Fragment中不方便再嵌套Fragment时,用Pager页面
 */

abstract class BasePager<T : BaseModel>(activity: Activity) :AnkoLogger {

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
    lateinit var manager: SubstribeManager

    lateinit var root:View

    private var isRegisterEventBus: Boolean = false

    init {
        injectDagger()
        subscribeEvent()
        bind(bindLayout())
    }

    private fun bind(layout: Int) {
        root = View.inflate(context,layout,null)
        initEvent()
    }

    abstract fun bindLayout():Int

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


    protected var visible: Boolean
        get() = root.isShown
        set(visible) {
            root.visibility = if (visible) View.VISIBLE else View.INVISIBLE
        }

    fun destroy() {
        mDisposables.dispose()
        //取消所有正在进行的网络任务
        if (isRegisterEventBus) {
            EventBus.getDefault().unregister(this)
        }

        model.destroy()
    }
}
