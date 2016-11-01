package com.suhang.networkmvp.interfaces;

import com.suhang.networkmvp.MyApplication;
import com.suhang.networkmvp.bean.HotListBean;
import com.suhang.networkmvp.bean.SectionListBean;
import com.suhang.networkmvp.bean.ThemeListBean;

import java.io.File;
import java.util.Map;

import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by sh on 2016/10/24 16:01.
 */

public interface INetworkService {
	//主URL
	String BASE_URL = "http://news-at.zhihu.com/api/4/";


	@GET(HotListBean.URL)
	Observable<HotListBean> getHotList();

	@GET(SectionListBean.URL)
	Observable<SectionListBean> getSectionList();

	@GET(ThemeListBean.URL)
	Observable<ThemeListBean> getThemeList();
}
