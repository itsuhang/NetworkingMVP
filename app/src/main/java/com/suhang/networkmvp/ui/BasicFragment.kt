package com.suhang.networkmvp.ui

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.suhang.networkmvp.utils.ScreenUtils
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/6/5 14:37.
 */
abstract class BasicFragment : Fragment(), HasSupportFragmentInjector, AnkoLogger {
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
    //是否为缓存布局
    private var isCacheView: Boolean = false

    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeEvent()
    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return childFragmentInjector
    }

    fun bind(layout: Int) {
        root = View.inflate(context, layout, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onViewCreate(inflater, container, savedInstanceState)
        return root
    }

    /**
     * 需要在绑定布局之前(onCreateView之前)做处理则覆盖此方法

     * @param inflater
     * *
     * @param container
     * *
     * @param savedInstanceState
     */
    protected fun onViewCreate(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?) {}


    /**
     * 订阅事件
     */
    protected abstract fun subscribeEvent()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initData()
        initEvent()
    }

    /**
     * 初始化数据
     */
    protected abstract fun initData()


    /**
     * 初始化事件
     */
    protected abstract fun initEvent()


    /**
     * 隐藏软键盘
     */
    protected fun hideKeyboard() {
        val view = activity.currentFocus
        if (view != null) {
            (activity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 沉浸状态栏偏移
     */
    protected fun immerseUI(view: View) {
        view.setPadding(0, ScreenUtils.getStatusBarHeight(context), 0, 0)
    }


    /**
     * 显示软键盘
     */
    protected fun showKeyboard(et: EditText) {
        et.requestFocus()
        (activity.applicationContext.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager).showSoftInput(et, InputMethodManager.SHOW_IMPLICIT)
    }

}