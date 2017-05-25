package com.suhang.networkmvp.interfaces


import com.suhang.layoutfinderannotation.FindMethod
import com.suhang.networkmvp.constants.URLS

import io.reactivex.Flowable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.QueryMap
import retrofit2.http.Streaming

/**
 * Created by sh on 2016/10/27 15:36.
 */
@FindMethod
interface IDownloadService {
    @Streaming
    @GET(URLS.URL_DOWNLOAD)
    fun download(@QueryMap map: Map<String, String>): Flowable<ResponseBody>
}
