package com.suhang.networkmvp.dagger.component


import com.suhang.networkmvp.application.App
import com.suhang.networkmvp.dagger.module.AppModule
import com.suhang.networkmvp.dagger.module.BaseModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by 苏杭 on 2017/1/22 17:32.
 */
@Singleton
@Component(modules = arrayOf(AppModule::class))
interface AppComponent {
    /**
     * 暴露子Component
     * @return
     */
    fun baseComponent(baseModule: BaseModule): BaseComponent

    fun inject(app: App)
}