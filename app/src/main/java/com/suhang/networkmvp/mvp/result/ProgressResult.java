package com.suhang.networkmvp.mvp.result;

/**
 * Created by 苏杭 on 2017/4/25 16:33.
 */

public class ProgressResult extends BaseResult{
    //返回的进度信息
    private int progress;
    //用于判断是哪一个请求
    private int tag;

    public void setProgress(int progress) {
        this.progress = progress;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    public int getProgress() {
        return progress;
    }

    public int getTag() {
        return tag;
    }

    public ProgressResult(int progress, int tag) {
        this.progress = progress;
        this.tag = tag;
    }
}
