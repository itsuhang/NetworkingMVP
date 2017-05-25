package com.suhang.networkmvp.domain

/**
 * Created by 苏杭 on 2017/4/24 16:19.
 */

data class AppMain(var total:String?,var list:List<ListEntity>?){

    data class ListEntity(var lvid: String?, var uid: String?, var nick: String?, var head: String?, var gameID: String?, var poster: String?, var title: String?, var stime: String?, var orientation: String?, var vtype: String?, var gameName: String?, var userCount: String?)
}
