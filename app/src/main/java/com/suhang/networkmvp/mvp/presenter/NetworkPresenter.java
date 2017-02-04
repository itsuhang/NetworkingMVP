package com.suhang.networkmvp.mvp.presenter;


import android.text.TextUtils;


import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.base.BasePresenter;
import com.suhang.networkmvp.mvp.contract.INetworkContract;
import com.suhang.networkmvp.mvp.model.NetworkModel;

import java.io.File;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by sh on 2016/10/25 17:41. MVP模式的网络访问帮助类,与页面的MVP模式配套使用,能很方便的处理网络请求
 */

public class NetworkPresenter extends BasePresenter<INetworkContract.INetworkView, NetworkModel> implements INetworkContract.INetworkPresenter {

    @Inject
    public NetworkPresenter(INetworkContract.INetworkView iNetworkView) {
        super(iNetworkView);
    }

    @Override
    public void getData(Class<? extends ErrorBean> aClass, Map<String, String> params, boolean needCache, int tag) {
        loadData(aClass, null, params, null, needCache, tag);
    }

    @Override
    public void getData(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, boolean needCache, int tag) {
        loadData(aClass, append, params, null, needCache, tag);
    }

    @Override
    public void getData(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, String cacheTag, int tag) {
        loadData(aClass, append, params, cacheTag, true, tag);
    }

    @Override
    public void getData(Class<? extends ErrorBean> aClass, Map<String, String> params, String cacheTag, int tag) {
        loadData(aClass, null, params, cacheTag, true, tag);
    }

    @Override
    public void getData(Class<? extends ErrorBean> aClass, String path, Map<String, String> params, int tag) {
        loadData(aClass, path, params, tag);
    }


    private void loadData(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, String cacheTag, boolean needCache, int tag) {
        mView.showLoading(tag);
        if (TextUtils.isEmpty(cacheTag)) {
            mModel.loadData(aClass, append, params, needCache, tag, new OnDataLoadingListener() {
                @Override
                public void onSuccess(ErrorBean o, boolean isNeedLoad) {
                    if (isNeedLoad) {
                        mView.hideLoading(tag);
                    }
                    mView.setData(o, tag);
                }

                @Override
                public void onError(ErrorBean errorBean) {
                    mView.showError(errorBean, tag);
                    mView.hideLoading(tag);
                }

                @Override
                public void onProgress(int percent, boolean isDone) {

                }
            });
        } else {
            mModel.loadData(aClass, append, params, cacheTag, tag, new OnDataLoadingListener() {
                @Override
                public void onSuccess(ErrorBean o, boolean isNeedLoad) {
                    if (isNeedLoad) {
                        mView.hideLoading(tag);
                    }
                    mView.setData(o, tag);
                }

                @Override
                public void onError(ErrorBean errorBean) {
                    mView.showError(errorBean, tag);
                    mView.hideLoading(tag);
                }

                @Override
                public void onProgress(int percent, boolean isDone) {

                }
            });
        }
    }

    private void loadData(Class<? extends ErrorBean> aClass, String path, Map<String, String> params, int tag) {
        mView.showLoading(tag);
        mModel.loadData(aClass, path, params, tag, new OnDataLoadingListener() {
            @Override
            public void onSuccess(ErrorBean o, boolean isNeedLoad) {
                if (isNeedLoad) {
                    mView.hideLoading(tag);
                }
                mView.setData(o, tag);
            }

            @Override
            public void onError(ErrorBean errorBean) {
                mView.showError(errorBean, tag);
                mView.hideLoading(tag);
            }

            @Override
            public void onProgress(int percent, boolean isDone) {

            }
        });
    }


    @Override
    public void cancelNormal(int tag) {
        mModel.cancelNormal(tag);
    }

    @Override
    public void cancelDownload(int tag) {
        mModel.cancelDownload(tag);
    }

    @Override
    public void getDownloadData(String url, String name, String path, int tag) {
        mView.showLoading(tag);
        mModel.download(url, name, path, tag, new OnDataLoadingListener() {
            @Override
            public void onSuccess(ErrorBean o, boolean isNetWork) {
                mView.setData(o, tag);
                mView.hideLoading(tag);
            }

            @Override
            public void onError(ErrorBean errorBean) {
                mView.hideLoading(tag);
            }

            @Override
            public void onProgress(int percent, boolean isDone) {
                mView.progress(percent, tag);
            }
        });
    }


    @Override
    public void uploadFile(Class<? extends ErrorBean> aClass, Map<String, String> params, File file, int tag) {
        mView.showLoading(tag);
        mModel.upload(aClass, params, file, tag, new OnDataLoadingListener() {
            @Override
            public void onSuccess(ErrorBean o, boolean isNetWork) {
                mView.setData(o, tag);
                mView.hideLoading(tag);
            }

            @Override
            public void onError(ErrorBean errorBean) {
                mView.showError(errorBean, tag);
                mView.hideLoading(tag);
            }

            @Override
            public void onProgress(int percent, boolean isDone) {
                mView.progress(percent, tag);
            }
        });
    }
}
