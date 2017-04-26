package com.suhang.networkmvp.function;

import com.suhang.networkmvp.event.BaseResult;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/4/26 14:23.
 */

public class BaseSubscribe {
    @Inject
    CompositeDisposable mDisposables;
    @Inject
    RxBus mRxBus;

    @Inject
    public BaseSubscribe() {
    }

//    /**
//     * 订阅成功事件(订阅后才可收到该事件,订阅要在获取数据之前进行)
//     * @param aClass 继承BaseResult的结果类的字节码
//     * @param <T>
//     * @return
//     */
//    public  <T extends BaseResult,V extends BaseResult.ResultCallback> void subscribe(Class<T> aClass,V callback){
//        mDisposables.add(mRxBus.toFlowable(aClass).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop().subscribe(t -> {
//
//        }));
//    }

    public void destory() {
        mDisposables.dispose();
    }

}
