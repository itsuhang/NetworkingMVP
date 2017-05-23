package com.suhang.networkmvp.mvp.result

import com.suhang.networkmvp.constants.DEFAULT_TAG

/**
 * Created by 苏杭 on 2017/4/25 16:17.
 * View层从model层获取数据失败后的返回类，用于获取错误信息，并可扩展属性（如：需要tag，则可以直接在此类中添加tag属性）
 */

class ErrorResult(//返回的错误结果类
        var result: Any? = null,var tag: Int = 0) : BaseResult {

    init {
        if (tag == 0) {
            tag = DEFAULT_TAG
        }
    }
}
