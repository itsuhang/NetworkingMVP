package com.suhang.networkmvp.constants

import com.suhang.networkmvp.mvp.result.SuccessResult

/**
 * Created by 苏杭 on 2017/5/23 17:30.
 */
val Any.DEFAULT_TAG
    get() = 100

val Any.ERROR_TAG
    get() = -1

val Any.GET
    get() = 222

val SuccessResult.POST
    get() = 333

fun Any.errorMessage(e: Exception) :String{
    return e.toString()+"\n"+e.message
}

