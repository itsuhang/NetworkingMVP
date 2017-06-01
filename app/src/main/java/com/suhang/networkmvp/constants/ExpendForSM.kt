package com.suhang.networkmvp.constants

import com.suhang.networkmvp.function.rx.FlowableWrap
import com.suhang.networkmvp.function.rx.RxBusSingle
import com.suhang.networkmvp.function.rx.SubstribeManager
import com.suhang.networkmvp.mvp.result.*
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * Created by 苏杭 on 2017/5/31 15:53.
 */

fun  SubstribeManager.subscribeSuccess():FlowableWrap<SuccessResult> {
    return FlowableWrap(mRxBus.toFlowable(SuccessResult::class.java).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(), mDisposable)
}

fun  SubstribeManager.subscribeError():FlowableWrap<ErrorResult> {
    return FlowableWrap(mRxBus.toFlowable(ErrorResult::class.java).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(), mDisposable)
}

fun  SubstribeManager.subscribeLoading():FlowableWrap<LoadingResult> {
    return FlowableWrap(mRxBus.toFlowable(LoadingResult::class.java).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(), mDisposable)
}

fun  SubstribeManager.subscribeProgress():FlowableWrap<ProgressResult> {
    return FlowableWrap(mRxBus.toFlowable(ProgressResult::class.java).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(), mDisposable)
}

fun  SubstribeManager.subscribeEvent():FlowableWrap<EventResult> {
    return FlowableWrap(mRxBus.toFlowable(EventResult::class.java).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(), mDisposable)
}

fun  SubstribeManager.subscribeGlobalProgress():FlowableWrap<ProgressResult> {
    return FlowableWrap(RxBusSingle.instance().toFlowable(ProgressResult::class.java).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(), mDisposable)
}
