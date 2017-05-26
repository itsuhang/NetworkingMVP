package com.suhang.networkmvp.mvp.model

import android.app.Activity

import com.suhang.networkmvp.function.rx.RxBus

import javax.inject.Inject

import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger

/**
 * Created by 苏杭 on 2017/1/21 15:11.
 */

abstract class BaseModel : IBaseModel ,AnkoLogger{
    @Inject
    lateinit var mRxBus: RxBus
    @Inject
    lateinit var mDisposables: CompositeDisposable
    @Inject
    lateinit var mActivity: Activity

    override fun destroy() {
        mDisposables.dispose()
    }
}
