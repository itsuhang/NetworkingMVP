package com.suhang.networkmvp.mvp.model

/**
 * Created by 苏杭 on 2017/6/1 20:08.
 */
interface IHomeModel :IBaseModel{
    fun download()
    fun cancelDownload()
    fun getHomeData()
    fun getHomeData(count: Int, position: Int)
    fun getHomeData(count: Int, position: List<Int>)
    fun getLoadMore(page: Int)
    fun deleteHistory(luid: String, position: Int)
    fun deleteHistory(luids: List<String>, positions: List<Int>)
}