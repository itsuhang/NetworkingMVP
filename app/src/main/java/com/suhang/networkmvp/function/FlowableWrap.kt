package com.suhang.networkmvp.function

import com.suhang.networkmvp.constants.ErrorCode
import com.suhang.networkmvp.constants.errorMessage
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.utils.LogUtil

import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn

/**
 * Created by 苏杭 on 2017/4/28 11:02.
 */

class FlowableWrap<T>(private val mFlowable: Flowable<T>, private val mDisposable: CompositeDisposable):AnkoLogger{


    fun subscribe(consumer: Next<T>): FlowableWrap<T> {
        mDisposable.add(mFlowable.subscribe({ consumer.onNext(it) }, {
            warn(ErrorBean(ErrorCode.ERROR_CODE_SUBSCRIBE_INNER, ErrorCode.ERROR_DESC_SUBSCRIBE_INNER+"\n",errorMessage(it)))
        }))
        return this
    }

    fun subscribe(consumer: Next<T>, error: Error): FlowableWrap<T> {
        mDisposable.add(mFlowable.subscribe({ consumer.onNext(it) }, { error.onError(it) }))
        return this
    }

    interface Next<T> {
        fun onNext(t: T)
    }

    interface Error {
        fun onError(throwable: Throwable)
    }

}
