package com.suhang.networkmvp.adapter

import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup


import com.suhang.networkmvp.ui.fragment.BaseFragment


/**
 * Created by 6rooms on 2016/3/3 17:04.
 */
class MainFragmentAdapter(fm: FragmentManager, private val fragments: List<BaseFragment<*>>) : FragmentPagerAdapter(fm) {

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        return super.instantiateItem(container, position)
    }

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        //		super.destroyItem(container, position, object);
    }

    override fun getItem(position: Int): BaseFragment<*> {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun destroy() {
        for (fragment in fragments) {
            fragment.destroy()
        }
    }

}
