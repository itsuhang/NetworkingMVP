package com.suhang.networkmvp.function;

import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.event.BaseResult;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/4/28 11:00.
 */

@BaseScope
public class SubscribeManager {
    @Inject
    RxBus mRxBus;
    @Inject
    CompositeDisposable mDisposable;

    @Inject
    public SubscribeManager() {
    }

    /**
     * 发送事件
     * @param o
     */
    public void post(Object o) {
        mRxBus.post(o);
    }

    public RxBus getRxBus() {
        return mRxBus;
    }

    /**
     * 订阅成功事件(订阅后才可收到该事件,订阅要在获取数据之前进行)
     *
     * @param aClass 继承BaseResult的结果类的字节码
     */
    public <V extends BaseResult> FlowableWrap<V> subscribe(Class<V> aClass) {
        return new FlowableWrap<>(mRxBus.toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop(),mDisposable);
    }
}
