package com.suhang.networkmvp.utils


import com.suhang.networkmvp.annotation.Content
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.domain.WrapBean

import org.reactivestreams.Publisher

import java.lang.reflect.Field
import java.lang.reflect.Method

import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.FlowableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.annotations.NonNull
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers

/**
 * Created by sh on 2016/10/25 11:16.
 */

object RxUtil {

//    new FlowableTransformer() {
//        @Override
//        public Publisher apply(@NonNull Flowable upstream) {
//            return upstream.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
//        }
//    }
    /**
     * 简化RX线程处理
     */
    fun fixScheduler(): FlowableTransformer<Any, Any> = FlowableTransformer { upstream -> upstream.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread()) }

    /**
     * 生成Observable
     */
    fun <T> createData(t: T): Flowable<T> {
        return Flowable.create({ flowable ->
            try {
                flowable.onNext(t)
                flowable.onComplete()
            } catch (e: Exception) {
                flowable.onError(e)
            }
        }, BackpressureStrategy.BUFFER)
    }

    /**
     * 统一返回结果处理
     * 从给定包裹类中获取内容部分,转换后返回,包裹类中需将返回内容的get方法用@Content注解标记
     */
    fun handleResultNone(): FlowableTransformer<Any, Any> = FlowableTransformer { upstream ->
        upstream.flatMap(Function<Any, Publisher<Any>> { t ->
            t.javaClass.methods
                    .filter { it.getAnnotation(Content::class.java) != null }
                    .map { it.invoke(t) }
                    .forEach { return@Function createData(it) }
            throw RuntimeException("包裹bean类与实际获得的bean类不匹配,或包裹类中找不到@Content注解标记的get内容的方法")
        })
    }
}
