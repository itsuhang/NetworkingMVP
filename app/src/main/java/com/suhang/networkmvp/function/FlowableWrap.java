package com.suhang.networkmvp.function;

import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.event.result.BaseResult;
import com.suhang.networkmvp.event.ErrorCode;
import com.suhang.networkmvp.utils.LogUtil;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by 苏杭 on 2017/4/28 11:02.
 */

public class FlowableWrap<T extends BaseResult> {
    private Flowable<T> mFlowable;
    private CompositeDisposable mDisposable;

    public FlowableWrap(Flowable<T> flowable, CompositeDisposable disposable) {
        mFlowable = flowable;
        mDisposable = disposable;
    }


    public FlowableWrap<T> subscribe(Next<T> consumer) {
        mDisposable.add(mFlowable.subscribe(consumer::onNext, throwable -> LogUtil.i("啊啊啊" + new ErrorBean(ErrorCode.ERROR_CODE_SUBSCRIBE_INNER, ErrorCode.ERROR_DESC_SUBSCRIBE_INNER))));
        return this;
    }

    public FlowableWrap<T> subscribe(Next<T> consumer, Error error) {
        mDisposable.add(mFlowable.subscribe(consumer::onNext, error::onError));
        return this;
    }

    public interface Next<T> {
        void onNext(T t);
    }

    public interface Error {
        void onError(Throwable throwable);
    }

}
