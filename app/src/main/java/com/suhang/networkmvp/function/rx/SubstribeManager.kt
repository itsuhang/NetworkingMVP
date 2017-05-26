package com.suhang.networkmvp.function.rx

import com.suhang.networkmvp.mvp.event.BaseEvent

import javax.inject.Inject

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by 苏杭 on 2017/5/2 11:18.
 */
class SubstribeManager @Inject
constructor() {
    @Inject
    lateinit var mRxBus: RxBus
    @Inject
    lateinit var mDisposable: CompositeDisposable

    /**
     * 发送事件
     */
    fun post(o: Any) {
        mRxBus.post(o)
    }

    /**
     * 订阅成功事件(订阅后才可收到该事件,订阅要在获取数据之前进行)

     * @param aClass 继承BaseResult的结果类的字节码
     */
    fun <V> subscribeResult(aClass: Class<V>): FlowableWrap<V> {
        return FlowableWrap(mRxBus.toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(), mDisposable)
    }

    fun <T : BaseEvent> subscribeEvent(aClass: Class<T>): FlowableWrap<T> {
        return FlowableWrap(mRxBus.toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(), mDisposable)
    }

}
