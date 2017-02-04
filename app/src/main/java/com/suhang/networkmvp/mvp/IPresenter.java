package com.suhang.networkmvp.mvp;

/**
 * Created by 苏杭 on 2017/1/21 14:54.
 */

public interface IPresenter {
    /**
     * 解除视图绑定,防止内存泄漏
     */
    void detachView();

}
