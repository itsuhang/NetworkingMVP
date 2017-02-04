package com.suhang.networkmvp.domain;

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
	public static String ERROR_DESC_FETCH = "网络访问出错";
	//MessageDigest.getInstance("MD5")该方法参数不正确时异常
	public static String ERROR_CODE_ALGORITHM = "-8002";
	public static String ERROR_DESC_ALGORITHM = "没有此算法";
	//网络缓存读写时发生错误
	public static String ERROR_CODE_CACHEWR = "-8003";
	public static String ERROR_DESC_CACHEWR = "缓存读写错误";
	//访问网络时,通过反射获取Bean中的子URL字段出错
	public static String ERROR_CODE_GETURL = "-8004";
	public static String ERROR_DESC_GETURL = "获取子方法异常(反射)";
	//访问网络时,通过反射调用对应的方法时,没有找到该方法
	public static String ERROR_CODE_REFLECT_NETWORK = "-8005";
	public static String ERROR_DESC_REFLECT_NETWORK = "没有该方法(反射)";
	//RxBus出现异常
	public static String ERROR_CODE_RXBUS = "-8007";
	public static String ERROR_DESC_RXBUS = "RxBus出现异常";
	//上传时没有连接到服务器
	public static String ERROR_CODE_UPLOAD = "-8008";
	public static String ERROR_DESC_UPLOAD = "上传时没有连接到服务器";
	//(Dagger)组件中没有对应的注册方法
	public static String ERROR_CODE_REFLECT_DAGGER = "-8009";
	public static String ERROR_DESC_REFLECT_DAGGER = "(Dagger)组件中没有对应的注册方法（反射错误）";
	//(data bind)布局中没有定义event或data变量,或者没有创建相关event和data类
	public static String ERROR_CODE_REFLECT_BINDING = "-8010";
	public static String ERROR_DESC_REFLECT_BINDING = "(data bind)布局中没有定义event或data变量或者变量名错误,或者没有创建相关event和data类（反射错误）";
	//(Bean类)没有在该类中找到假数据
	public static String ERROR_CODE_FETCH_LOCALDATA = "-8011";
	public static String ERROR_DESC_FETCH_LOCALDATA = "(Bean类)没有在该类中找到假数据";
	//(BaseFragment/BaseActivity/BasePager)没有获取到@MView注解的View或@MPresenter的注解，或他们的参数类型错误
	public static String ERROR_CODE_REFLECT_ANNOMVP = "-8012";
	public static String ERROR_DESC_REFLECT_ANNOMVP   = "(BaseFragment/BaseActivity/BasePager)没有获取到@MView注解的View，或参数类型错误(反射错误)";
	//(MVP)Presenter中的构造方法参数只能有一个，为IBaseView的子类
	public static String ERROR_CODE_REFLECT_MVP = "-8013";
	public static String ERROR_DESC_REFLECT_MVP   = "(MVP)Presenter中的构造方法参数只能有一个，为IBaseView的子类(反射错误)";
	//(Socket错误)
	public static String ERROR_CODE_SOCKET = "-8014";
	public static String ERROR_DESC_SOCKET   = "Socket错误:";
	//网络访问错误，bean类中的URL和METHOD属性不正确
	public static String ERROR_CODE_NETWORK_PARAMS = "-8015";
	public static String ERROR_DESC_NETWORK_PARAMS  = "网络访问错误，bean类中的URL和METHOD属性不正确:";
	//网络访问错误，Service中不到与METHOD属性对应的方法
	public static String ERROR_CODE_NETWORK_METHOD = "-8016";
	public static String ERROR_DESC_NETWORK_METHOD = "网络访问错误，Service中不到与METHOD属性对应的方法:";
	//(BaseAdapter)，得到的Presenter与泛型类型不对应（强制类型转换错误）
	public static String ERROR_CODE_ADAPTER_CAST = "-8017";
	public static String ERROR_DESC_ADAPTER_CAST = "(BaseAdapter)，得到的Presenter与泛型类型不对应:";
	//(CanRefreshPresenter)，子类没有设置HuanpengRefreshLayout或Adapter
	public static String ERROR_CODE_REFRESH_LAYOUT = "-8018";
	public static String ERROR_DESC_REFRESH_LAYOUT = "(CanRefreshPresenter)，子类没有设置HuanpengRefreshLayout或Adapter";
	//(下拉加载)，没有更多了
	public static String ERROR_CODE_REFRESH_NOTLOADMORE = "-8019";
	public static String ERROR_DESC_REFRESH_NOTLOADMORE = "没有更多了";
	//(Socket)，Socket（发送）IO异常
	public static String ERROR_CODE_SOCKET_SEND = "-8020";
	public static String ERROR_DESC_SOCKET_SEND = "Socket（发送）IO异常";
	//(Network_Download)，下载过程出现IO错误
	public static String ERROR_CODE_DOWNLOAD = "-8021";
	public static String ERROR_DESC_DOWNLOAD = "下载过程出现IO错误";
	//(Network_Download)，创建文件失败
	public static String ERROR_CODE_DOWNLOAD_FILE = "-8022";
	public static String ERROR_DESC_DOWNLOAD_FILE = "创建文件失败";
}
