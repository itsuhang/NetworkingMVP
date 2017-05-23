package com.suhang.networkmvp.domain;

import com.suhang.networkmvp.annotation.Content;

/**
 * Created by 苏杭 on 2017/4/24 16:30.
 */

public class HuanPeng<T extends ErrorBean> implements WrapBean {
    private String status;
    private T content;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    @Content
    public T getContent() {
        return content;
    }

    public void setContent(T content) {
        this.content = content;
    }

}
