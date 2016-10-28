package com.suhang.networkmvp.interfaces;

import com.suhang.networkmvp.MyApplication;

import java.io.File;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by sh on 2016/10/24 16:01.
 */

public interface INetworkService {
	//缓存目录
	String CACHE_PATH = MyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "NetCache";
	//主URL
	String BASE_URL = "http://dev.huanpeng.com/";


//	@POST(GameInfoBean.URL)
//	Observable<GameInfoBean> getGameInfo();
//
//	@POST(HistoryBean.URL)
//	@FormUrlEncoded
//	Observable<HistoryBean> getHistoryInfo(@FieldMap Map<String, String> params);
//
//	@POST(PersonalInfoBean.URL)
//	@FormUrlEncoded
//	Observable<PersonalInfoBean> getPersonalInfo(@FieldMap Map<String, String> params);
}
