package com.suhang.networkmvp.mvp.result

/**
 * Created by 苏杭 on 2017/4/25 16:10.
 * View层从model层获取数据成功后的返回类，用于获取需要的bean类，并可扩展属性（如：需要tag，则可以直接在此类中添加tag属性）
 */

class SuccessResult(//返回的结果类
        var result: Any,var tag: Int) : BaseResult()
