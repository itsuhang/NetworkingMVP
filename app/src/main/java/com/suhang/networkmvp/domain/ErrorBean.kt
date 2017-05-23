package com.suhang.networkmvp.domain


import com.suhang.networkmvp.constants.Constants
import com.suhang.networkmvp.constants.ErrorCode

/**
 * Created by sh on 2016/3/11 16:13.
 */
open class ErrorBean(
        //错误代码
        var code: String? = null,
        //错误描述
        var desc: String? = null,

        val exceptionMessage: String? = null) {

    var appendMessage: Any? = null


    //错误类型(如:是否需要提示给用户)
    var type = Constants.ERRORTYPE_ONE

    override fun toString(): String {
        return "ErrorBean(code=$code, desc=$desc, \n exceptionMessage=$exceptionMessage, type='$type')"
    }
}
