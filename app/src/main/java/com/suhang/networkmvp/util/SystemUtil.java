package com.suhang.networkmvp.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;

import com.suhang.networkmvp.MyApplication;

/**
 * Created by sh on 2016/10/26 15:44.
 */

public class SystemUtil {
	public static int getAppVersion() {
		try {
			PackageInfo info = MyApplication.getInstance().getPackageManager().getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
			return info.versionCode;
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return 1;
	}

	/**
	 * 检测网络是否连接
	 *
	 * @return
	 */
	public boolean isNetworkAvailable() {
		// 得到网络连接信息
		ConnectivityManager manager = (ConnectivityManager) MyApplication.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
		// 去进行判断网络是否连接
		if (manager.getActiveNetworkInfo() != null) {
			return manager.getActiveNetworkInfo().isAvailable();
		}
		return false;
	}
}
