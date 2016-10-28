package com.suhang.networkmvp.util;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by sh on 2016/10/25 11:16.
 */

public class RxUtil {
	/**
	 * 简化RX线程处理
	 * @param <T>
	 * @return
	 */
	public static <T> Observable.Transformer<T, T> fixScheduler() {
		return tObservable -> tObservable.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
	}
}
