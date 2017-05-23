package com.suhang.networkmvp.function

import android.app.Activity
import android.text.TextUtils
import android.util.ArrayMap
import com.bumptech.glide.disklrucache.DiskLruCache
import com.google.gson.Gson
import com.suhang.networkmvp.constants.Constants
import com.suhang.networkmvp.constants.ErrorCode
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
    private fun dealCache(aClass: Class<*>, append: String, cacheTag: String?, o: Any?, tag: Int) {
        mDisposables?.add(Flowable.create(FlowableOnSubscribe<Any> { e ->
            var at = o
            try {
                val field: Field
                if (TextUtils.isEmpty(append)) {
                    field = aClass.getField(Constants.URL)
                } else {
                    field = aClass.getField(Constants.URL + append)
                }
                val url = field.get(null) as String
                val key: String
                if (cacheTag != null) {
                    key = Md5Util.getMD5(url + cacheTag)
                } else {
                    key = Md5Util.getMD5(url)
                }
                val value = sOpen?.get(key)
                if (o != null) {
                    val s = mGson?.toJson(o)
                    if (value == null || s != value.getString(0)) {
                        val edit = sOpen?.edit(key)
                        edit?.set(0, s)
                        edit?.commit()
                        at = o
                    }
                } else {
                    if (value != null) {
                        at = mGson?.fromJson(value.getString(0), aClass)
                    }
                }
            } catch (e: IOException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_CACHEWR, ErrorCode.ERROR_DESC_CACHEWR + "\n" + e.toString())
                mRxBus?.post(LoadingResult(false, tag))
                mRxBus?.post(ErrorResult(errorBean, tag))
            } catch (e: IllegalAccessException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL + "\n" + e.toString())
                mRxBus?.post(LoadingResult(false, tag))
                mRxBus?.post(ErrorResult(errorBean, tag))
            } catch (e: NoSuchFieldException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_GETURL, ErrorCode.ERROR_DESC_GETURL + "\n" + e.toString())
                mRxBus?.post(LoadingResult(false, tag))
                mRxBus?.post(ErrorResult(errorBean, tag))
            } catch (e: NoSuchAlgorithmException) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_ALGORITHM, ErrorCode.ERROR_DESC_ALGORITHM + "\n" + e.toString())
                mRxBus?.post(LoadingResult(false, tag))
                mRxBus?.post(ErrorResult(errorBean, tag))
            } catch (e: Exception) {
                val errorBean = ErrorBean(ErrorCode.ERROR_CODE_NETWORK_PARAMS, ErrorCode.ERROR_DESC_NETWORK_PARAMS + "\n" + e.toString())
                mRxBus?.post(LoadingResult(false, tag))
                mRxBus?.post(ErrorResult(errorBean, tag))
            }

            if (at != null) {
                e.onNext(at)
            }
        }, BackpressureStrategy.BUFFER).compose(RxUtil.fixScheduler<Any>()).subscribe { errorBean ->
            if (o == null) {
                mRxBus?.post(SuccessResult(errorBean, tag))
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
