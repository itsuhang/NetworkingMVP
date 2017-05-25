package com.suhang.networkmvp.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;


import com.suhang.networkmvp.ui.fragment.BaseFragment;

import java.util.List;


/**
 * Created by 6rooms on 2016/3/3 17:04.
 */
public class MainFragmentAdapter extends FragmentPagerAdapter {
	private List<BaseFragment> fragments;

	public MainFragmentAdapter(FragmentManager fm, List<BaseFragment> fragments) {
		super(fm);
		this.fragments = fragments;
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		return super.instantiateItem(container, position);
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
//		super.destroyItem(container, position, object);
	}

	@Override
	public BaseFragment getItem(int position) {
		return fragments.get(position);
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	public void destory() {
		if (fragments != null) {
			for (BaseFragment fragment : fragments) {
				fragment.destory();
			}
		}
	}

}
