package com.suhang.networkmvp.mvp.result

import com.suhang.networkmvp.constants.DEFAULT_TAG

/**
 * Created by 苏杭 on 2017/4/26 14:04.
 */

open abstract class BaseResult(var tag: Int) {
    var append: Any? = null

    init {
        tag = DEFAULT_TAG
    }
}
