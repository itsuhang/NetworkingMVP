package com.suhang.networkmvp.ui.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.suhang.layoutfinderannotation.GenInheritedSubComponent
import com.suhang.layoutfinderannotation.GenSubComponent
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.application.DaggerHelper
import com.suhang.networkmvp.constants.Constant
import com.suhang.networkmvp.dagger.module.BaseModule
import com.suhang.networkmvp.function.rx.SubstribeManager
import com.suhang.networkmvp.mvp.model.IBaseModel
import com.suhang.networkmvp.utils.ScreenUtils
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/1/21 10:52.
 */
@GenInheritedSubComponent(tag = Constant.BASE_DAGGER_TAG,childTag = Constant.BASE_FRAGMENT_DAGGER_TAG,modules = arrayOf(BaseModule::class),scope = BaseScope::class,shouldInject = false)
abstract class BaseFragment<T : IBaseModel> : Fragment(), AnkoLogger {

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    lateinit var disposables: CompositeDisposable

    @Inject
    lateinit var model: T

    /**
     * 获取RxBus,可进行订阅操作
     */
    @Inject
    lateinit var manager: SubstribeManager

    //是否为缓存布局
    private var isCacheView: Boolean = false

    lateinit var root: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initDagger()
        subscribeEvent()
    }

    fun bind(layout: Int) {
        root = View.inflate(context, layout, null)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        onViewCreate(inflater, container, savedInstanceState)
        return root
    }

    /**
     * 可使用DaggerHelper初始化Dagger
     */
    open fun initDagger() {
        DaggerHelper.getInstance().getBaseFragmentComponent(this,activity)
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

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        model.destroy()
    }


    /**
     * 有时会有Activity给关闭而内部Fragment不走onDestory()方法,则可手动调用此方法销毁数据
     */
    fun destroy() {
        disposables.dispose()
        //取消所有正在进行的网络任务
        model.destroy()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }


    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    override fun onDetach() {
        super.onDetach()
    }
}
