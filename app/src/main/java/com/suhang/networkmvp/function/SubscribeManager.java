package com.suhang.networkmvp.function;

import android.util.Log;

import com.suhang.networkmvp.annotation.Result;
import com.suhang.networkmvp.event.BaseResult;
import com.suhang.networkmvp.interfaces.IResultCallback;
import com.suhang.networkmvp.utils.LogUtil;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

import javax.inject.Inject;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/4/26 14:23.
 */

public class SubscribeManager {
    @Inject
    CompositeDisposable mDisposables;
    @Inject
    RxBus mRxBus;

    @Inject
    public SubscribeManager() {
    }

    /**
     * 订阅成功事件(订阅后才可收到该事件,订阅要在获取数据之前进行)
     */
    public void subscribe(IResultCallback callback) {
        loop(callback.getClass(),callback);
    }

    /**
     * 递归查找Result接口,并订阅该事件
     * @param aClass
     * @param callback
     */
    private void loop(Class aClass,IResultCallback callback) {
        if (!aClass.equals(IResultCallback.class)) {
            for (Class<?> c : aClass.getInterfaces()) {
                if (c.getAnnotation(Result.class) != null) {
                    for (Method method : c.getMethods()) {
                        Class<?>[] parameterTypes = method.getParameterTypes();
                        if (parameterTypes.length > 0) {
                            Class<?> type = parameterTypes[0];
                            mDisposables.add(mRxBus.toFlowable(type).observeOn(AndroidSchedulers.mainThread()).onBackpressureDrop().subscribe(t -> {
                                method.invoke(callback, t);
                            }, throwable -> {
                                throw new RuntimeException("View层回调错误,请检查Result类或View层传入接口是否正确",throwable);
                            }));
                        }
                    }
                }
                loop(c,callback);
            }
        }
    }

    public void destory() {
        mDisposables.dispose();
    }

}
