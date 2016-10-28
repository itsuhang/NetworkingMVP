package com.suhang.networkmvp.presenter;

import com.suhang.networkmvp.bean.ErrorBean;
import com.suhang.networkmvp.model.INetworkModel;
import com.suhang.networkmvp.model.NetworkModel;
import com.suhang.networkmvp.view.INetworkView;

import java.io.File;
import java.util.Map;

/**
 * Created by sh on 2016/10/25 17:41.
 */

public class NetworkPresenter implements INetworkPresenter {
	private INetworkModel mNetworkModel;
	private INetworkView mNetworkView;

	public NetworkPresenter(INetworkView networkView) {
		mNetworkView = networkView;
		mNetworkModel = new NetworkModel();
	}

	@Override
	public <T> void getData(Class<T> aClass, Map<String, String> params, boolean needCache, int tag) {
		mNetworkView.showLoading();
		mNetworkModel.loadData(aClass, params, needCache, tag, new OnDataLoadingListener<T>() {
			@Override
			public void onSuccess(T t, boolean isNetWork) {
				mNetworkView.setData(t, tag);
				if (isNetWork) {
					mNetworkView.hideLoading();
				}
			}

			@Override
			public void onError(ErrorBean errorBean) {
				mNetworkView.showError(errorBean, tag);
				mNetworkView.hideLoading();
			}

			@Override
			public void onProgress(int percent, boolean isDone) {

			}
		});
	}

	@Override
	public void cancelNormal(int tag) {
		mNetworkModel.cancelNormal(tag);
	}

	@Override
	public void cancelDownload(int tag) {
		mNetworkModel.cancelDownload(tag);
	}

	@Override
	public void getDownloadData(String url, String name, String path, int tag) {
		mNetworkView.showLoading();
		mNetworkModel.download(url, name, path, tag, new OnDataLoadingListener() {
			@Override
			public void onSuccess(Object o, boolean isNetWork) {
				mNetworkView.setData(o, tag);
				mNetworkView.hideLoading();
			}

			@Override
			public void onError(ErrorBean errorBean) {
				mNetworkView.hideLoading();
			}

			@Override
			public void onProgress(int percent, boolean isDone) {
				mNetworkView.progress(percent, tag);
			}
		});
	}

	@Override
	public <T> void uploadFile(Class<T> aClass ,Map<String, String> params, File file, int tag) {
		mNetworkModel.upload(aClass,params, file, tag, new OnDataLoadingListener<T>() {
			@Override
			public void onSuccess(T t, boolean isNetWork) {
				mNetworkView.setData(t, tag);
			}

			@Override
			public void onError(ErrorBean errorBean) {
				mNetworkView.showError(errorBean, tag);
			}

			@Override
			public void onProgress(int percent, boolean isDone) {
				mNetworkView.progress(percent, tag);
			}
		});
	}

	@Override
	public void cancelAll() {
		mNetworkModel.cancelAll();
	}
}
