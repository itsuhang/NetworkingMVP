package com.suhang.networkmvp.mvp.model;



import android.util.ArrayMap;
import android.util.Log;

import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.GithubBean;

import javax.inject.Inject;


/**
 * Created by 苏杭 on 2017/1/21 15:58.
 */

@PagerScope
public class AttentionModel extends CanNetworkModel {
    @Inject
    public AttentionModel() {
    }

    public void log() {
        mNetworkPresenter.getPostDataWrap(AppMain.class,new ArrayMap<>(),false,100);
    }

    public void doRefresh() {
        Log.i("啊啊啊", "刷新咯");
    }

    public void doLoadMore() {
        Log.i("啊啊啊", "加载更多咯");
    }
}
