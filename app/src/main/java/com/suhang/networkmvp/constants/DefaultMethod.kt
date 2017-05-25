package com.suhang.networkmvp.constants

import android.view.View
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.interfaces.ErrorLogger
import java.lang.StringBuilder

/**
 * Created by 苏杭 on 2017/5/23 17:30.
 */

fun ErrorLogger.errorMessage(e: Exception) :String{
    val sb:StringBuilder = StringBuilder()
    sb.append(e.toString()+"\n")
    sb.append(e.message+"\n")
    e.stackTrace.forEach {
        sb.append(it.toString()+"\n")
    }
    return sb.toString()
}
fun ErrorLogger.errorMessage(e: Throwable) :String{
    val sb:StringBuilder = StringBuilder()
    sb.append(e.toString()+"\n")
    sb.append(e.message+"\n")
    e.stackTrace.forEach {
        sb.append(it.toString()+"\n")
    }
    return sb.toString()
}

fun View.dip2px(dpValue: Float): Int {
    return (BaseApp.instance.resources.displayMetrics.density*dpValue+0.5f).toInt()
}

fun View.dip2pxf(dpValue: Float): Float {
    return BaseApp.instance.resources.displayMetrics.density*dpValue+0.5f
}

fun View.px2dip(pxValue: Float): Int {
    return (pxValue/BaseApp.instance.resources.displayMetrics.density+0.5f).toInt()
}

fun View.px2dipf(pxValue: Float): Float {
    return pxValue/BaseApp.instance.resources.displayMetrics.density+0.5f
}

