package com.suhang.networkmvp.event;

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
    //属性ViewDatabinding的类型与布局不符
    public static String ERROR_CODE_DATABINDING_FIELD = "-8006";
    public static String ERROR_DESC_DATABINDING_FIELD = "(databinding)属性ViewDatabinding的类型与布局不符";
    //没有找到被@Binding注解的属性ViewDatabinding
    public static String ERROR_CODE_DATABINDING_NOFIELD = "-8007";
    public static String ERROR_DESC_DATABINDING_NOFIELD = "(databinding)没有找到被@Binding注解的属性ViewDatabinding";

    //事件订阅内部发送错误
    public static String ERROR_CODE_SUBSCRIBE_INNER = "-8008";
    public static String ERROR_DESC_SUBSCRIBE_INNER = "事件订阅内部发送错误";
    //BaseModel内部事件订阅发生错误
    public static String ERROR_CODE_SUBSCRIBE_INNER_MODEL = "-8009";
    public static String ERROR_DESC_SUBSCRIBE_INNER_MODEL = "BaseModel内部事件订阅发生错误";
    //(data bind)布局中没有定义event或data变量,或者没有创建相关event和data类
    public static String ERROR_CODE_REFLECT_BINDING = "-8010";
    public static String ERROR_DESC_REFLECT_BINDING = "(databinding)布局中没有定义event或data变量或者变量名错误,或者没有创建相关event和data类（反射错误）";
    //网络访问错误，bean类中的URL和METHOD属性不正确
    public static String ERROR_CODE_NETWORK_PARAMS = "-8015";
    public static String ERROR_DESC_NETWORK_PARAMS = "网络访问错误，bean类中的URL和METHOD属性不正确:";
    //网络访问错误，Service中不到与METHOD属性对应的方法
    public static String ERROR_CODE_NETWORK_METHOD = "-8016";
    public static String ERROR_DESC_NETWORK_METHOD = "网络访问错误，Service中不到与METHOD属性对应的方法:";
    //(Network_Download)，下载过程出现IO错误
    public static String ERROR_CODE_DOWNLOAD = "-8021";
    public static String ERROR_DESC_DOWNLOAD = "下载过程出现IO错误";
    //(Network_Download)，创建文件失败
    public static String ERROR_CODE_DOWNLOAD_FILE = "-8022";
    public static String ERROR_DESC_DOWNLOAD_FILE = "创建文件失败";
}
