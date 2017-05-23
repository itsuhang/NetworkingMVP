package com.suhang.networkmvp.mvp.model

import com.suhang.networkmvp.constants.DEFAULT_TAG


/**
 * Created by 苏杭 on 2017/5/23 15:56.
 */
interface INetworkModel {
    companion object{
        val GET: Int = 1
        val POST: Int = 2
    }
    /**
     * 访问网络获取数据(POST)(获取包裹类,并转换为所需bean类)
     * @param aClass Bean类字节码
     *
     * @param append Bean类中URL字段后的附加字段(类似URL1,URL2),用于处理一个Bean类对应多个接口
     *
     * @param needCache 是否需要缓存(默认根据URL缓存)
     *
     * @param cacheTag 缓存附加标志(以URL+cacheTag缓存),用于处理同一个URL,根据传入参数不同得到的数据不同时的缓存方案(POST请求)
     *
     * @param whichTag 标记,用于一个页面同时处理多个获取数据的请求时分辨是哪一个请求
     *
     * @param objects 接口参数(根据Retrofit的Service中对应的方法参数传入)
     */
    fun loadData(requestWay:Int,dataClass: Class<Any>, append: String = "", needCache: Boolean = false, cacheTag: String, whichTag: Int = DEFAULT_TAG,isWrap:Boolean, vararg params: Any)
}