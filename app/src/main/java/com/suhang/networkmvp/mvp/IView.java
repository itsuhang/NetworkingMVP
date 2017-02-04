package com.suhang.networkmvp.mvp;

import android.app.Activity;

import com.suhang.networkmvp.domain.ErrorBean;


/**
 * Created by 苏杭 on 2017/1/21 14:54.
 */

public interface IView {
    /**
     * 统一处理错误(关于泛型, 错误类并不一定会全部统一,所以使用泛型)
     * @param tag (用于分辨是那个任务调用的该方法)
     */
    void showError(ErrorBean e, int tag);

    /**
     * 处理等待操作
     * @param tag (用于分辨是那个任务调用的该方法)
     */
    void showLoading(int tag);

    /**
     * 处理等待结束操作
     * @param tag (用于分辨是那个任务调用的该方法)
     */
    void hideLoading(int tag);

    /**
     * 提供Activity
     */
    Activity provideActivity();

}
