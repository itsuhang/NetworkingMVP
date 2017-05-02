package com.suhang.networkmvp.mvp.event;

/**
 * Created by 苏杭 on 2017/5/2 10:56.
 */

public class ClickEvent extends BaseEvent {
    private int id;

    public ClickEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
