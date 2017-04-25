package com.suhang.networkmvp.application;

import android.app.Application;

import com.suhang.networkmvp.dagger.component.AppComponent;
import com.suhang.networkmvp.dagger.component.DaggerAppComponent;
import com.suhang.networkmvp.dagger.module.AppModule;
import com.suhang.networkmvp.function.CrashHandler;
import com.suhang.networkmvp.utils.LogUtil;


/**
 * Created by 苏杭 on 2017/1/20 15:01.
 */

public class BaseApp extends Application {
    private static BaseApp sApp;
    private AppComponent mAppComponent;
    private boolean isDebug = true;

    public static BaseApp getInstance() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
//        CrashHandler.getInstance().init(this);
        if (isDebug) {
            LogUtil.changeLogSwitch(true);
        } else {
            LogUtil.changeLogSwitch(false);
        }
        mAppComponent = DaggerAppComponent.builder().appModule(new AppModule(this)).build();
    }

    public AppComponent getAppComponent() {
        return mAppComponent;
    }
}
