package com.suhang.networkmvp.mvp.model

import android.util.ArrayMap
import com.suhang.networkmvp.domain.AppMain
import com.suhang.networkmvp.domain.GithubBean
import com.suhang.networkmvp.function.NetworkManager
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/4/28 16:33.
 */

class AttentionModel @Inject
constructor() : BaseModel(), IAttentionModel {
    @Inject
    lateinit var mManager: NetworkManager


    fun getAppMainData() {
        info("啊啊啊啊")
        mManager.loadPostDataWrap(AppMain.URL,needCache = true,params = ArrayMap<Any,Any>())
    }

    fun getGithubData() {
        mManager.loadGetData(GithubBean.URL, params = "2/1")
    }
}
