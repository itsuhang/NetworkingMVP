package com.suhang.networkmvp.mvp.model

import android.util.ArrayMap
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.constants.DEFAULT_TAG
import com.suhang.networkmvp.constants.URLS
import com.suhang.networkmvp.function.NetworkManager
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/5/2 16:33.
 */

class HomeModel @Inject
constructor() : BaseModel() {
    @Inject
    lateinit var manager: NetworkManager

    fun download() {
//        manager.initDownload(IDownloadService::class.java, URLS.URL_BASE_DOWNLOAD)
        manager.download(URLS.URL_DOWNLOAD, BaseApp.APP_PATH+"/huanpeng.apk",params = ArrayMap<Any,Any>())
    }

    fun cancelDownload() {
        manager.cancelNormal(DEFAULT_TAG)
    }

    fun getHomeData() {
        val ls = ArrayMap<String, String>()
        ls.put("uid", "2240")
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        ls.put("size", "10")
        //        manager.loadPostDataWrap(HistoryBean.class, false, HomeFragment.TAG, ls);
    }

    fun getHomeData(count: Int, position: Int) {
        val ls = ArrayMap<String, String>()
        ls.put("uid", "2240")
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        ls.put("size", count.toString())
        //        manager.setAppendMessage(HistoryBean.class, position);
        //        manager.loadPostDataWrap(HistoryBean.class, false, HomeFragment.TAG_LOADMORE_NORMAL, ls);
    }

    fun getHomeData(count: Int, position: List<Int>) {
        val ls = ArrayMap<String, String>()
        ls.put("uid", "2240")
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        ls.put("size", count.toString())
        //        manager.setAppendMessage(HistoryBean.class, position);
        //        manager.loadPostDataWrap(HistoryBean.class, false, HomeFragment.TAG_LOADMORE_NORMAL, ls);
    }

    fun getLoadMore(page: Int) {
        val ls = ArrayMap<String, String>()
        ls.put("uid", "2240")
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        ls.put("size", "10")
        ls.put("page", page.toString())
        //        manager.loadPostDataWrap(HistoryBean.class, false, HomeFragment.TAG_LOADMORE, ls);
    }

    fun deleteHistory(luid: String, position: Int) {
        val params = ArrayMap<String, String>()
        params.put("uid", "2240")
        params.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        params.put("history", luid)
        //        manager.setAppendMessage(DeleteHistoryBean.class, position);
        //        manager.loadPostDataWrap(DeleteHistoryBean.class, false, HomeFragment.TAG_DELETE,params);
    }

    fun deleteHistory(luids: List<String>, positions: List<Int>) {
        val params = ArrayMap<String, String>()
        params.put("uid", "2240")
        params.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        val sb = StringBuffer()
        for (i in luids.indices) {
            if (i == 0) {
                sb.append(luids[i])
            } else {
                sb.append("," + luids[i])
            }
        }
        params.put("history", sb.toString())
        //        manager.setAppendMessage(DeleteHistoryBean.class, positions);
        //        manager.loadPostDataWrap(DeleteHistoryBean.class, false, HomeFragment.TAG_DELETE,params);
    }

    override fun destroy() {
        super.destroy()
    }
}
