package com.suhang.networkmvp.interfaces;


import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.GithubBean;
import com.suhang.networkmvp.domain.HuanPeng;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Created by sh on 2016/10/24 16:01.
 */

public interface INetworkService{
    @POST(AppMain.URL)
    @FormUrlEncoded
    Flowable<HuanPeng<AppMain>> getAppMain(@FieldMap Map<String, String> params);
}
