package com.suhang.networkmvp.mvp.model

import android.util.ArrayMap

import com.suhang.networkmvp.domain.DeleteHistoryBean
import com.suhang.networkmvp.domain.HomeBean
import com.suhang.networkmvp.function.NetworkManager
import com.suhang.networkmvp.ui.fragment.HomeFragment

import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/5/2 16:33.
 */

class HomeModel @Inject
constructor() : BaseModel() {
    @Inject
    lateinit var mModel: NetworkManager

    fun getHomeData() {
        val ls = ArrayMap<String, String>()
        ls.put("uid", "2240")
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        ls.put("size", "10")
        //        mModel.loadPostDataWrap(HomeBean.class, false, HomeFragment.TAG, ls);
    }

    fun getHomeData(count: Int, position: Int) {
        val ls = ArrayMap<String, String>()
        ls.put("uid", "2240")
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        ls.put("size", count.toString())
        //        mModel.setAppendMessage(HomeBean.class, position);
        //        mModel.loadPostDataWrap(HomeBean.class, false, HomeFragment.TAG_LOADMORE_NORMAL, ls);
    }

    fun getHomeData(count: Int, position: List<Int>) {
        val ls = ArrayMap<String, String>()
        ls.put("uid", "2240")
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        ls.put("size", count.toString())
        //        mModel.setAppendMessage(HomeBean.class, position);
        //        mModel.loadPostDataWrap(HomeBean.class, false, HomeFragment.TAG_LOADMORE_NORMAL, ls);
    }

    fun getLoadMore(page: Int) {
        val ls = ArrayMap<String, String>()
        ls.put("uid", "2240")
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        ls.put("size", "10")
        ls.put("page", page.toString())
        //        mModel.loadPostDataWrap(HomeBean.class, false, HomeFragment.TAG_LOADMORE, ls);
    }

    fun deleteHistory(luid: String, position: Int) {
        val params = ArrayMap<String, String>()
        params.put("uid", "2240")
        params.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7")
        params.put("history", luid)
        //        mModel.setAppendMessage(DeleteHistoryBean.class, position);
        //        mModel.loadPostDataWrap(DeleteHistoryBean.class, false, HomeFragment.TAG_DELETE,params);
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
        //        mModel.setAppendMessage(DeleteHistoryBean.class, positions);
        //        mModel.loadPostDataWrap(DeleteHistoryBean.class, false, HomeFragment.TAG_DELETE,params);
    }
}
