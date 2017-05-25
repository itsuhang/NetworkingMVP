package com.suhang.networkmvp.interfaces


import com.suhang.layoutfinderannotation.FindMethod
import com.suhang.networkmvp.constants.URLS
import com.suhang.networkmvp.domain.AppMain
import com.suhang.networkmvp.domain.DeleteHistoryBean
import com.suhang.networkmvp.domain.HistoryBean
import com.suhang.networkmvp.domain.HuanPeng
import io.reactivex.Flowable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

/**
 * Created by sh on 2016/10/24 16:01.
 */
@FindMethod
interface INetworkService {
    @POST(URLS.URL_APPMAIN)
    @FormUrlEncoded
    fun getAppMain(@FieldMap params: Map<String, String>): Flowable<HuanPeng<AppMain>>

    @POST(URLS.URL_HISTORY)
    @FormUrlEncoded
    fun getHistoryInfo(@FieldMap params: Map<String, String>): Flowable<HuanPeng<HistoryBean>>

    @POST(URLS.URL_HISTORY_DELETE)
    @FormUrlEncoded
    fun deleteHistory(@FieldMap params: Map<String, String>): Flowable<HuanPeng<DeleteHistoryBean>>
}
