package com.suhang.networkmvp.interfaces;

/**
 * Created by 苏杭 on 2016/11/21 14:39.
 */

public interface IAdapterHelper {
	/**
	 * 设置从网络获取了多少条数据
	 * @return
	 */
	int setNetItemCount();


	/**
	 * 设置每一页显示多少条
	 * @return
	 */
	int setMaxCount();

	/**
	 * 从网络获取了多少条数据
	 * @return
     */
	int getNetItemCount();

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
