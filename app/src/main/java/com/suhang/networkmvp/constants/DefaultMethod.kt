package com.suhang.networkmvp.constants

import com.suhang.networkmvp.application.BaseApp

/**
 * Created by 苏杭 on 2017/5/23 17:30.
 */

fun Any.errorMessage(e: Exception) :String{
    return e.toString()+"\n"+e.message
}
fun Any.errorMessage(e: Throwable) :String{
    return e.toString()+"\n"+e.message
}

fun Any.dip2px(dpValue: Float): Int {
    return (BaseApp.instance.resources.displayMetrics.density*dpValue+0.5f).toInt()
}

fun Any.dip2pxf(dpValue: Float): Float {
    return BaseApp.instance.resources.displayMetrics.density*dpValue+0.5f
}

fun Any.px2dip(pxValue: Float): Int {
    return (pxValue/BaseApp.instance.resources.displayMetrics.density+0.5f).toInt()
}

fun Any.px2dipf(pxValue: Float): Float {
    return pxValue/BaseApp.instance.resources.displayMetrics.density+0.5f
}

