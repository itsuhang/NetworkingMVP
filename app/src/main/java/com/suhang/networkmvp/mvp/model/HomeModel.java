package com.suhang.networkmvp.mvp.model;

import android.util.Log;


import com.suhang.networkmvp.annotation.FragmentScope;

import javax.inject.Inject;


/**
 * Created by 苏杭 on 2017/1/21 15:58.
 */

@FragmentScope
public class HomeModel extends CanNetworkModel {
    @Inject
    public HomeModel() {
    }

    public void log() {
        Log.i(TAG, "log: "+mNetworkPresenter);
    }
}
