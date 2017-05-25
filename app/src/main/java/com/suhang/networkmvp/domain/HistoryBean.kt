package com.suhang.networkmvp.domain

/**
 * Created by 苏杭 on 2016/11/14 11:52.
 */

data class HistoryBean(var total: String, var list: List<ListEntity>) {
    data class ListEntity(var uid: String, var title: String, var orientation: String, var gameName: String, var ctime: String, var isLiving: String, var poster: String, var head: String, var nick: String, var stime: String, var viewCount: String, var isSwipeMenuOpen: Boolean, var isChecked: Boolean)
}
