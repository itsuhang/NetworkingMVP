package com.suhang.networkmvp.mvp.model;



import com.suhang.networkmvp.annotation.PagerScope;
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
        mNetworkPresenter.getData(GithubBean.class,"2/1",null,100);
    }
}
