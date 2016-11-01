package com.suhang.networkmvp.view;


import com.suhang.networkmvp.bean.ErrorBean;

/**
 * Created by sh on 2016/10/25 17:43.
 */

public interface INetworkView {
	/**
	 * 获取数据时可用于显示加载中进度条
	 */
	void showLoading();

	/**
	 * 获取数据成功或者失败后隐藏显示出的进度条
	 */
	void hideLoading();

	/**
	 * 获取数据失败后用于进行错误提示
	 * @param errorBean
	 * @param tag
	 */
	void showError(ErrorBean errorBean, int tag);

	/**
	 * 在将得到的数据传递给View层,在View层拿着数据更新页面
	 * 注意:因主布局中ViewPager,有预加载,如果页面切换过快,当后台开启线程获取数据后,在View层更新UI时该页面已经被销毁,
	 * 就会出现空指针异常,所以在设置数据之前要先判断页面是否被销毁,或者判断控件是否为空
	 * @param tag
	 */
	void setData(Object o,int tag);

	void progress(int precent, int tag);
}
