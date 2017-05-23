package com.suhang.networkmvp.function

import android.app.Activity
import android.text.TextUtils
import android.util.ArrayMap
import com.bumptech.glide.disklrucache.DiskLruCache
import com.google.gson.Gson
import com.suhang.networkmvp.constants.*
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.mvp.model.INetworkModel
import com.suhang.networkmvp.mvp.result.ErrorResult
import com.suhang.networkmvp.mvp.result.LoadingResult
import com.suhang.networkmvp.mvp.result.SuccessResult
import com.suhang.networkmvp.utils.Md5Util
import com.suhang.networkmvp.utils.RxUtil
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.FlowableOnSubscribe
import io.reactivex.annotations.NonNull
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import okhttp3.Call
import org.jetbrains.anko.AnkoLogger
import java.io.IOException
import java.lang.reflect.Field
import java.security.NoSuchAlgorithmException
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/5/23 17:37.
 */

class NetworkManager : INetworkModel, AnkoLogger {

    @Inject
    protected var mRxBus: RxBus? = null
    @Inject
    internal var mDisposables: CompositeDisposable? = null
    @Inject
    internal var mActivity: Activity? = null
    @Inject
    internal var sOpen: DiskLruCache? = null
    @Inject
    internal var mGson: Gson? = null

    private val mSubscriptionMap = ArrayMap<Int, Disposable>()
    private val mCallMap = ArrayMap<Int, Call>()
    private val mMessages = ArrayMap<Class<out ErrorBean>, Any>()
    @Inject
    fun NetworkModel() {

    }

    override fun loadData(requestWay: Int, dataClass: Class<Any>, append: String, needCache: Boolean, cacheTag: String, whichTag: Int, isWrap: Boolean, vararg params: Any) {
        when (requestWay) {
            INetworkModel.GET -> {
                mRxBus?.post(LoadingResult(true, whichTag))
                if (needCache) {
                }
                var flowable: Flowable<out ErrorBean>? = null


            }

            INetworkModel.POST -> {


            }
        }
    }


    /**
     * 从制定的Bean类中通过特殊字段查找Bean类对应的URL 从本地获取缓存,有网则先从本地获取,再从网络获取,写入缓存

     * @param append 一个Bean类对应多个URL时用的标志
     * *
     * @param cacheTag 用于给缓存加上分辨标志
     * *
     * @param o 网络返回的结果Bean类
     */
    private fun dealCache(url: String, cacheTag: String? = null, o: Any? = null, tag: Int = DEFAULT_TAG) {
        val loadingResult = LoadingResult(false, tag)
        val errorResult = ErrorResult(tag = tag)
        mDisposables?.add(Flowable.create(FlowableOnSubscribe<Any> { e ->
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
                    val s = mGson?.toJson(o)
                    val edit = sOpen?.edit(key)
                    edit?.set(0, "${s}@${aClass.canonicalName}")
                    edit?.commit()
                    result = o
                } else {
                    val value = sOpen?.get(key)
                    val split = value?.getString(0)?.split("@")
                    val name = Class.forName(split?.get(1))
                    result = mGson?.fromJson(split?.get(0), name)
                }
            } catch (e: IOException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_CACHEWR, ErrorCode.ERROR_DESC_CACHEWR, errorMessage(e))
                mRxBus?.post(loadingResult)
                mRxBus?.post(errorResult.apply { result = errorBean })
            } catch (e: IllegalAccessException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL, errorMessage(e))
                mRxBus?.post(loadingResult)
                mRxBus?.post(errorResult.apply { result = errorBean })
            } catch (e: NoSuchFieldException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL, errorMessage(e))
                mRxBus?.post(loadingResult)
                mRxBus?.post(errorResult.apply { result = errorBean })
            } catch (e: NoSuchAlgorithmException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_ALGORITHM, ErrorCode.ERROR_DESC_ALGORITHM, errorMessage(e))
                mRxBus?.post(loadingResult)
                mRxBus?.post(errorResult.apply { result = errorBean })
            } catch (e: Exception) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS, errorMessage(e))
                mRxBus?.post(loadingResult)
                mRxBus?.post(errorResult.apply { result = errorBean })
            }

            if (result != null) {
                e.onNext(result)
            }
        }, BackpressureStrategy.BUFFER).compose(RxUtil.fixScheduler<Any>()).subscribe { error ->
            if (o == null) {
                mRxBus?.post(SuccessResult(error, tag))
            }
        })
    }


    /**
     * 添加网络任务到队列,以便于取消任务
     */
    private fun addDisposable(disposable: Disposable, tag: Int) {
        mSubscriptionMap.put(tag, disposable)
    }

//    /**
//     * 设置附加信息
//     */
//    fun setAppendMessage(aClass: Class<*>, o: Any) {
//        mMessages.put(aClass, o)
//    }


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
        mDisposables?.dispose()
        mActivity = null
        cancelAll()
    }
}
