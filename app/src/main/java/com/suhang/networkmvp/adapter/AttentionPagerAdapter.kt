package com.suhang.networkmvp.adapter


import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.view.ViewGroup
import com.suhang.networkmvp.ui.fragment.BaseFragment


/**
 * Created by 苏杭 on 2016/11/11 17:24.
 */

class AttentionPagerAdapter(fm: FragmentManager, private val fragments: List<Fragment>) : FragmentPagerAdapter(fm) {
    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount(): Int {
        return fragments.size
    }

    override fun destroyItem(container: ViewGroup, position: Int, o: Any) {

    }

    fun destroy() {
        fragments
                .filterIsInstance<BaseFragment<*>>()
                .forEach { it.destroy() }
    }
}
