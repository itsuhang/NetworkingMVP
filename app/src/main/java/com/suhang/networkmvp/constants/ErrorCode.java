package com.suhang.networkmvp.constants;

/**
 * Created by sh on 2016/10/28 14:58.
 */

public class ErrorCode {
	/**
	 * 错误码
	 */
	//网络访问异常
	public static String ERROR_CODE_NETWORK = "-8000";
	public static String ERROR_DESC_NETWORK = "网络访问出错";
	//抓取bean类数据异常,传入的Bean.class在INetWorkService中没有对应的获取数据方法
	public static String ERROR_CODE_FETCH = "-8001";
	public static String ERROR_DESC_FETCH = "没有抓取到该Bean类信息";
	//MessageDigest.getInstance("MD5")该方法参数不正确时异常
	public static String ERROR_CODE_ALGORITHM = "-8002";
	public static String ERROR_DESC_ALGORITHM = "没有此算法";
	//网络缓存读写时发生错误
	public static String ERROR_CODE_CACHEWR = "-8003";
	public static String ERROR_DESC_CACHEWR = "缓存读写错误";
	//访问网络时,通过反射获取Bean中的子URL字段出错
	public static String ERROR_CODE_GETURL = "-8004";
	public static String ERROR_DESC_GETURL = "获取子URL异常(反射)";
	//下载过程中出现IO异常
	public static String ERROR_CODE_DOWNLOAD_IO = "-8005";
	public static String ERROR_DESC_DOWNLOAD_IO = "下载IO异常";
}
