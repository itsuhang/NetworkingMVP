package com.suhang.networkmvp.constants;

import android.os.Environment;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.application.BaseApp;
import com.suhang.networkmvp.utils.ResourceUtil;

import java.io.File;

/**
 * Created by 苏杭 on 2017/1/22 15:55.
 */

public abstract class BaseConstants {
    public static final int INNER_ERROR = -1;
    public static final String URL = "URL";
    public static final String APP_PATH = providerAppPath();
    /**
     * 错误类型1,不显示给用户
     */
    public static final String ERRORTYPE_ONE = providerErrorTypeOne();
    /**
     * 错误类型2,需要显示给用户
     */
    public static final String ERRORTYPE_TWO = providerErrorTypeTwo();

    /**
     * app sd卡文件路径
     */
    protected static String providerAppPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/suhang";
    }

    /**
     * 提供错误类型1(不显示给用户)
     * @return
     */
    protected static String providerErrorTypeOne() {
        return "1";
    }

    /**
     * 提供错误类型2(显示给用户)
     */
    protected static String providerErrorTypeTwo() {
        return "2";
    }
}

