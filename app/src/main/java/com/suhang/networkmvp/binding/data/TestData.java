package com.suhang.networkmvp.binding.data;

import com.suhang.networkmvp.adapter.HomeRvAdapter;
import com.suhang.networkmvp.binding.event.BaseData;

/**
 * Created by 苏杭 on 2017/5/16 21:24.
 */

public class TestData extends BaseData{
	private HomeRvAdapter.MyViewHolder mHolder;

	public HomeRvAdapter.MyViewHolder getHolder() {
		return mHolder;
	}

	public void setHolder(HomeRvAdapter.MyViewHolder holder) {
		mHolder = holder;
	}
}
