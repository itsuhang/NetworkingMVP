package com.suhang.networkmvp.domain;

/**
 * Created by 苏杭 on 2017/4/25 16:10.
 *  View层从model层获取数据成功后的返回类，用于获取需要的bean类，并可扩展属性（如：需要tag，则可以直接在此类中添加tag属性）
 */

public class SuccessResult {
    //返回的结果类
    private ErrorBean result;
    //用于判断是哪一个请求
    private int tag;
    //是否是网络获取的数据（而不是缓存数据）
    private boolean isNetwork;

    public boolean isNetwork() {
        return isNetwork;
    }

    public void setNetwork(boolean network) {
        isNetwork = network;
    }

    /**
     * 处理成需要的bean类
     * @param tClass
     * @param <T>
     * @return
     */
    public <T> T getResult(Class<T> tClass) {
        return tClass.cast(result);
    }

    public int getTag() {
        return tag;
    }

    public void setResult(ErrorBean result) {
        this.result = result;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public SuccessResult(ErrorBean result,int tag,boolean isNetwork) {
        this.result = result;
        this.tag = tag;
        this.isNetwork = isNetwork;
    }
}
