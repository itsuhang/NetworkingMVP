package com.suhang.networkmvp.function;

import com.suhang.networkmvp.annotation.BaseScope;

import javax.inject.Inject;

import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;
import io.reactivex.subscribers.SerializedSubscriber;

@BaseScope
public class RxBus {
    //相当于Rxjava1.x中的Subject
    private final FlowableProcessor<Object> mBus;
    @Inject
    public RxBus() {
        //调用toSerialized()方法，保证线程安全
        mBus = PublishProcessor.create().toSerialized();
    }

    /**
     * 发送消息
     * @param o
     */
    public void post(Object o) {
        new SerializedSubscriber<>(mBus).onNext(o);
    }

    /**
     * 确定接收消息的类型
     * @param aClass
     * @param <T>
     * @return
     */
    public <T> Flowable<T> toFlowable(Class<T> aClass) {
        return mBus.ofType(aClass);
    }

    /**
     * 判断是否有订阅者
     * @return
     */
    public boolean hasSubscribers() {
        return mBus.hasSubscribers();
    }

}