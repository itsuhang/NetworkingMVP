package com.suhang.networkmvp.presenter;


import com.suhang.networkmvp.bean.ErrorBean;

import java.io.File;
import java.util.Map;

/**
 * Created by sh on 2016/10/25 17:40.
 */

public interface INetworkPresenter {
	/**
	 * 从Model层获取数据的方法,获取数据后将数据通过View层对象传递给View层显示数据
	 *  @param tag  标记,用于一个页面同时处理多个获取数据的请求
	 * @param params 接口参数
	 */
	void getData(Class aClass, Map<String, String> params, boolean needCache, int tag);

	/**
	 * 取消网络访问或停止上传
	 * @param tag
	 */
	void cancelNormal(int tag);

	/**
	 * 停止下载
	 * @param tag
	 */
	void cancelDownload(int tag);

	/**
	 * 下载文件
	 * @param url
	 * @param name
	 * @param path
	 * @param tag
	 */
	void getDownloadData(String url, String name, String path, int tag);

	/**
	 * 上传文件
	 * @param params
	 * @param file
	 * @param tag
	 */
	void uploadFile(Class aClass ,Map<String, String> params, File file, int tag);

	/**
	 * 取消所有任务
	 */
	void cancelAll();

	/**
	 * 获取数据的回调接口
	 * param type 为获取的Bean类型,默认1为正常类型,2为Error类型
	 */
	interface OnDataLoadingListener {
		void onSuccess(Object o, boolean isNetWork);

		void onError(ErrorBean errorBean);

		void onProgress(int percent, boolean isDone);
	}
}
