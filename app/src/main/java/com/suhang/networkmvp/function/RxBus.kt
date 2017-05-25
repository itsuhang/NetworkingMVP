package com.suhang.networkmvp.function

import com.suhang.networkmvp.annotation.BaseScope

import javax.inject.Inject

import io.reactivex.Flowable
import io.reactivex.processors.FlowableProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.subscribers.SerializedSubscriber

@BaseScope
class RxBus @Inject
constructor() {
    //相当于Rxjava1.x中的Subject
    private val mBus: FlowableProcessor<Any> = PublishProcessor.create<Any>().toSerialized()

    init {
        //调用toSerialized()方法，保证线程安全
    }

    /**
     * 发送消息
     * @param o
     */
    fun post(o: Any) {
        SerializedSubscriber(mBus).onNext(o)
    }

    /**
     * 确定接收消息的类型
     * @param aClass
     * *
     * @param <T>
     * *
     * @return
    </T> */
    fun <T> toFlowable(aClass: Class<T>): Flowable<T> {
        return mBus.ofType(aClass)
    }

    /**
     * 判断是否有订阅者
     * @return
     */
    fun hasSubscribers(): Boolean {
        return mBus.hasSubscribers()
    }

}