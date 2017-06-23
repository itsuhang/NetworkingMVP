package com.suhang.networkmvp.function

import android.util.ArrayMap
import android.webkit.MimeTypeMap
import com.google.gson.Gson
import com.jakewharton.disklrucache.DiskLruCache
import com.suhang.layoutfinder.MethodFinder
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.constants.DEFAULT_TAG
import com.suhang.networkmvp.constants.ErrorCode
import com.suhang.networkmvp.constants.errorMessage
import com.suhang.networkmvp.domain.DownLoadBean
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.domain.ZipData
import com.suhang.networkmvp.function.rx.RxBus
import com.suhang.networkmvp.function.upload.UploadFileRequestBody
import com.suhang.networkmvp.interfaces.ErrorLogger
import com.suhang.networkmvp.interfaces.INetworkManager
import com.suhang.networkmvp.interfaces.INetworkManager.Companion.GET
import com.suhang.networkmvp.interfaces.INetworkManager.Companion.POST
import com.suhang.networkmvp.interfaces.RetrofitHelper
import com.suhang.networkmvp.mvp.result.ErrorResult
import com.suhang.networkmvp.mvp.result.LoadingResult
import com.suhang.networkmvp.mvp.result.SuccessResult
import com.suhang.networkmvp.utils.FileUtils
import com.suhang.networkmvp.utils.Md5Util
import com.suhang.networkmvp.utils.RxUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.functions.Function3
import io.reactivex.functions.Function4
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import okhttp3.ResponseBody
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/5/23 17:37.
 */
@BaseScope
class NetworkManager @Inject constructor() : INetworkManager, AnkoLogger, ErrorLogger {
    companion object {
        val pattern: String = "@packname@"
    }

    @Inject
    lateinit var mRxBus: RxBus
    @Inject
    lateinit var mDisposables: CompositeDisposable
    @Inject
    lateinit var sOpen: DiskLruCache
    @Inject
    lateinit var mGson: Gson

    private val mSubscriptionMap = ArrayMap<Int, Disposable>()

    override fun upload(url: String, file: File, whichTag: Int, append: Any?, param: Map<String, String>, vararg params: Any) {
        doUpload(url, file, whichTag, append, param = param, params = params)
    }

    override fun uploadWrap(url: String, file: File, whichTag: Int, append: Any?, param: Map<String, String>, vararg params: Any) {
        doUpload(url, file, whichTag, append, true, param = param, params = params)
    }

    private fun doUpload(url: String, file: File, whichTag: Int, append: Any?, isWrap: Boolean = false, param: Map<String, String>, vararg params: Any) {
        val requestBodyMap = ArrayMap<String, RequestBody>()
        val fileRequestBody = UploadFileRequestBody(file, MediaType.parse(getMimeType(file.absolutePath)))
        requestBodyMap.put("file\"; filename=\"" + file.name, fileRequestBody)
        for ((key, value) in param) {
            requestBodyMap.put(key, RequestBody.create(null, value))
        }
        var flowable: Flowable<Any>? = null
        try {
            flowable = MethodFinder.find(url, *params)
        } catch (e: Exception) {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK_FINDMETHOD, ErrorCode.ERROR_DESC_NETWORK_FINDMETHOD, errorMessage(e))
            mRxBus.post(LoadingResult(false, whichTag))
            mRxBus.post(ErrorResult(errorBean, whichTag))
        }

