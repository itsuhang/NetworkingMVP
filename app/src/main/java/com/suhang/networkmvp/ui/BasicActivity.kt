package com.suhang.networkmvp.ui

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.utils.InputLeakUtil
import com.suhang.networkmvp.utils.ScreenUtils
import org.jetbrains.anko.AnkoLogger

/**
 * Created by 苏杭 on 2017/6/5 14:30.
 */

abstract class BasicActivity : AppCompatActivity(), AnkoLogger {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        (application as BaseApp).addToStack(this)
        subscribeEvent()
    }

    protected fun bind(@LayoutRes layout: Int) {
        setContentView(layout)
        initData()
        initEvent()
    }

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
        InputLeakUtil.fixInputMethodManager(this)
    }
}
