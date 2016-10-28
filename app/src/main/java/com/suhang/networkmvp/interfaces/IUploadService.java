package com.suhang.networkmvp.interfaces;


import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by sh on 2016/10/27 15:36.
 */

public interface IUploadService {
	//主URL
	String BASE_URL = "http://dev.huanpeng.com/";

//	@POST(UploadHeadBean.URL)
//	@Multipart
//	Observable<ResponseBody> getUploadHead(@PartMap Map<String, RequestBody> externalFileParameters);
}
