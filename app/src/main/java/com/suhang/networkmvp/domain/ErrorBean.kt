package com.suhang.networkmvp.domain


/**
 * Created by sh on 2016/3/11 16:13.
 */
data class ErrorBean(
        //错误代码
        var code: String = "",
        //错误描述
        var desc: String = "",

        var errorCase: String = "",

        var showCode: String = "",

        var type: Int = ErrorBean.TYPE_HIDDEN
){
    companion object{
        val TYPE_SHOW:Int = 1
        val TYPE_HIDDEN:Int = 2
        val TYPE_OTHER:Int = 3
    }
}

