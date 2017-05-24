package com.suhang.networkmvp.ui.activity

import android.os.Bundle
import android.util.ArrayMap
import com.suhang.layoutfinder.MethodFinder
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.MainFragmentAdapter
import com.suhang.networkmvp.annotation.ActivityScope
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.databinding.ActivityMainBinding
import com.suhang.networkmvp.domain.AppMain
import com.suhang.networkmvp.mvp.model.BlankModel
import com.suhang.networkmvp.ui.fragment.AttentionFragment
import com.suhang.networkmvp.ui.fragment.BaseFragment
import com.suhang.networkmvp.ui.fragment.HomeFragment
import com.suhang.networkmvp.utils.RxUtil
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import org.jetbrains.anko.info
import java.util.*

@ActivityScope
class MainActivity : BaseActivity<BlankModel, ActivityMainBinding>() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun bindLayout(): Int {
        return R.layout.activity_main
    }

    override fun subscribeEvent() {

    }

    override fun initData() {
        val fragments = ArrayList<BaseFragment<*, *>>()
        fragments.add(HomeFragment())
        fragments.add(AttentionFragment())
        val fl :Flowable<Any>  = MethodFinder.find(AppMain.URL, ArrayMap<Any, Any>())
        fl.compose(RxUtil.handleResultNone()).compose(RxUtil.fixScheduler()).subscribe({t->info(t.toString())})
        mBinding.vpMain.adapter = MainFragmentAdapter(supportFragmentManager, fragments)
    }


    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }

    companion object {
        private val TAG = "MainActivity"
    }

}
