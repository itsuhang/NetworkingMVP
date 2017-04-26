package com.suhang.networkmvp.interfaces;

import com.suhang.networkmvp.event.BaseResult;

/**
 * Created by 苏杭 on 2017/4/26 15:03.
 */

public interface IResultCallback<T extends BaseResult> {
    void onResult(T t);
}
