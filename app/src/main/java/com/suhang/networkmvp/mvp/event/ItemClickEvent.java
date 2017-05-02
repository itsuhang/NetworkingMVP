package com.suhang.networkmvp.mvp.event;

/**
 * Created by 苏杭 on 2017/5/2 10:56.
 */

public class ItemClickEvent extends BaseEvent {
    private int position;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public ItemClickEvent(int position) {
        this.position = position;
    }
}
