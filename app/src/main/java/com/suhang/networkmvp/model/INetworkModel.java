package com.suhang.networkmvp.model;


import com.suhang.networkmvp.presenter.INetworkPresenter;

import java.io.File;
import java.util.Map;

/**
 * Created by sh on 2016/10/25 16:44.
 */

public interface INetworkModel {

	/**
	 * 获取数据的方法
	 *
	 * @param params   post请求需要的参数键值对
	 * @param listener 回调接口
	 */
	<T> void loadData(Class<T> aClass, Map<String, String> params, boolean needCache, int tag, INetworkPresenter.OnDataLoadingListener<T> listener);

	/**
	 * 上传文件
	 * @param params
	 * @param file
	 * @param tag 标识
	 * @param listener
	 */
	<T> void upload(Class<T> aClass, Map<String, String> params, File file, int tag, INetworkPresenter.OnDataLoadingListener<T> listener);

	/**
	 * 下载文件
	 *
	 * @param url
	 * @param listener
	 * @param handler
	 */
	void download(String url, String name, String path, int tag, INetworkPresenter.OnDataLoadingListener listener);

	/**
	 * 取消某个请求或者停止上传
	 *
	 * @param tag 该请求的标记
	 */
	void cancelNormal(int tag);

	/**
	 * 停止下载
	 *
	 * @param tag 该请求的标记
	 */
	void cancelDownload(int tag);

	/**
	 * 取消所有请求,用于防止内存泄漏
	 */
	void cancelAll();
}
