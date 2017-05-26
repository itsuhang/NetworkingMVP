package com.suhang.networkmvp.function.rx

import com.suhang.networkmvp.constants.ErrorCode
import com.suhang.networkmvp.constants.errorMessage
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.interfaces.ErrorLogger

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn

/**
 * Created by 苏杭 on 2017/4/28 11:02.
 */

class FlowableWrap<T>(private val mFlowable: Flowable<T>, private val mDisposable: CompositeDisposable): AnkoLogger, ErrorLogger {
    /**
     * 订阅事件,与Rxjava一致,自动添加事件到CompositeDisposable中,方便回收
     */
    fun subscribe(consumer: Consumer<T>) {
        mDisposable.add(mFlowable.subscribe({ consumer.accept(it) }, {
            warn(ErrorBean(ErrorCode.Companion.ERROR_CODE_SUBSCRIBE_INNER, ErrorCode.Companion.ERROR_DESC_SUBSCRIBE_INNER +"\n",errorMessage(it)))
        }))
    }

    /**
     * 订阅事件,与Rxjava一致,自动添加事件到CompositeDisposable中,方便回收
     */
    fun subscribe(consumer: Consumer<T>, error: Consumer<in Throwable>) {
        mDisposable.add(mFlowable.subscribe({ consumer.accept(it) }, { error.accept(it) }))
    }
}
