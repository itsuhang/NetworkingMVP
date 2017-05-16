package com.suhang.networkmvp.utils;


import com.suhang.networkmvp.annotation.Content;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.domain.WrapBean;

import org.reactivestreams.Publisher;

import java.lang.reflect.Method;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sh on 2016/10/25 11:16.
 */

public class RxUtil {
    /**
     * 简化RX线程处理
     */
    public static <T> FlowableTransformer<T, T> fixScheduler() {
        return upstream -> upstream.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * 生成Observable
     */
    public static <T> Flowable<T> createData(final T t) {
        return Flowable.create(new FlowableOnSubscribe<T>() {
            @Override
            public void subscribe(FlowableEmitter<T> flowable) {
                try {
                    flowable.onNext(t);
                    flowable.onComplete();
                } catch (Exception e) {
                    flowable.onError(e);
                }
            }
        }, BackpressureStrategy.BUFFER);
    }

    /**
     * 统一返回结果处理
     * 从给定包裹类中获取内容部分,转换后返回,包裹类中需将返回内容的get方法用@Content注解标记
     */
    public static <T extends WrapBean> FlowableTransformer<T, ErrorBean> handleResultNone() {
        return upstream -> upstream.flatMap(new Function<T, Publisher<ErrorBean>>() {
            @Override
            public Publisher<ErrorBean> apply(T t) throws Exception {
                for (Method m : t.getClass().getMethods()) {
                    if (m.getAnnotation(Content.class) != null) {
                        ErrorBean o = (ErrorBean) m.invoke(t);
                        return createData(o);
                    }
                }
                throw new RuntimeException("包裹bean类与实际获得的bean类不匹配,或包裹类中找不到@Content注解标记的get内容的方法");
            }
        });
    }
}
