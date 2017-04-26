package com.suhang.networkmvp.mvp.base;


import com.suhang.networkmvp.event.ErrorResult;
import com.suhang.networkmvp.event.LoadingResult;
import com.suhang.networkmvp.event.SuccessResult;
import com.suhang.networkmvp.mvp.IView;

/**
 * Created by 苏杭 on 2017/1/24 17:03.
 * 不需要任何基本功能的View层(不需要网络访问等)
 */

public interface IBlankView extends IView,SuccessResult.SuccessCallback,ErrorResult.ErrorCallback,LoadingResult.LoadingCallback{

}
