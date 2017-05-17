package com.suhang.networkmvp.mvp.event;

/**
 * Created by 苏杭 on 2017/5/2 10:56.
 */

public class BindingEvent extends BaseEvent {
    private int id;
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public BindingEvent(int id, Object data) {
        this.id = id;
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
