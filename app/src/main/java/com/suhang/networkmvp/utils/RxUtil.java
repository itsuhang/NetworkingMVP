package com.suhang.networkmvp.utils;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableEmitter;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by sh on 2016/10/25 11:16.
 */

public class RxUtil {
	/**
	 * 简化RX线程处理
	 *
	 * @param <T>
	 * @return
	 */
	public static <T> FlowableTransformer<T, T> fixScheduler() {
		return upstream -> upstream.subscribeOn(Schedulers.computation()).observeOn(AndroidSchedulers.mainThread());
	}

	/**
	 * 生成Observable
	 *
	 * @param <T>
	 * @return
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

//	/**
//	 * 统一返回结果处理
//	 *
//	 * @param <T>
//	 * @return
//	 */
//	public static <T extends ErrorBean> FlowableTransformer<HuanpengBean<T>, T> handleResult() {
//		return upstream -> upstream.flatMap(new Function<HuanpengBean<T>, Publisher<T>>() {
//			@Override
//			public Publisher<T> apply(HuanpengBean<T> tHuanpengBean) throws Exception {
//				if ("1".equals(tHuanpengBean.getStatus())) {
//					return createData(tHuanpengBean.getContent());
//				} else {
//					return createData(tHuanpengBean.getContent());
//				}
//			}
//		});
//	}
//
//	/**
//	 * 统一返回结果处理
//	 *
//	 * @param <T>
//	 * @return
//	 */
//	public static <T> FlowableTransformer<T, ErrorBean> handleResultNone() {
//		return upstream -> upstream.flatMap(new Function<T, Publisher<ErrorBean>>() {
//			@Override
//			public Publisher<ErrorBean> apply(T t) throws Exception {
//				return createData(tHuanpengBean.getContent());
//			}
//		});
//	}
}
