package com.suhang.networkmvp.utils;

import android.text.TextUtils;
import android.webkit.MimeTypeMap;

import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.domain.WrapBean;
import com.suhang.networkmvp.function.ProgressListener;
import com.suhang.networkmvp.function.UploadFileRequestBody;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import io.reactivex.Flowable;
import okhttp3.MediaType;
import okhttp3.RequestBody;

/**
 * Created by sh on 2016/10/25 16:06. Retrofit工具
 */

public class RetrofitHelper<T> {
    @Inject
    T t;

    @Inject
    public RetrofitHelper() {
    }
    /**
     * 获取资源MIME类型
     */
    public String getMimeType(String path) {
        String type = null;
        String extension = MimeTypeMap.getFileExtensionFromUrl(path);
        if (extension != null) {
            MimeTypeMap mime = MimeTypeMap.getSingleton();
            type = mime.getMimeTypeFromExtension(extension);
        }
        return type;
    }

    /**
     * 反射查找Retrofit的Service中的方法(用于上传)
     *
     * @param method 要查找的方法名
     * @param file 要上传的文件
     * @param params 上传要带的参数
     */
    @SuppressWarnings("unchecked")
    public Flowable<? extends ErrorBean> fetchUpload(String method, File file, Map<String, String> params, ProgressListener listener) throws InvocationTargetException, IllegalAccessException, NoSuchMethodException {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        UploadFileRequestBody fileRequestBody = new UploadFileRequestBody(file, MediaType.parse(getMimeType(file.getAbsolutePath())), listener);
        requestBodyMap.put("file\"; filename=\"" + file.getName(), fileRequestBody);
        for (Map.Entry<String, String> entry : params.entrySet()) {
            requestBodyMap.put(entry.getKey(), RequestBody.create(null, entry.getValue()));
        }
        Method fetch = t.getClass().getDeclaredMethod(method, Map.class);
        return (Flowable<? extends ErrorBean>) fetch.invoke(t, requestBodyMap);
    }

    /**
     * 反射查找Retrofit的Service中的方法
     *
     * @param method 要查找的方法名
     * @param params 要携带的参数
     */
    @SuppressWarnings("unchecked")
    public Flowable<? extends ErrorBean> fetch(String method, Map<String, String> params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method fetch = t.getClass().getDeclaredMethod(method, Map.class);
        return (Flowable<? extends ErrorBean>) fetch.invoke(t, params);
    }

    /**
     * 反射查找Retrofit的Service中的方法
     * 此方法获取的是包裹类
     *
     * @param method 要查找的方法名
     * @param params 要携带的参数
     */
    @SuppressWarnings("unchecked")
    public Flowable<? extends WrapBean> fetchWrap(String method, Map<String, String> params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Method fetch = t.getClass().getDeclaredMethod(method, Map.class);
        return (Flowable<? extends WrapBean>) fetch.invoke(t, params);
    }

    /**
     * 反射查找Retrofit的Service中的方法
     *
     * @param method 要查找的方法名(没有则传null)
     * @param path get请求未确定路径(不需要则传null)
     * @param params 要携带的参数
     */
    @SuppressWarnings("unchecked")
    public Flowable<? extends ErrorBean> fetch(String method, String path, Map<String, String> params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (TextUtils.isEmpty(path) && params != null) {
            Method fetch = t.getClass().getDeclaredMethod(method, Map.class);
            return (Flowable<? extends ErrorBean>) fetch.invoke(t, params);
        } else if (TextUtils.isEmpty(path) && params == null) {
            Method fetch = t.getClass().getDeclaredMethod(method);
            return (Flowable<? extends ErrorBean>) fetch.invoke(t);
        } else if (!TextUtils.isEmpty(path) && params != null) {
            Method fetch = t.getClass().getDeclaredMethod(method, String.class, Map.class);
            return (Flowable<? extends ErrorBean>) fetch.invoke(t, path, params);
        } else {
            Method fetch = t.getClass().getDeclaredMethod(method, String.class);
            return (Flowable<? extends ErrorBean>) fetch.invoke(t, path);
        }
    }

    /**
     * 反射查找Retrofit的Service中的方法
     * 此方法获取的是包裹类
     *
     * @param method 要查找的方法名(没有则传null)
     * @param path get请求未确定路径(不需要则传null)
     * @param params 要携带的参数
     */
    @SuppressWarnings("unchecked")
    public Flowable<? extends WrapBean> fetchWrap(String method, String path, Map<String, String> params) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (TextUtils.isEmpty(path) && params != null) {
            Method fetch = t.getClass().getDeclaredMethod(method, Map.class);
            return (Flowable<? extends WrapBean>) fetch.invoke(t, params);
        } else if (TextUtils.isEmpty(path) && params == null) {
            Method fetch = t.getClass().getDeclaredMethod(method);
            return (Flowable<? extends WrapBean>) fetch.invoke(t);
        } else if (!TextUtils.isEmpty(path) && params != null) {
            Method fetch = t.getClass().getDeclaredMethod(method, String.class, Map.class);
            return (Flowable<? extends WrapBean>) fetch.invoke(t, path, params);
        } else {
            Method fetch = t.getClass().getDeclaredMethod(method, String.class);
            return (Flowable<? extends WrapBean>) fetch.invoke(t, path);
        }
    }
}
