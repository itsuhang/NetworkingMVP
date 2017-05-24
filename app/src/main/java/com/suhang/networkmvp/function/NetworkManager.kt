package com.suhang.networkmvp.function

import android.app.Activity
import android.util.ArrayMap
import com.bumptech.glide.disklrucache.DiskLruCache
import com.google.gson.Gson
import com.suhang.layoutfinder.MethodFinder
import com.suhang.networkmvp.constants.*
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.mvp.model.INetworkManager
import com.suhang.networkmvp.mvp.model.INetworkManager.Companion.GET
import com.suhang.networkmvp.mvp.model.INetworkManager.Companion.POST
import com.suhang.networkmvp.mvp.result.ErrorResult
import com.suhang.networkmvp.mvp.result.LoadingResult
import com.suhang.networkmvp.mvp.result.SuccessResult
import com.suhang.networkmvp.utils.Md5Util
import com.suhang.networkmvp.utils.RxUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableOnSubscribe
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.Call
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.warn
import java.io.IOException
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/5/23 17:37.
 */

class NetworkManager @Inject constructor() : INetworkManager, AnkoLogger {

    @Inject
    lateinit var mRxBus: RxBus
    @Inject
    lateinit var mDisposables: CompositeDisposable
    @Inject
    lateinit var sOpen: DiskLruCache
    @Inject
    lateinit var mGson: Gson

    private val mSubscriptionMap = ArrayMap<Int, Disposable>()
    private val mCallMap = ArrayMap<Int, Call>()

    private fun load(requestWay: Int, url: String, whichTag: Int = DEFAULT_TAG, needCache: Boolean = false, cacheTag: String? = null, append: Any? = null, isWrap: Boolean = false, vararg params: Any) {
        mRxBus.post(LoadingResult(true, whichTag))
        var flowable: Flowable<Any>? = null
        try {
            flowable = MethodFinder.find(url, *params)
        } catch (e: Exception) {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK_FINDMETHOD, ErrorCode.ERROR_DESC_NETWORK_FINDMETHOD, errorMessage(e))
            mRxBus.post(LoadingResult(false, whichTag))
            mRxBus.post(ErrorResult(errorBean, whichTag))
        }
        if (needCache && requestWay == POST) {
            dealCache(url, cacheTag, tag = whichTag)
        }
        if (flowable != null) {
            val compose: Flowable<Any>
            if (isWrap) {
                compose = flowable.onBackpressureDrop().compose(RxUtil.fixScheduler())
            } else {
                compose = flowable.onBackpressureDrop().compose(RxUtil.handleResultNone()).compose(RxUtil.fixScheduler())
            }
            val disposable = compose.subscribe({ o ->
                mRxBus.post(LoadingResult(false, whichTag))
                val successResult = SuccessResult(o, whichTag)
                successResult.append = append
                if (requestWay == POST) {
                    dealCache(url, cacheTag, o, tag = whichTag)
                }
                mRxBus.post(SuccessResult(o, whichTag))
            }, { throwable ->
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK, ErrorCode.ERROR_DESC_NETWORK, errorMessage(throwable))
                errorBean.run {
                    type = Constants.ERRORTYPE_TWO
                    mRxBus.post(LoadingResult(false, whichTag))
                    mRxBus.post(ErrorResult(this, whichTag))
                    warn(toString())
                }
            })
            addDisposable(disposable, whichTag)
        } else {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_FETCH, ErrorCode.ERROR_DESC_FETCH)
            errorBean.run {
                mRxBus.post(LoadingResult(false, whichTag))
                type = Constants.ERRORTYPE_TWO
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
                    edit?.set(0, "$s@${aClass.canonicalName}")
                    edit?.commit()
                    result = o
                } else {
                    val value = sOpen.get(key)
                    val split = value?.getString(0)?.split("@")
                    val name = Class.forName(split?.get(1))
                    result = mGson.fromJson(split?.get(0), name)
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
        }, BackpressureStrategy.BUFFER).compose(RxUtil.fixScheduler()).subscribe { error ->
            if (o == null) {
                mRxBus.post(SuccessResult(error, tag))
            }
        })
    }


    /**
     * 添加网络任务到队列,以便于取消任务
     */
    private fun addDisposable(disposable: Disposable, tag: Int) {
        mSubscriptionMap.put(tag, disposable)
    }


    /**
     * 取消制定的网络任务
     */
    fun cancelNormal(tag: Int) {
        if (mSubscriptionMap[tag] != null) {
            mSubscriptionMap[tag]?.dispose()
        }
    }

    /**
     * 取消制定的下载任务
     */
    fun cancelDownload(tag: Int) {
        if (mCallMap[tag] != null) {
            mCallMap[tag]?.cancel()
        }
    }


    /**
     * 取消所有网络任务
     */
    fun cancelAll() {
        for ((_, value) in mSubscriptionMap) {
            value.dispose()
        }
        mSubscriptionMap.clear()
        for ((_, value) in mCallMap) {
            value.cancel()
        }
        mCallMap.clear()
    }


    /**
     * 资源回收
     */
    fun destory() {
        mDisposables.dispose()
        cancelAll()
    }
}
