package com.suhang.networkmvp.interfaces;


import com.suhang.layoutfinderannotation.FindMethod;
import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.DeleteHistoryBean;
import com.suhang.networkmvp.domain.HomeBean;
import com.suhang.networkmvp.domain.HuanPeng;

import java.util.Map;

import io.reactivex.Flowable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by sh on 2016/10/24 16:01.
 */
@FindMethod
public interface INetworkService {
    @POST(AppMain.URL)
    @FormUrlEncoded
    Flowable<HuanPeng<AppMain>> getAppMain(@FieldMap Map<String, String> params);

    @POST(HomeBean.URL)
    @FormUrlEncoded
    Flowable<HuanPeng<HomeBean>> getHistoryInfo(@FieldMap Map<String, String> params);

    @POST(DeleteHistoryBean.URL)
    @FormUrlEncoded
    Flowable<HuanPeng<DeleteHistoryBean>> deleteHistory(@FieldMap Map<String, String> params);
}
