package com.suhang.networkmvp.application;

/**
 * Created by 苏杭 on 2017/4/25 11:38.
 */
//TODO 1.统一处理事件的接收,一个Result类对应一个回调接口,并在View层设置给中间管理者
//TODO 2.Adapter中的事件回调处理
//TODO  3.BaseSubscribe用apt实现
//TODO  4.不在View层调用model层方法


public class App extends BaseApp{
    @Override
    public void onCreate() {
        super.onCreate();
    }
}
