package com.suhang.networkmvp.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;


import com.suhang.networkmvp.ui.pager.BasePager;

import java.util.List;


/**
 * Created by 苏杭 on 2016/11/11 17:24.
 */

public class AttentionPagerAdapter extends PagerAdapter {
	private List<BasePager> mPagers;

	public AttentionPagerAdapter(List<BasePager> pagers) {
		this.mPagers = pagers;
	}

	@Override
	public int getCount() {
		return mPagers.size();
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		BasePager basePager = mPagers.get(position);
		container.addView(basePager.getRootView());
		basePager.initData();
		return basePager.getRootView();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		mPagers.get(position).destory();
		container.removeView((View) object);
	}

	@Override
	public boolean isViewFromObject(View view, Object object) {
		return view ==object;
	}

	public void destory() {
		if (mPagers != null) {
			for (BasePager pager : mPagers) {
				if (pager != null) {
					pager.destory();
				}
			}
		}
	}
}
