package com.suhang.networkmvp.application;

import android.app.Application;

import com.suhang.networkmvp.dagger.component.AppComponent;
import com.suhang.networkmvp.dagger.component.DaggerAppComponent;
import com.suhang.networkmvp.dagger.module.AppModule;
import com.suhang.networkmvp.utils.LogUtil;


/**
 * Created by 苏杭 on 2017/1/20 15:01.
 */

public class App extends Application {
    private static App sApp;
    private AppComponent mAppComponent;

    public static App getInstance() {
        return sApp;
    }
    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
//        CrashHandler.getInstance().init(this);
        LogUtil.changeLogSwitch(true);
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
