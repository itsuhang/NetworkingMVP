package com.suhang.networkmvp.constants;

import android.os.Environment;


import com.suhang.networkmvp.R;
import com.suhang.networkmvp.application.App;
import com.suhang.networkmvp.utils.ResourceUtil;

import java.io.File;

/**
 * Created by 苏杭 on 2017/1/22 15:55.
 */

public class Constants {
    public static final String URL = "URL";
    public static final String APP_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/suhang";
    public static final String CACHE_PATH = App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "NetCache";
    public static final String CACHE_PATH_OKHTTP= App.getInstance().getCacheDir().getAbsolutePath() + File.separator + "NetCache_OKHTTP";
    public static final String BASE_URL = "http://gank.io/api/";
    /**
     * 错误类型1,不显示给用户
     */
    public static final String ERRORTYPE_ONE = "1";
    /**
     * 错误类型2,需要显示给用户
     */
    public static final String ERRORTYPE_TWO = "2";
    public static final String ERRORTYPE_THREE = "3";

    public static final String HOST = ResourceUtil.s(R.string.host);

    /*
       Activity匹配
     */
    public static final String SPLASH = "splash";
}
