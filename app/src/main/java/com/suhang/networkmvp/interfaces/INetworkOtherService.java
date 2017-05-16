package com.suhang.networkmvp.interfaces;


import com.suhang.layoutfinderannotation.FindMethod;
import com.suhang.networkmvp.domain.GithubBean;

import io.reactivex.Flowable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by sh on 2016/10/24 16:01.
 */
@FindMethod
public interface INetworkOtherService{
    @GET("history/content/{user}")
    Flowable<GithubBean> getGithubData(@Path("user") String path);
}
