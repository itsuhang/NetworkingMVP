package com.suhang.networkmvp.constants

/**
 * Created by sh on 2016/10/28 14:58.
 */

interface ErrorCode {
    companion object{
        /**
         * 错误码
         */
        //网络访问异常
        var ERROR_CODE_NETWORK = "-8000"
        var ERROR_DESC_NETWORK = "网络访问出错"
        //抓取bean类数据异常,传入的Bean.class在INetWorkService中没有对应的获取数据方法
        var ERROR_CODE_FETCH = "-8001"
        var ERROR_DESC_FETCH = "网络访问出错"
        //MessageDigest.getInstance("MD5")该方法参数不正确时异常
        var ERROR_CODE_ALGORITHM = "-8002"
        var ERROR_DESC_ALGORITHM = "没有此算法"
        //网络缓存读写时发生错误
        var ERROR_CODE_CACHEWR = "-8003"
        var ERROR_DESC_CACHEWR = "缓存读写错误"
        //访问网络时,通过反射获取Bean中的子URL字段出错
        var ERROR_CODE_GETURL = "-8004"
        var ERROR_DESC_GETURL = "获取子方法异常(反射)"
        //访问网络时,通过反射调用对应的方法时,没有找到该方法
        var ERROR_CODE_REFLECT_NETWORK = "-8005"
        var ERROR_DESC_REFLECT_NETWORK = "没有该方法(反射)"
        //属性ViewDatabinding的类型与布局不符
        var ERROR_CODE_DATABINDING_FIELD = "-8006"
        var ERROR_DESC_DATABINDING_FIELD = "(databinding)属性ViewDatabinding的类型与布局不符"
        //没有找到被@Binding注解的属性ViewDatabinding
        var ERROR_CODE_DATABINDING_NOFIELD = "-8007"
        var ERROR_DESC_DATABINDING_NOFIELD = "(databinding)没有找到被@Binding注解的属性ViewDatabinding"

        //事件订阅内部发送错误
        var ERROR_CODE_SUBSCRIBE_INNER = "-8008"
        var ERROR_DESC_SUBSCRIBE_INNER = "事件订阅内部发送错误"
        //BaseModel内部事件订阅发生错误
        var ERROR_CODE_SUBSCRIBE_INNER_MODEL = "-8009"
        var ERROR_DESC_SUBSCRIBE_INNER_MODEL = "BaseModel内部事件订阅发生错误"
        //(data bind)布局中没有定义event或data变量,或者没有创建相关event和data类
        var ERROR_CODE_REFLECT_BINDING = "-8010"
        var ERROR_DESC_REFLECT_BINDING = "(databinding)布局中没有定义event或data变量或者变量名错误,或者没有创建相关event和data类（反射错误）"
        //BaseRvAdapter中onBindViewHolder()出现异常
        var ERROR_CODE_RVADAPTER_BIND = "-8011"
        var ERROR_DESC_RVADAPTER_BIND = "BaseRvAdapter中onBindViewHolder()出现异常(数据出错,一般情况可忽略该错误)"
        //NetworkModel:根据参数查找Retrofit的Service中的方法时发生异常
        var ERROR_CODE_NETWORK_FINDMETHOD = "-8012"
        var ERROR_DESC_NETWORK_FINDMETHOD = "NetworkModel:根据参数查找Retrofit的Service中的方法时发生异常"
        //BaseApp:初始化Retrofit出错
        var ERROR_CODE_BASEAPP_RETROFIT = "-8013"
        var ERROR_DESC_BASEAPP_RETROFIT = "BaseApp:初始化Retrofit出错"
        //网络访问错误，bean类中的URL和METHOD属性不正确
        var ERROR_CODE_NETWORK_PARAMS = "-8015"
        var ERROR_DESC_NETWORK_PARAMS = "网络访问错误，bean类中的URL和METHOD属性不正确:"
        //网络访问错误，Service中不到与METHOD属性对应的方法
        var ERROR_CODE_NETWORK_METHOD = "-8016"
        var ERROR_DESC_NETWORK_METHOD = "网络访问错误，Service中不到与METHOD属性对应的方法:"
        //(Network_Download)，下载过程出现IO错误
        var ERROR_CODE_DOWNLOAD = "-8021"
        var ERROR_DESC_DOWNLOAD = "下载过程出现IO错误"
        //(Network_Download)，创建文件失败
        var ERROR_CODE_DOWNLOAD_FILE = "-8022"
        var ERROR_DESC_DOWNLOAD_FILE = "创建文件失败"
    }
}
