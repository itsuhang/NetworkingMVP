package com.suhang.networkmvp.adapter

import android.support.v4.view.PagerAdapter
import android.view.View
import android.view.ViewGroup


import com.suhang.networkmvp.ui.pager.BasePager


/**
 * Created by 苏杭 on 2016/11/11 17:24.
 */

class AttentionPagerAdapter(private val mPagers: List<BasePager<*>>) : PagerAdapter() {

    override fun getCount(): Int {
        return mPagers.size
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val basePager = mPagers[position]
        container.addView(basePager.root)
        basePager.initData()
        return basePager.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {
        mPagers[position].destroy()
        container.removeView(o as View)
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    /**
     * 回收资源
     */
    fun destroy() {
        for (pager in mPagers) {
            pager.destroy()
        }
    }
}
