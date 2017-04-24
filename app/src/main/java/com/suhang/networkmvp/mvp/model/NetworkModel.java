package com.suhang.networkmvp.mvp.model;

import com.google.gson.Gson;

import android.text.TextUtils;

import com.bumptech.glide.disklrucache.DiskLruCache;
import com.suhang.networkmvp.constants.Constants;
import com.suhang.networkmvp.domain.DownLoadBean;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.domain.ErrorCode;
import com.suhang.networkmvp.domain.WrapBean;
import com.suhang.networkmvp.function.ProgressListener;
import com.suhang.networkmvp.mvp.base.BaseModel;
import com.suhang.networkmvp.mvp.contract.INetworkContract;
import com.suhang.networkmvp.utils.LogUtil;
import com.suhang.networkmvp.utils.Md5Util;
import com.suhang.networkmvp.utils.RetrofitHelper;
import com.suhang.networkmvp.utils.RxUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.FlowableOnSubscribe;
import io.reactivex.FlowableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by sh on 2016/10/25 16:45.
 */

public class NetworkModel extends BaseModel {
    @Inject
    RetrofitHelper mHelper;
    @Inject
    DiskLruCache sOpen;
    @Inject
    Gson mGson;

    private CompositeDisposable mDisposable = new CompositeDisposable();
    private Map<Integer, Disposable> mSubscriptionMap = new HashMap<>();
    private Map<Integer, Call> mCallMap = new HashMap<>();

    @Inject
    public NetworkModel() {
    }

    public <T extends WrapBean> void providerWrap(T t) {

    }

    /**
     * 添加网络任务到队列,以便于取消任务
     */
    private void addDisposable(Disposable disposable, int tag) {
        mSubscriptionMap.put(tag, disposable);
    }

