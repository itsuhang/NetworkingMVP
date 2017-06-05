package com.suhang.networkmvp.ui.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import com.suhang.networkmvp.function.rx.SubstribeManager
import com.suhang.networkmvp.mvp.model.IBaseModel
import com.suhang.networkmvp.ui.BasicActivity
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasFragmentInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/1/21 10:52.
 */
abstract class BaseActivity<T : IBaseModel> : BasicActivity(), HasFragmentInjector, HasSupportFragmentInjector {
    @Inject
    lateinit var supportFragmentInjector: DispatchingAndroidInjector<Fragment>
    @Inject
    lateinit var frameworkFragmentInjector: DispatchingAndroidInjector<android.app.Fragment>

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    lateinit var disposables: CompositeDisposable

    @Inject
    lateinit var activity: Activity

    @Inject
    lateinit var context: Context

    @Inject
    lateinit var model: T

    /**
     * 获取RxBus,可进行订阅操作
     */
    @Inject
    lateinit var manager: SubstribeManager

    public override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return supportFragmentInjector
    }

    override fun fragmentInjector(): AndroidInjector<android.app.Fragment> {
        return frameworkFragmentInjector
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.dispose()
        model.destroy()
    }
}
