package com.suhang.networkmvp.domain

import com.suhang.networkmvp.annotation.Content

/**
 * Created by 苏杭 on 2017/4/24 16:30.
 */

class HuanPeng<T> : WrapBean {
    var status: String? = null
    @get:Content
    var content: T? = null
}
