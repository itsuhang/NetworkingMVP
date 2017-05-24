package com.suhang.networkmvp.constants

import android.os.Environment


import com.suhang.networkmvp.R
import com.suhang.networkmvp.application.BaseApp
import com.suhang.networkmvp.utils.ResourceUtil

import java.io.File

/**
 * Created by 苏杭 on 2017/1/22 15:55.
 */

interface Constants {
    companion object{
        val INNER_ERROR = -1
        val URL = "URL"
        val APP_PATH = Environment.getExternalStorageDirectory().absolutePath + "/suhang"
        val CACHE_PATH = BaseApp.instance.cacheDir.absolutePath + File.separator + "NetCache"
        val CACHE_PATH_OKHTTP = BaseApp.instance.cacheDir.absolutePath + File.separator + "NetCache_OKHTTP"
        val BASE_URL = "http://www.huanpeng.com"
        val BASE_URL1 = "http://gank.io/api/"

        val DATABINDING_DATA = "data"
        val DATABINDING_BR = BaseApp.instance.getPackageName() + ".BR"
        /**
         * 错误类型1,不显示给用户
         */
        val ERRORTYPE_ONE = "1"
        /**
         * 错误类型2,需要显示给用户
         */
        val ERRORTYPE_TWO = "2"
        val ERRORTYPE_THREE = "3"

        val HOST = ResourceUtil.s(R.string.host)

        /*
           Activity匹配
         */
        val SPLASH = "splash"
    }
}
