package com.suhang.networkmvp.interfaces

import com.suhang.networkmvp.constants.DEFAULT_TAG
import io.reactivex.Flowable
import java.io.File


/**
 * Created by 苏杭 on 2017/5/23 15:56.
 */
interface INetworkManager {
    companion object {
        val GET: Int = 1
        val POST: Int = 2
    }

    /**
     * 访问网络获取数据(POST)(转换为所需bean类)
     * @param aClass Bean类字节码
     *
     *
     * @param cacheTag 缓存附加标志(以URL+cacheTag缓存),用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求
     *
     * @param objects 接口参数(根据Retrofit的Service中对应的方法参数传入)
     *
     * @param append 附加信息,随着请求传入,通过Result返回(example:历史删除某条数据,删除成功后返回结果,这时需要知道是哪个位置被删除了,可以通过此参数传入)
     */
    fun loadPostData(url: String, whichTag: Int = DEFAULT_TAG, needCache: Boolean = false, cacheTag: String? = null, append: Any? = null, vararg params: Any)

    /**
     * 访问网络获取数据(GET)(转换为所需bean类)
     * @param aClass Bean类字节码
     *
     *
     * @param cacheTag 缓存附加标志(以URL+cacheTag缓存),用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求
     *
     * @param objects 接口参数(根据Retrofit的Service中对应的方法参数传入)
     *
     * @param append 附加信息,随着请求传入,通过Result返回(example:历史删除某条数据,删除成功后返回结果,这时需要知道是哪个位置被删除了,可以通过此参数传入)
     */
    fun loadGetData(url: String, whichTag: Int = DEFAULT_TAG, needCache: Boolean = false, cacheTag: String? = null, append: Any? = null, vararg params: Any)

    /**
     * 访问网络获取数据(POST)(获取包裹类,并转换为所需bean类)
     * @param aClass Bean类字节码
     *
     *
     * @param cacheTag 缓存附加标志(以URL+cacheTag缓存),用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求
     *
     * @param objects 接口参数(根据Retrofit的Service中对应的方法参数传入)
     *
     * @param append 附加信息,随着请求传入,通过Result返回(example:历史删除某条数据,删除成功后返回结果,这时需要知道是哪个位置被删除了,可以通过此参数传入)
     */
    fun loadPostDataWrap(url: String, whichTag: Int = DEFAULT_TAG, needCache: Boolean = false, cacheTag: String? = null, append: Any? = null, vararg params: Any)

    /**
     * 访问网络获取数据(GET)(获取包裹类,并转换为所需bean类)
     * @param aClass Bean类字节码
     *
     *
     * @param cacheTag 缓存附加标志(以URL+cacheTag缓存),用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求
     *
     * @param objects 接口参数(根据Retrofit的Service中对应的方法参数传入)
     *
     * @param append 附加信息,随着请求传入,通过Result返回(example:历史删除某条数据,删除成功后返回结果,这时需要知道是哪个位置被删除了,可以通过此参数传入)
     */
    fun loadGetDataWrap(url: String, whichTag: Int = DEFAULT_TAG, needCache: Boolean = false, cacheTag: String? = null, append: Any? = null, vararg params: Any)

    /**
     * 下载文件
     * @param url 下载地址
     *
     * @param path 保存路径
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求(并可用于取消下载)
     *
     * @param params 参数
     */
    fun download(url: String, path: String, whichTag: Int = DEFAULT_TAG, vararg params: Any)

    /**
     * 上传文件
     * @param url 下载地址
     *
     * @param file 文件体
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求(并可用于取消上传)
     *
     * @param params 参数
     */
    fun upload(url: String, file: File, whichTag: Int = DEFAULT_TAG, append: Any? = null, param: Map<String, String>, vararg params: Any)

    /**
     * 上传文件(返回带包裹类并处理成需要的Bean类)
     * @param url 下载地址
     *
     * @param file 文件体
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求(并可用于取消上传)
     *
     * @param params 参数
     */
    fun uploadWrap(url: String, file: File, whichTag: Int = DEFAULT_TAG, append: Any? = null, param: Map<String, String>, vararg params: Any)

    /**
     * 访问网络获取数据(转换为所需bean类)
     * 该方法与返回值传入 zip()方法中,用于同时请求两个数据,并在拿到数据,处理后再返回
     * @param aClass Bean类字节码
     *
     *
     * @param cacheTag 缓存附加标志(以URL+cacheTag缓存),用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求
     *
     * @param objects 接口参数(根据Retrofit的Service中对应的方法参数传入)
     *
     * @param append 附加信息,随着请求传入,通过Result返回(example:历史删除某条数据,删除成功后返回结果,这时需要知道是哪个位置被删除了,可以通过此参数传入)
     */
    fun getFlowable(url: String, whichTag: Int = DEFAULT_TAG, vararg params: Any): FlowableInfo

    /**
     * 访问网络获取数据(获取包裹类,并转换为所需bean类)
     * 该方法与返回值传入 zip()方法中,用于同时请求两个数据,并在拿到数据,处理后再返回
     * @param aClass Bean类字节码
     *
     *
     * @param cacheTag 缓存附加标志(以URL+cacheTag缓存),用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求
     *
     * @param objects 接口参数(根据Retrofit的Service中对应的方法参数传入)
     *
     * @param append 附加信息,随着请求传入,通过Result返回(example:历史删除某条数据,删除成功后返回结果,这时需要知道是哪个位置被删除了,可以通过此参数传入)
     */
    fun getFlowableWrap(url: String, whichTag: Int = DEFAULT_TAG, vararg params: Any): FlowableInfo

    data class FlowableInfo constructor(val flowable: Flowable<Any>?, val tag: Int, val url: String)
    /**
     * 同时处理多个异步任务,并在获取到返回值后进行处理(tag取第一个任务的tag)
     */
    fun zip(info1: FlowableInfo, info2: FlowableInfo)

    /**
     * 同时处理多个异步任务,并在获取到返回值后进行处理
     */
    fun zip(info1: FlowableInfo, info2: FlowableInfo, info3: FlowableInfo)

    /**
     * 同时处理多个异步任务,并在获取到返回值后进行处理
     */
    fun zip(info1: FlowableInfo, info2: FlowableInfo, info3: FlowableInfo, info4: FlowableInfo)

    /**
     * 取消指定的网络任务
     */
    fun cancelNormal(tag: Int)

    /**
     * 资源回收
     */
    fun destroy()
}