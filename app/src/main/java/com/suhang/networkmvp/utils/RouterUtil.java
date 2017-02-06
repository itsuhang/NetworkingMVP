package com.suhang.networkmvp.utils;

import com.suhang.networkmvp.constants.Constants;

/**
 * Created by 苏杭 on 2017/2/6 17:35.
 */

public class RouterUtil {
    public static String format(String name) {
        return Constants.HOST + "://" + name;
    }

    public static String formatWithParam(String name, String paramName, String param) {
        return Constants.HOST + "://" + name + "?" + paramName + "=" + param;
    }

    public static String formatWithParams(String name, String[] paramNames, String[] params) {
        StringBuilder sb = new StringBuilder();
        sb.append(Constants.HOST).append("://").append(name).append("?");
        for (int i = 0; i < paramNames.length; i++) {
            sb.append(paramNames[i]).append("=").append(params[i]).append("&");
        }
        return sb.toString();
    }
}
