package com.suhang.networkmvp.constants;

import com.suhang.networkmvp.MyApplication;

import java.io.File;

/**
 * Created by sh on 2016/10/31 17:52.
 */

public class Constant {
	public static String CACHE_PATH = MyApplication.getInstance().getCacheDir().getAbsolutePath() + File.separator + "NetCache";
}
