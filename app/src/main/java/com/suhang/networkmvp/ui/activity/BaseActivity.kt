package com.suhang.networkmvp.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.suhang.layoutfinderannotation.GenInheritedSubComponent
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.application.DaggerHelper
import com.suhang.networkmvp.constants.Constant
import com.suhang.networkmvp.dagger.module.BaseModule
import com.suhang.networkmvp.function.rx.SubstribeManager
import com.suhang.networkmvp.mvp.model.IBaseModel
import com.suhang.networkmvp.utils.InputLeakUtil
import com.suhang.networkmvp.utils.ScreenUtils
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/1/21 10:52.
 */
@GenInheritedSubComponent(tag = Constant.BASE_DAGGER_TAG,childTag = Constant.BASE_ACTIVITY_DAGGER_TAG,modules = arrayOf(BaseModule::class),scope = BaseScope::class,shouldInject = false)
abstract class BaseActivity<T : IBaseModel> : AppCompatActivity(),AnkoLogger{

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    lateinit var disposables: CompositeDisposable

    @Inject
    lateinit var activity: Activity

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var model: T

    @Inject
    lateinit var rxPermission:RxPermissions

    /**
     * 获取RxBus,可进行订阅操作
     */
    @Inject
    lateinit var manager: SubstribeManager

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BaseApp).addToStack(this)
        initDagger()
        subscribeEvent()
    }

    protected fun bind(@LayoutRes layout: Int) {
        setContentView(layout)
        initData()
        initEvent()
    }

    /**
     * 可使用DaggerHelper初始化Dagger
     */
    open fun initDagger() {
        baseActivityComponent = DaggerHelper.getInstance().getBaseActivityComponent(this,this)
    }

    lateinit var baseActivityComponent:BaseActivityComponent

    /**
     * 订阅事件
     */
    protected abstract fun subscribeEvent()

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

    override fun onResume() {
        super.onResume()
    }

    override fun onRestart() {
        super.onRestart()
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onPause() {
        super.onPause()
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
        (application as BaseApp).removeFromStack(this)
        disposables.dispose()
        //处理InputMethodManager导致的内存泄漏
        model.destroy()
        InputLeakUtil.fixInputMethodManager(this)
        DaggerHelper.getInstance().removeComponent(baseActivityComponent)
    }
}
