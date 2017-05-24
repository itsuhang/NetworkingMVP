package com.suhang.networkmvp.mvp.result

/**
 * Created by 苏杭 on 2017/4/25 16:22.
 * View层从model层获取数据时的事件，用于显示加载状态和因此加载状态，并可扩展属性（如：需要tag，则可以直接在此类中添加tag属性）
 */

class LoadingResult(//调用加载操作和隐藏加载操作的标志
        var isLoading: Boolean, tag: Int) : BaseResult(tag)
