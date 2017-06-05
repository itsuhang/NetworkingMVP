package com.suhang.networkmvp.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.suhang.networkmvp.ui.BasicFragment


import com.suhang.networkmvp.ui.fragment.BaseFragment


/**
 * Created by 6rooms on 2016/3/3 17:04.
 */
class MainFragmentAdapter(fm: FragmentManager, private val fragments: List<Fragment>) : FragmentPagerAdapter(fm) {

    override fun destroyItem(container: ViewGroup?, position: Int, `object`: Any) {
        //		super.destroyItem(container, position, object);
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    fun destroy() {
        fragments
                .filterIsInstance<BaseFragment<*>>()
                .forEach { it.destroy() }
    }

}