    /**
     * 访问网络获取数据(POST)
     *
     * @param aClass Bean类字节码
     * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
     * @param params 接口参数
     * @param needCache 是否需要缓存
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    public void loadPostData(boolean isWrap, Class<? extends ErrorBean> aClass, String append, Map<String, String> params, boolean needCache, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        if (isWrap) {
            loadPostWrap(aClass, append, params, null, needCache, tag, listener);
        } else {
            loadPost(aClass, append, params, null, needCache, tag, listener);
        }
    }

    /**
     * 访问网络获取数据(POST)
     *
     * @param aClass Bean类字节码
     * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
     * @param params 接口参数
     * @param cacheTag 缓存附加标志,用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    public void loadPostData(boolean isWrap, Class<? extends ErrorBean> aClass, String append, Map<String, String> params, String cacheTag, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        if (isWrap) {
            loadPostWrap(aClass, append, params, cacheTag, true, tag, listener);
        } else {
            loadPost(aClass, append, params, cacheTag, true, tag, listener);
        }
    }

    /**
     * 访问网络获取数据(POST)
     *
     * @param aClass Bean类字节码
     * @param params 接口参数
     * @param cacheTag 缓存附加标志,用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    public void loadPostData(boolean isWrap, Class<? extends ErrorBean> aClass, Map<String, String> params, String cacheTag, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        if (isWrap) {
            loadPostWrap(aClass, null, params, cacheTag, true, tag, listener);
        } else {
            loadPost(aClass, null, params, cacheTag, true, tag, listener);
        }
    }

    /**
     * 访问网络获取数据(POST)
     *
     * @param aClass Bean类字节码
     * @param params 接口参数
     * @param needCache 是否需要缓存
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    public void loadPostData(boolean isWrap, Class<? extends ErrorBean> aClass, Map<String, String> params, boolean needCache, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        if (isWrap) {
            loadPostWrap(aClass, null, params, null, needCache, tag, listener);
        } else {
            loadPost(aClass, null, params, null, needCache, tag, listener);
        }
    }


    /**
     * 访问网络获取数据(GET)
     *
     * @param aClass Bean类字节码
     * @param path get请求路径
     * @param params 接口参数
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    public void loadGetData(boolean isWrap, Class<? extends ErrorBean> aClass, String path, Map<String, String> params, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        if (isWrap) {
            loadGetWrap(aClass, path, null, params, tag, listener);
        } else {
            loadGet(aClass, path, null, params, tag, listener);
        }
    }

    /**
     * 访问网络获取数据(POST)
     *
     * @param aClass Bean类字节码
     * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
     * @param params 接口参数
     * @param cacheTag 缓存附加标志,用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     * @param needCache 是否需要缓存
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    private void loadPost(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, String cacheTag, boolean needCache, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        if (needCache) {
            dealCache(aClass, append, cacheTag, null, listener);
        }
        Flowable<? extends ErrorBean> flowable = null;
        try {
            flowable = fetch(aClass, append, params);
        } catch (InvocationTargetException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getTargetException().getMessage()));
        } catch (IllegalAccessException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getMessage()));
        } catch (NoSuchMethodException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_METHOD, ErrorCode.ERROR_DESC_NETWORK_METHOD + "\n" + e.getMessage()));
        } catch (NoSuchFieldException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS + "\n" + e.getMessage()));
        }
        if (flowable != null) {
            Disposable disposable = flowable.compose(RxUtil.fixScheduler()).subscribe(o -> {
                if (o.getCode() != null) {
                    listener.onError(o);
                } else {
                    dealCache(aClass, append, cacheTag, o, listener);
                    listener.onSuccess(o, true);
                }
            }, throwable -> {
                ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK);
                errorBean.setType(Constants.ERRORTYPE_TWO);
                LogUtil.i("啊啊啊" + errorBean.getCode() + "   " + errorBean.getDesc() + "  " + throwable);
                listener.onError(errorBean);
            });
            addDisposable(disposable, tag);
        } else {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH);
            errorBean.setType(Constants.ERRORTYPE_TWO);
            listener.onError(errorBean);
        }
    }


    /**
     * 访问网络获取数据(POST)
     * 此方法将获取的包裹类转换为内容类并返回
     *
     * @param aClass Bean类字节码
     * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
     * @param params 接口参数
     * @param cacheTag 缓存附加标志,用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     * @param needCache 是否需要缓存
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    private void loadPostWrap(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, String cacheTag, boolean needCache, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        if (needCache) {
            dealCache(aClass, append, cacheTag, null, listener);
        }
        Flowable<? extends WrapBean> flowable = null;
        try {
            flowable = fetchWrap(aClass, append, params);
        } catch (InvocationTargetException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getTargetException().getMessage()));
        } catch (IllegalAccessException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getMessage()));
        } catch (NoSuchMethodException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_METHOD, ErrorCode.ERROR_DESC_NETWORK_METHOD + "\n" + e.getMessage()));
        } catch (NoSuchFieldException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS + "\n" + e.getMessage()));
        }
        if (flowable != null) {
            Disposable disposable = flowable.compose(RxUtil.handleResultNone()).compose(RxUtil.fixScheduler()).subscribe(o -> {
                if (o.getCode() != null) {
                    listener.onError(o);
                } else {
                    dealCache(aClass, append, cacheTag, o, listener);
                    listener.onSuccess(o, true);
                }
            }, throwable -> {
                ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK);
                errorBean.setType(Constants.ERRORTYPE_TWO);
                LogUtil.i("啊啊啊" + errorBean.getCode() + "   " + errorBean.getDesc() + "  " + throwable);
                listener.onError(errorBean);
            });
            addDisposable(disposable, tag);
        } else {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH);
            errorBean.setType(Constants.ERRORTYPE_TWO);
            listener.onError(errorBean);
        }
    }


    /**
     * 访问网络获取数据(GET)
     *
     * @param aClass Bean类字节码
     * @param path get请求路径
     * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
     * @param params 接口参数
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    private void loadGet(Class<? extends ErrorBean> aClass, String path, String append, Map<String, String> params, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        Flowable<? extends ErrorBean> flowable = null;
        try {
            flowable = fetch(aClass, path, append, params);
        } catch (InvocationTargetException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getTargetException().getMessage()));
        } catch (IllegalAccessException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getMessage()));
        } catch (NoSuchMethodException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_METHOD, ErrorCode.ERROR_DESC_NETWORK_METHOD + "\n" + e.getMessage()));
        } catch (NoSuchFieldException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS + "\n" + e.getMessage()));
        }
        if (flowable != null) {
            Disposable disposable = flowable.compose(RxUtil.fixScheduler()).subscribe(o -> {
                if (o.getCode() != null) {
                    listener.onError(o);
                } else {
                    listener.onSuccess(o, true);
//                    dealCache(aClass, append, cacheTag, o, listener);
                }
            }, throwable -> {
                ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK);
                errorBean.setType(Constants.ERRORTYPE_TWO);
                LogUtil.i("啊啊啊" + errorBean.getCode() + "   " + errorBean.getDesc() + "  " + throwable);
                listener.onError(errorBean);
            });
            addDisposable(disposable, tag);
        } else {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH);
            errorBean.setType(Constants.ERRORTYPE_TWO);
            listener.onError(errorBean);
        }
    }

    /**
     * 访问网络获取数据(GET)
     * 此方法将获取的包裹类转换为内容类并返回
     *
     * @param aClass Bean类字节码
     * @param path get请求路径
     * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
     * @param params 接口参数
     * @param tag 标记,用于一个页面同时处理多个获取数据的请求
     * @param listener 成功和错误等回调
     */
    private void loadGetWrap(Class<? extends ErrorBean> aClass, String path, String append, Map<String, String> params, int tag, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        Flowable<? extends WrapBean> flowable = null;
        try {
            flowable = fetchWrap(aClass, path, append, params);
        } catch (InvocationTargetException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getTargetException().getMessage()));
        } catch (IllegalAccessException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getMessage()));
        } catch (NoSuchMethodException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_METHOD, ErrorCode.ERROR_DESC_NETWORK_METHOD + "\n" + e.getMessage()));
        } catch (NoSuchFieldException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS + "\n" + e.getMessage()));
        }
        if (flowable != null) {
            Disposable disposable = flowable.compose(RxUtil.handleResultNone()).compose(RxUtil.fixScheduler()).subscribe(o -> {
                if (o.getCode() != null) {
                    listener.onError(o);
                } else {
                    listener.onSuccess(o, true);
//                    dealCache(aClass, append, cacheTag, o, listener);
                }
            }, throwable -> {
                ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK);
                errorBean.setType(Constants.ERRORTYPE_TWO);
                LogUtil.i("啊啊啊" + errorBean.getCode() + "   " + errorBean.getDesc() + "  " + throwable);
                listener.onError(errorBean);
            });
            addDisposable(disposable, tag);
        } else {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH);
            errorBean.setType(Constants.ERRORTYPE_TWO);
            listener.onError(errorBean);
        }
    }


    /**
     * 上传文件
     *
     * @param aClass Bean类字节码
     */
    public void upload(Class<? extends ErrorBean> aClass, Map<String, String> params, File file, int tag, INetworkContract.INetworkPresenter.
            OnDataLoadingListener listener) {
        Flowable<? extends ErrorBean> flowable = null;
        try {
            flowable = fetchUpload(aClass, file, params, (currentBytes, contentLength, done) -> listener.onProgress((int) (100f * currentBytes / contentLength), done));
        } catch (IllegalAccessException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getMessage()));
        } catch (InvocationTargetException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_REFLECT_NETWORK, ErrorCode.ERROR_DESC_REFLECT_NETWORK + "\n" + e.getTargetException().getMessage()));
        } catch (NoSuchMethodException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_METHOD, ErrorCode.ERROR_DESC_NETWORK_METHOD + "\n" + e.getMessage()));
        } catch (NoSuchFieldException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS + "\n" + e.getMessage()));
        }
        if (flowable != null) {
            Disposable disposable = flowable.compose(RxUtil.fixScheduler()).subscribe(o -> listener.onSuccess(o, false), throwable -> {
                ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK);
                errorBean.setType(Constants.ERRORTYPE_TWO);
                LogUtil.i("啊啊啊" + errorBean.getCode() + "   " + errorBean.getDesc() + "  " + throwable);
                listener.onError(errorBean);
            });
            addDisposable(disposable, tag);
        } else {
            ErrorBean errorBean = new ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH);
            errorBean.setType(Constants.ERRORTYPE_TWO);
            listener.onError(errorBean);
        }
    }


    /**
     * 从制定的Bean类中通过特殊字段查找Bean类对应的URL 从本地获取缓存,有网则先从本地获取,再从网络获取,写入缓存
     *
     * @param append 一个Bean类对应多个URL时用的标志
     * @param cacheTag 用于给缓存加上分辨标志
     * @param o 网络返回的结果Bean类
     */
    private void dealCache(Class<? extends ErrorBean> aClass, String append, String cacheTag, ErrorBean o, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) {
        mDisposable.add(Flowable.create((FlowableOnSubscribe<ErrorBean>) sub -> {
            ErrorBean at = o;
            try {
                Field field;
                if (TextUtils.isEmpty(append)) {
                    field = aClass.getField(Constants.URL);
                } else {
                    field = aClass.getField(Constants.URL + append);
                }
                String url = (String) field.get(null);
                String key;
                if (cacheTag != null) {
                    key = Md5Util.getMD5(url + cacheTag);
                } else {
                    key = Md5Util.getMD5(url);
                }
                DiskLruCache.Value value = sOpen.get(key);
                if (o != null) {
                    String s = mGson.toJson(o);
                    if (value == null || !s.equals(value.getString(0))) {
                        DiskLruCache.Editor edit = sOpen.edit(key);
                        edit.set(0, s);
                        edit.commit();
                        at = o;
                    }
                } else {
                    if (value != null) {
                        at = mGson.fromJson(value.getString(0), aClass);
                    }
                }
            } catch (IOException e) {
                listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_CACHEWR, ErrorCode.ERROR_DESC_CACHEWR));
            } catch (IllegalAccessException | NoSuchFieldException e) {
                listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL));
            } catch (NoSuchAlgorithmException e) {
                listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_ALGORITHM, ErrorCode.ERROR_DESC_ALGORITHM));
            } catch (Exception e) {
                listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS));
            }
            if (at != null) {
                sub.onNext(at);
            }
        }, BackpressureStrategy.BUFFER).compose(RxUtil.fixScheduler()).subscribe(errorBean -> {
            if (o == null) {
                listener.onSuccess(errorBean, false);
            }
        }));
    }


    /**
     * 通过Class来判断是否有该Bean类对应的抓取数据的方法(POST)
     */
    private Flowable<? extends ErrorBean> fetch(Class<? extends ErrorBean> aClass, String append, Map<String, String> params) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Flowable<? extends ErrorBean> flowable;
        String method;
        if (TextUtils.isEmpty(append)) {
            method = (String) aClass.getDeclaredField("METHOD").get(null);
        } else {
            method = (String) aClass.getDeclaredField("METHOD" + append).get(null);
        }
        flowable = mHelper.fetch(method, params);
        return flowable;
    }

    /**
     * 通过Class来判断是否有该Bean类对应的抓取数据的方法(POST)
     * 此方法获取的是包裹类,需通转换为内容Bean类
     */
    private Flowable<? extends WrapBean> fetchWrap(Class<? extends ErrorBean> aClass, String append, Map<String, String> params) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Flowable<? extends WrapBean> flowable;
        String method;
        if (TextUtils.isEmpty(append)) {
            method = (String) aClass.getDeclaredField("METHOD").get(null);
        } else {
            method = (String) aClass.getDeclaredField("METHOD" + append).get(null);
        }
        flowable = mHelper.fetchWrap(method, params);
        return flowable;
    }

    /**
     * 通过Class来判断是否有该Bean类对应的抓取数据的方法(GET)
     */
    private Flowable<? extends ErrorBean> fetch(Class<? extends ErrorBean> aClass, String path, String append, Map<String, String> params) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Flowable<? extends ErrorBean> flowable;
        String method;
        if (TextUtils.isEmpty(append)) {
            method = (String) aClass.getDeclaredField("METHOD").get(null);
        } else {
            method = (String) aClass.getDeclaredField("METHOD" + append).get(null);
        }
        flowable = mHelper.fetch(method, path, params);
        return flowable;
    }

    /**
     * 通过Class来判断是否有该Bean类对应的抓取数据的方法(GET)
     * 此方法获取的是包裹类,需通转换为内容Bean类
     */
    private Flowable<? extends WrapBean> fetchWrap(Class<? extends ErrorBean> aClass, String path, String append, Map<String, String> params) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Flowable<? extends WrapBean> flowable;
        String method;
        if (TextUtils.isEmpty(append)) {
            method = (String) aClass.getDeclaredField("METHOD").get(null);
        } else {
            method = (String) aClass.getDeclaredField("METHOD" + append).get(null);
        }
        flowable = mHelper.fetchWrap(method, path, params);
        return flowable;
    }

    /**
     * 通过Class来判断是否有该Bean类对应的抓取数据的方法(上传)
     */
    @SuppressWarnings("unchecked")
    private Flowable<? extends ErrorBean> fetchUpload(Class<? extends ErrorBean> aClass, File file, Map<String, String> params, ProgressListener listener) throws InvocationTargetException, IllegalAccessException, NoSuchFieldException, NoSuchMethodException {
        Flowable<? extends ErrorBean> flowable;
        String method = (String) aClass.getDeclaredField("METHOD").get(null);
        flowable = mHelper.fetchUpload(method, file, params, listener);
        return flowable;
    }


    /**
     * 下载文件
     *
     * @param url 下载地址
     * @param name 文件名
     * @param path 文件保存路径
     * @param tag 任务标记
     */
    public void download(String url, String name, String path, int tag, INetworkContract.INetworkPresenter.
            OnDataLoadingListener listener) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        mCallMap.put(tag, call);
        try {
            saveFile(call.execute(), path, name, listener);
        } catch (IOException e) {
            listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_DOWNLOAD, ErrorCode.ERROR_DESC_DOWNLOAD));
        }
    }

    /**
     * 访问网络获取流,并将流写入到文件中
     *
     * @param response 请求下载的响应体
     * @param destFileDir 下载到的位置
     * @param name 下载后的文件名
     */
    private File saveFile(Response response, String destFileDir, String name, INetworkContract.INetworkPresenter.OnDataLoadingListener listener) throws IOException {
        InputStream is;
        byte[] buf = new byte[2048];
        FileOutputStream fos;

        is = response.body().byteStream();
        final long total = response.body().contentLength();
        long sum = 0L;
        File dir = new File(destFileDir);
        if (!dir.exists()) {
            if (!dir.mkdirs()) {
                listener.onError(new ErrorBean(ErrorCode.ERROR_CODE_DOWNLOAD_FILE, ErrorCode.ERROR_DESC_DOWNLOAD_FILE));
                return null;
            }
        }

        File file = new File(dir, name);
        fos = new FileOutputStream(file);
        int lastPercent = 0;
        int len1;
        while ((len1 = is.read(buf)) != -1) {
            sum += (long) len1;
            fos.write(buf, 0, len1);
            int percent = (int) ((float) sum * 100f / (float) total);
            if (percent - lastPercent > 1) {
                listener.onProgress(percent, false);
                lastPercent = percent;
            }
        }
        listener.onProgress(100, true);
        listener.onSuccess(new DownLoadBean(file.getAbsolutePath()), false);
        fos.flush();
        is.close();
        fos.close();
        return file;
    }


    /**
     * 取消制定的网络任务
     */
    public void cancelNormal(int tag) {
        if (mSubscriptionMap.get(tag) != null) {
            mSubscriptionMap.get(tag).dispose();
        }
    }

    /**
     * 取消制定的下载任务
     */
    public void cancelDownload(int tag) {
        if (mCallMap.get(tag) != null) {
            mCallMap.get(tag).cancel();
        }
    }

    @Override
    public void destory() {
        super.destory();
        cancelAll();
    }

    /**
     * 取消所有网络任务
     */
    public void cancelAll() {
        for (Map.Entry<Integer, Disposable> entry : mSubscriptionMap.entrySet()) {
            entry.getValue().dispose();
        }
        mSubscriptionMap.clear();
        for (Map.Entry<Integer, Call> entry : mCallMap.entrySet()) {
            entry.getValue().cancel();
        }
        mCallMap.clear();
    }
}
