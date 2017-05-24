package com.suhang.networkmvp.utils;


import com.suhang.networkmvp.application.BaseApp;

/**
 * 长度单位dp,px互转工具
 * Created by sh
 */
public class DensityUtil {  
	  
    /** 
     * 根据手机的分辨率从 dip 的单位 转成为 px(像素) 
     */  
    public static int dip2px(float dpValue) {
        final float scale = BaseApp.instance.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);  
    }

    public static float dip(float dpValue) {
        final float scale = BaseApp.instance.getResources().getDisplayMetrics().density;
        return dpValue * scale + 0.5f;
    }
  
    /** 
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
     */  
    public static int px2dip(float pxValue) {
        final float scale = BaseApp.instance.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);  
    }  
}  