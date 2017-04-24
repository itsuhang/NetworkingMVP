package com.suhang.networkmvp.mvp.contract;


import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.IPresenter;
import com.suhang.networkmvp.mvp.IView;

import java.io.File;
import java.util.Map;

/**
 * Created by 苏杭 on 2016/11/14 8:11.
 */

public interface INetworkContract {

    interface INetworkPresenter extends IPresenter {
        /**
         * 获取数据的方法，根据Bean类中的URL和METHOD属性
         *
         * @param aClass Bean类字节码
         * @param params 接口参数
         * @param needCache 是否需要缓存数据
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getPostData(Class<? extends ErrorBean> aClass, Map<String, String> params, boolean needCache, int tag);

        /**
         * 获取数据，根据Bean类中的URL+append和METHOD+append属性
         *
         * @param aClass Bean类字节码
         * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
         * @param params 接口参数
         * @param needCache 是否需要缓存数据
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getPostData(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, boolean needCache, int tag);

        /**
         * 带有缓存标记
         *
         * @param aClass Bean类字节码
         * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
         * @param params 接口参数
         * @param cacheTag 缓存附加标志,用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getPostData(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, String cacheTag, int tag);

        /**
         * @param aClass Bean类字节码
         * @param params 接口参数
         * @param cacheTag 缓存附加标志,用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getPostData(Class<? extends ErrorBean> aClass, Map<String, String> params, String cacheTag, int tag);

        /**
         * GET
         */
        void getGetData(Class<? extends ErrorBean> aClass, String path, Map<String, String> params, int tag);


        /**
         * 获取数据的方法，根据Bean类中的URL和METHOD属性
         * 此方法获取包裹类
         *
         * @param aClass Bean类字节码
         * @param params 接口参数
         * @param needCache 是否需要缓存数据
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getPostDataWrap(Class<? extends ErrorBean> aClass, Map<String, String> params, boolean needCache, int tag);

        /**
         * 获取数据，根据Bean类中的URL+append和METHOD+append属性
         * 此方法获取包裹类
         *
         * @param aClass Bean类字节码
         * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
         * @param params 接口参数
         * @param needCache 是否需要缓存数据
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getPostDataWrap(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, boolean needCache, int tag);

        /**
         * 带有缓存标记
         * 此方法获取包裹类
         *
         * @param aClass Bean类字节码
         * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
         * @param params 接口参数
         * @param cacheTag 缓存附加标志,用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getPostDataWrap(Class<? extends ErrorBean> aClass, String append, Map<String, String> params, String cacheTag, int tag);

        /**
         * 此方法获取包裹类
         *
         * @param aClass Bean类字节码
         * @param params 接口参数
         * @param cacheTag 缓存附加标志,用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getPostDataWrap(Class<? extends ErrorBean> aClass, Map<String, String> params, String cacheTag, int tag);

        /**
         * 此方法获取包裹类
         * GET
         */
        void getGetDataWrap(Class<? extends ErrorBean> aClass, String path, Map<String, String> params, int tag);


        /**
         * 取消网络访问或停止上传
         *
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void cancelNormal(int tag);

        /**
         * 停止下载
         *
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void cancelDownload(int tag);

        /**
         * 下载文件
         *
         * @param url 下载链接
         * @param name 文件名
         * @param path 文件下载路径
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void getDownloadData(String url, String name, String path, int tag);

        /**
         * 上传文件
         *
         * @param params 上传参数
         * @param file 要上传的文件
         * @param tag 标记,用于一个页面同时处理多个获取数据的请求
         */
        void uploadFile(Class<? extends ErrorBean> aClass, Map<String, String> params, File file, int tag);

        /**
         * 获取数据的回调接口 param type 为获取的Bean类型,默认1为正常类型,2为Error类型
         */
        interface OnDataLoadingListener {
            /**
             * 成功回调
             *
             * @param errorBean 返回的数据Bean
             * @param isNeedLoad 是否需要进行加载操作(显示等待框等..)
             */
            void onSuccess(ErrorBean errorBean, boolean isNeedLoad);


            /**
             * 错误回答
             */
            void onError(ErrorBean errorBean);

            /**
             * 进度回调
             */
            void onProgress(int percent, boolean isDone);
        }

    }

    interface INetworkView extends IView {
        /**
         * 得到数据回调给该方法
         */
        void setData(ErrorBean e, int tag);

        /**
         * 进度回调
         */
        void progress(int precent, int tag);

    }

}
