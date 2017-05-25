package com.suhang.networkmvp.mvp.model

import android.util.ArrayMap
import com.suhang.networkmvp.constants.URLS
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
        mManager.loadPostDataWrap(URLS.URL_APPMAIN,needCache = true,params = ArrayMap<Any,Any>())
    }

    fun getGithubData() {
        mManager.loadGetData(URLS.URL_GITHUB, params = "2/1")
    }
}
