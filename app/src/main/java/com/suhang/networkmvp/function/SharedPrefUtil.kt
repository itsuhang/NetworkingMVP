package com.suhang.networkmvp.function

import android.content.Context
import android.content.SharedPreferences

import com.suhang.networkmvp.application.BaseApp


/**
 * 本地存储SharedPreferences工具
 * Created by sh
 */
object SharedPrefUtil {
    lateinit var mSp: SharedPreferences
    private var name = "config"

    private fun getSharedPref(context: Context): SharedPreferences? {
        mSp = context.getSharedPreferences(name, Context.MODE_PRIVATE)
        return mSp
    }

    /**
     * 设置SharePrefrences文件名字

     * @param name
     */
    fun setName(name: String) {
        SharedPrefUtil.name = name
    }


    //下面方法都是对不同数据类型进行保存,获取

    fun putBoolean(key: String, value: Boolean) {
        getSharedPref(BaseApp.instance)?.edit()?.putBoolean(key, value)?.apply()
    }

    fun getBoolean(key: String, defValue: Boolean): Boolean? {
        return getSharedPref(BaseApp.instance)?.getBoolean(key, defValue)
    }

    fun putString(key: String, value: String) {
        getSharedPref(BaseApp.instance)?.edit()?.putString(key, value)?.apply()
    }

    fun getString(key: String, defValue: String): String? {
        return getSharedPref(BaseApp.instance)?.getString(key, defValue)
    }

    fun putInt(key: String, value: Int) {
        getSharedPref(BaseApp.instance)?.edit()?.putInt(key, value)?.apply()
    }

    fun getInt(key: String, defValue: Int): Int? {
        return getSharedPref(BaseApp.instance)?.getInt(key, defValue)
    }

    fun putFloat(key: String, value: Float) {
        getSharedPref(BaseApp.instance)?.edit()?.putFloat(key, value)?.apply()
    }

    fun getFloat(key: String, defValue: Float): Float? {
        return getSharedPref(BaseApp.instance)?.getFloat(key, defValue)
    }

    fun putLong(key: String, value: Long) {
        getSharedPref(BaseApp.instance)?.edit()?.putLong(key, value)?.apply()
    }

    fun getLong(key: String, defValue: Long): Long? {
        return getSharedPref(BaseApp.instance)?.getLong(key, defValue)
    }


    /**
     * 删除某个键值对

     * @param context
     * *
     * @param key
     */
    fun remove(context: Context, key: String) {
        getSharedPref(context)?.edit()?.remove(key)?.apply()
    }

}