package com.suhang.networkmvp.interfaces;

/**
 * Created by 苏杭 on 2016/11/21 14:39.
 */

public interface IAdapterHelper {
	/**
	 * 从网络获取了多少条数据
	 * @return
     */
	void setTotalCount(int count);

	/**
	 * 每一页显示多少条
	 * @return
     */
	int getMaxCount();

	/**
	 * 获得当前正在显示的条数
	 * @return
     */
	int getCurrentCount();

}
