package com.suhang.networkmvp.domain;

/**
 * Created by 苏杭 on 2017/4/25 16:17.
 * View层从model层获取数据失败后的返回类，用于获取错误信息，并可扩展属性（如：需要tag，则可以直接在此类中添加tag属性）
 */

public class ErrorResult {
    //返回的错误结果类
    private ErrorBean result;
    //用于判断是哪一个请求
    private int tag;

    public ErrorResult(ErrorBean result, int tag) {
        this.result = result;
        this.tag = tag;
    }

    public ErrorBean getResult() {
        return result;
    }

    public void setResult(ErrorBean result) {
        this.result = result;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }
}
