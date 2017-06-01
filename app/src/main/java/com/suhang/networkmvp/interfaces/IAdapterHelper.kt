package com.suhang.networkmvp.interfaces

/**
 * Created by 苏杭 on 2016/11/21 14:39.
 */

interface IAdapterHelper {
    /**
     * 从网络获取了多少条数据
     * @return
     */
    var totalCount:Int

    /**
     * 每一页显示多少条
     * @return
     */
    var pageSize: Int

    /**
     * 总共多少页
     */
    var totalPage: Int
    /**
     * 当前在第几页
     */
    var currentPage: Int

    var nextPage: Int

}
