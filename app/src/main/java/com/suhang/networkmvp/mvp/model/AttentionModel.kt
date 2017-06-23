package com.suhang.networkmvp.mvp.model

import android.util.ArrayMap
import com.suhang.networkmvp.constants.URLS
import com.suhang.networkmvp.interfaces.INetworkManager
import org.jetbrains.anko.info
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/4/28 16:33.
 */
class AttentionModel @Inject constructor():IAttentionModel {
    override fun destroy() {
        info(mManager)
    }

    @Inject
    lateinit var mManager: INetworkManager



    override fun getAppMainData() {
        val flow1 = mManager.getFlowableWrap(URLS.URL_APPMAIN,whichTag = 111,params = ArrayMap<Any,Any>())
        val flow3 = mManager.getFlowableWrap(URLS.URL_APPMAIN,whichTag = 333,params = ArrayMap<Any,Any>())
        val flow2 = mManager.getFlowable(URLS.URL_GITHUB,whichTag = 222, params = "2/1")
        mManager.zip(flow1,flow2,flow3)
//        mManager.loadPostDataWrap(URLS.URL_APPMAIN,needCache = true,params = ArrayMap<Any,Any>())
    }

    override fun getGithubData() {
        mManager.loadGetData(URLS.URL_GITHUB, params = "2/1")
    }
}
