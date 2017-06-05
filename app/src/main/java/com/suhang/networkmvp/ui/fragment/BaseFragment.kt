package com.suhang.networkmvp.ui.fragment

import com.suhang.networkmvp.function.rx.SubstribeManager
import com.suhang.networkmvp.mvp.model.IBaseModel
import com.suhang.networkmvp.ui.BasicFragment
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/1/21 10:52.
 */
abstract class BaseFragment<T : IBaseModel> : BasicFragment() {


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
}
