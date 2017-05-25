package com.suhang.networkmvp.mvp.result

/**
 * Created by 苏杭 on 2017/4/25 16:33.
 */

class ProgressResult(//返回的进度信息
        var progress: Int,var done:Boolean, tag: Int) : BaseResult(tag)