        if (flowable != null) {
            val compose: Flowable<Any>
            if (isWrap) {
                compose = flowable.onBackpressureDrop().compose(RxUtil.handleResultNone()).compose(RxUtil.fixScheduler())
            } else {
                compose = flowable.onBackpressureDrop().compose(RxUtil.fixScheduler())
            }
            val disposable = compose.subscribe({ o ->
                mRxBus.post(LoadingResult(false, whichTag))
                val successResult = SuccessResult(o, whichTag)
                successResult.append = append
                mRxBus.post(successResult)
            }, { throwable ->
                val stringFlowable = RetrofitHelper.find(url, params)
                if (stringFlowable != null) {
                    mDisposables.add(stringFlowable.onBackpressureDrop().subscribeOn(Schedulers.computation()).subscribe({
                        warn("data:" + it)
                    }))
                }
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK, url, type = ErrorBean.TYPE_SHOW)
                errorBean.run {
                    mRxBus.post(LoadingResult(false, whichTag))
                    mRxBus.post(ErrorResult(this, whichTag))
                    warn(errorMessage(throwable))
                }
            })
            addDisposable(disposable, whichTag)
        } else {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH + "flowable is null", url, type = ErrorBean.TYPE_SHOW)
            errorBean.run {
                mRxBus.post(LoadingResult(false, whichTag))
                mRxBus.post(ErrorResult(this, whichTag))
                warn(toString())
            }
        }
    }

    override fun download(url: String, path: String, whichTag: Int, vararg params: Any) {
        var flowable: Flowable<Any>? = null
        try {
            flowable = MethodFinder.find(url, *params)
        } catch (e: Exception) {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK_FINDMETHOD, ErrorCode.ERROR_DESC_NETWORK_FINDMETHOD, errorMessage(e))
            mRxBus.post(LoadingResult(false, whichTag))
            mRxBus.post(ErrorResult(errorBean, whichTag))
        }
        if (flowable != null) {
            val disposable = flowable.subscribeOn(Schedulers.computation()).unsubscribeOn(Schedulers.computation()).map(Function<Any, InputStream> {
                val responseBody = it as ResponseBody
                return@Function responseBody.byteStream()
            }).observeOn(Schedulers.computation()).doOnNext({
                val file: File = File(path)
                FileUtils.writeFile(it, file)
            }).observeOn(AndroidSchedulers.mainThread()).subscribe({
                val download: DownLoadBean = DownLoadBean(path)
                mRxBus.post(SuccessResult(download, whichTag))
            }, {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK, url, type = ErrorBean.TYPE_SHOW)
                errorBean.run {
                    mRxBus.post(LoadingResult(false, whichTag))
                    mRxBus.post(ErrorResult(this, whichTag))
                    warn(errorMessage(it))
                }
            })
            addDisposable(disposable, whichTag)
        } else {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH + "flowable is null", url, type = ErrorBean.TYPE_SHOW)
            errorBean.run {
                mRxBus.post(LoadingResult(false, whichTag))
                mRxBus.post(ErrorResult(this, whichTag))
                warn(toString())
            }
        }
    }


    private fun loadFlowable(url: String, whichTag: Int = DEFAULT_TAG, isWrap: Boolean = false, vararg params: Any): Flowable<Any>? {
        var flowable: Flowable<Any>? = null
        try {
            flowable = MethodFinder.find(url, *params)
        } catch (e: Exception) {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK_FINDMETHOD, ErrorCode.ERROR_DESC_NETWORK_FINDMETHOD, errorMessage(e))
            mRxBus.post(LoadingResult(false, whichTag))
            mRxBus.post(ErrorResult(errorBean, whichTag))
        }
        if (flowable != null) {
            if (isWrap) {
                flowable = flowable.onBackpressureDrop().compose(RxUtil.handleResultNone()).compose(RxUtil.fixScheduler())
            } else {
                flowable = flowable.onBackpressureDrop().compose(RxUtil.fixScheduler())
            }
        }
        return flowable
    }

    override fun getFlowable(url: String, whichTag: Int, vararg params: Any): INetworkManager.FlowableInfo {
        val info: INetworkManager.FlowableInfo = INetworkManager.FlowableInfo(loadFlowable(url, whichTag, params = *params), whichTag, url)
        return info
    }

    override fun getFlowableWrap(url: String, whichTag: Int, vararg params: Any): INetworkManager.FlowableInfo {
        val info: INetworkManager.FlowableInfo = INetworkManager.FlowableInfo(loadFlowable(url, whichTag, true, *params), whichTag, url)
        return info
    }

    override fun zip(info1: INetworkManager.FlowableInfo, info2: INetworkManager.FlowableInfo) {
        addDisposable(info1.flowable?.zipWith<Any, ZipData>(info2.flowable, BiFunction { o, o2 ->
            val map: ArrayMap<Int, Any> = ArrayMap()
            map.put(info1.tag, o)
            map.put(info2.tag, o2)
            ZipData(map)
        })?.subscribe({
            mRxBus.post(SuccessResult(it, info1.tag))
        }, {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK, info1.url + "\n" + info2.url, type = ErrorBean.TYPE_SHOW)
            errorBean.run {
                mRxBus.post(LoadingResult(false, info1.tag))
                mRxBus.post(ErrorResult(this, info1.tag))
                warn(errorMessage(it))
            }
        })!!, info1.tag)
    }

    override fun zip(info1: INetworkManager.FlowableInfo, info2: INetworkManager.FlowableInfo, info3: INetworkManager.FlowableInfo) {
        addDisposable(Flowable.zip<Any, Any, Any, ZipData>(info1.flowable, info2.flowable, info3.flowable, Function3 { t1, t2, t3 ->
            val map: ArrayMap<Int, Any> = ArrayMap()
            map.put(info1.tag, t1)
            map.put(info2.tag, t2)
            map.put(info3.tag, t3)
            ZipData(map)
        })?.subscribe({
            mRxBus.post(SuccessResult(it, info1.tag))
        }, {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK, info1.url + "\n" + info2.url + "\n" + info3.url, type = ErrorBean.TYPE_SHOW)
            errorBean.run {
                mRxBus.post(LoadingResult(false, info1.tag))
                mRxBus.post(ErrorResult(this, info1.tag))
                warn(errorMessage(it))
            }
        })!!, info1.tag)
    }

    override fun zip(info1: INetworkManager.FlowableInfo, info2: INetworkManager.FlowableInfo, info3: INetworkManager.FlowableInfo, info4: INetworkManager.FlowableInfo) {
        addDisposable(Flowable.zip<Any, Any, Any, Any, ZipData>(info1.flowable, info2.flowable, info3.flowable, info4.flowable, Function4 { t1, t2, t3, t4 ->
            val map: ArrayMap<Int, Any> = ArrayMap()
            map.put(info1.tag, t1)
            map.put(info2.tag, t2)
            map.put(info3.tag, t3)
            map.put(info4.tag, t4)
            ZipData(map)
        })?.subscribe({
            mRxBus.post(SuccessResult(it, info1.tag))
        }, {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK, info1.url + "\n" + info2.url + "\n" + info3.url, type = ErrorBean.TYPE_SHOW)
            errorBean.run {
                mRxBus.post(LoadingResult(false, info1.tag))
                mRxBus.post(ErrorResult(this, info1.tag))
                warn(errorMessage(it))
            }
        })!!, info1.tag)
    }

    private fun load(requestWay: Int, url: String, whichTag: Int = DEFAULT_TAG, needCache: Boolean = false, cacheTag: String? = null, append: Any? = null, isWrap: Boolean = false, vararg params: Any) {
        mRxBus.post(LoadingResult(true, whichTag))
        if (needCache && requestWay == POST) {
            dealCache(url, cacheTag, tag = whichTag)
        }
        var flowable: Flowable<Any>? = null
        try {
            flowable = MethodFinder.find(url, *params)
        } catch (e: Exception) {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK_FINDMETHOD, ErrorCode.ERROR_DESC_NETWORK_FINDMETHOD, errorMessage(e))
            mRxBus.post(LoadingResult(false, whichTag))
            mRxBus.post(ErrorResult(errorBean, whichTag))
        }
        if (flowable != null) {
            val compose: Flowable<Any>
            if (isWrap) {
                compose = flowable.onBackpressureDrop().compose(RxUtil.handleResultNone()).compose(RxUtil.fixScheduler())
            } else {
                compose = flowable.onBackpressureDrop().compose(RxUtil.fixScheduler())
            }
            val disposable = compose.subscribe({ o ->
                mRxBus.post(LoadingResult(false, whichTag))
                val successResult = SuccessResult(o, whichTag)
                successResult.append = append
                if (requestWay == POST) {
                    dealCache(url, cacheTag, o, tag = whichTag)
                }
                mRxBus.post(successResult)
            }, { throwable ->
                val stringFlowable = RetrofitHelper.find(url, params)
                if (stringFlowable != null) {
                    mDisposables.add(stringFlowable.onBackpressureDrop().subscribeOn(Schedulers.computation()).subscribe({
                        warn("data:" + it)
                    }))
                }
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK, url, type = ErrorBean.TYPE_SHOW)
                errorBean.run {
                    mRxBus.post(LoadingResult(false, whichTag))
                    mRxBus.post(ErrorResult(this, whichTag))
                    warn(errorMessage(throwable))
                }
            })
            addDisposable(disposable, whichTag)
        } else {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH + "flowable is null", url, type = ErrorBean.TYPE_SHOW)
            errorBean.run {
                mRxBus.post(LoadingResult(false, whichTag))
                mRxBus.post(ErrorResult(this, whichTag))
                warn(toString())
            }
        }
    }

    override fun loadGetData(url: String, whichTag: Int, needCache: Boolean, cacheTag: String?, append: Any?, vararg params: Any) {
        load(GET, url, whichTag, needCache, cacheTag, append, params = *params)
    }

    override fun loadGetDataWrap(url: String, whichTag: Int, needCache: Boolean, cacheTag: String?, append: Any?, vararg params: Any) {
        load(GET, url, whichTag, needCache, cacheTag, append, true, *params)
    }

    override fun loadPostDataWrap(url: String, whichTag: Int, needCache: Boolean, cacheTag: String?, append: Any?, vararg params: Any) {
        load(POST, url, whichTag, needCache, cacheTag, append, true, *params)
    }

    override fun loadPostData(url: String, whichTag: Int, needCache: Boolean, cacheTag: String?, append: Any?, vararg params: Any) {
        load(POST, url, whichTag, needCache, cacheTag, append, params = *params)
    }

    /**
     * 从制定的Bean类中通过特殊字段查找Bean类对应的URL 从本地获取缓存,有网则先从本地获取,再从网络获取,写入缓存

     * @param append 一个Bean类对应多个URL时用的标志
     * *
     * @param cacheTag 用于给缓存加上分辨标志
     * *
     * @param o 网络返回的结果Bean类
     */
    fun dealCache(url: String, cacheTag: String? = null, o: Any? = null, tag: Int = DEFAULT_TAG) {
        val loadingResult = LoadingResult(false, tag)
        mDisposables.add(Flowable.create(FlowableOnSubscribe<Any> { e ->
            var result: Any? = null
            try {
                val key: String
                if (cacheTag.isNullOrBlank()) {
                    key = Md5Util.getMD5(url)
                } else {
                    key = Md5Util.getMD5(url + cacheTag)
                }
                if (o != null) {
                    val aClass: Class<Any> = o.javaClass
                    val s = mGson.toJson(o)
                    val edit = sOpen.edit(key)
                    edit?.set(0, "$s$pattern${aClass.canonicalName}")
                    edit?.commit()
                    result = o
                } else {
                    val value = sOpen.get(key)
                    val split = value?.getString(0)?.split(pattern)
                    if (split?.get(1) != null) {
                        val name = Class.forName(split[1])
                        result = mGson.fromJson(split[0], name)
                    }
                }
            } catch (e: IOException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_CACHEWR, ErrorCode.ERROR_DESC_CACHEWR, errorMessage(e))
                errorBean.run {
                    mRxBus.post(loadingResult)
                    mRxBus.post(ErrorResult(errorBean, tag))
                }
            } catch (e: IllegalAccessException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL, errorMessage(e))
                errorBean.run {
                    mRxBus.post(loadingResult)
                    mRxBus.post(ErrorResult(errorBean, tag))
                }
            } catch (e: NoSuchFieldException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL, errorMessage(e))
                errorBean.run {
                    mRxBus.post(loadingResult)
                    mRxBus.post(ErrorResult(errorBean, tag))
                }
            } catch (e: NoSuchAlgorithmException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_ALGORITHM, ErrorCode.ERROR_DESC_ALGORITHM, errorMessage(e))
                errorBean.run {
                    mRxBus.post(loadingResult)
                    mRxBus.post(ErrorResult(errorBean, tag))
                }
            } catch (e: Exception) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS, errorMessage(e))
                errorBean.run {
                    mRxBus.post(loadingResult)
                    mRxBus.post(ErrorResult(errorBean, tag))
                }
            }

            if (result != null) {
                e.onNext(result)
            }
        }, BackpressureStrategy.BUFFER).compose(RxUtil.fixScheduler()).subscribe { success ->
            //o==null为读取缓存,o!=null 为写缓存操作,写缓存不需要通过这里发送成功结果
            if (o == null) {
                mRxBus.post(SuccessResult(success, tag))
            }
        })
    }

    /**
     * 得到mime类型
     */
    private fun getMimeType(path: String): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(path)
        if (extension != null) {
            val mime = MimeTypeMap.getSingleton()
            type = mime.getMimeTypeFromExtension(extension)
        }
        return type
    }

    /**
     * 添加网络任务到队列,以便于取消任务
     */
    private fun addDisposable(disposable: Disposable, tag: Int) {
        mSubscriptionMap.put(tag, disposable)
        mDisposables.add(disposable)
    }


    override fun cancelNormal(tag: Int) {
        if (mSubscriptionMap[tag] != null) {
            mDisposables.remove(mSubscriptionMap[tag])
        }
    }

    /**
     * 资源回收
     */
    override fun destroy() {
        mDisposables.dispose()
    }
}
