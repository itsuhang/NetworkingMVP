package com.suhang.networkmvp.event;

/**
 * Created by 苏杭 on 2017/4/25 16:22.
 * View层从model层获取数据时的事件，用于显示加载状态和因此加载状态，并可扩展属性（如：需要tag，则可以直接在此类中添加tag属性）
 */

public class LoadingResult extends BaseResult{
    //调用加载操作和隐藏加载操作的标志
    private boolean isLoading;
    //用于判断是哪一个请求
    private int tag;

    public boolean isLoading() {
        return isLoading;
    }

    public int getTag() {
        return tag;
    }

    public void setLoading(boolean loading) {
        isLoading = loading;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public LoadingResult(boolean isLoading, int tag) {
        this.isLoading = isLoading;
        this.tag = tag;
    }

}
