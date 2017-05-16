package com.suhang.networkmvp.mvp.model;

import android.util.ArrayMap;

import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.GithubBean;
import com.suhang.networkmvp.interfaces.INetworkOtherService;
import com.suhang.networkmvp.interfaces.INetworkService;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/4/28 16:33.
 */

public class AttentionModel extends BaseModel {
    public static final int APPMAIN = 1;
    public static final int GITHUB = 2;
    public static final int TAG_APP = 3;
    public static final int TAG_GIT = 4;
    @Inject
    NetworkModel mModel;

    @Inject
    public AttentionModel() {
    }


    public void getAppMainData() {
        mModel.loadPostDataWrap(AppMain.class,  false, TAG_APP,new ArrayMap<>());
    }

    public void getGithubData() {
        mModel.loadGetData(GithubBean.class, TAG_GIT,"2/1");
    }
}
