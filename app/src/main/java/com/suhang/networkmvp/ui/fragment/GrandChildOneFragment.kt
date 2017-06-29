package com.suhang.networkmvp.ui.fragment

import android.os.Bundle
import com.suhang.layoutfinderannotation.GenSubComponent
import com.suhang.networkmvp.R
import com.suhang.networkmvp.annotation.BaseScope
import com.suhang.networkmvp.application.DaggerHelper
import com.suhang.networkmvp.constants.Constant
import com.suhang.networkmvp.constants.subscribeSuccess
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.dagger.module.ChildModule
import com.suhang.networkmvp.domain.AppMain
import com.suhang.networkmvp.domain.GithubBean
import com.suhang.networkmvp.domain.ZipData
import com.suhang.networkmvp.mvp.model.AttentionModel
import com.suhang.networkmvp.mvp.model.IAttentionModel
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.pager_attention_one.view.*
import org.jetbrains.anko.info

/**
 * Created by 苏杭 on 2017/6/5 11:33.
 */
@GenSubComponent(tag = Constant.BASE_FRAGMENT_DAGGER_TAG, modules = arrayOf(ChildModule::class))
class GrandChildOneFragment : BaseFragment<IAttentionModel>() {
    override fun initDagger() {
        super.initDagger()
        DaggerHelper.getInstance().getGrandChildOneFragmentComponent(this, activity)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.pager_attention_one)
    }

    override fun subscribeEvent() {
        info(manager)
        model.destroy()
        manager.subscribeSuccess().subscribe(Consumer {
            val result = it.result
            when (result) {
                is AppMain -> {
                    info(result)
                }

                is ZipData -> {
                    info(result)
                }

                is GithubBean -> {
                    info(result)
                }
            }
        })
    }

    override fun initData() {
    }

    override fun initEvent() {
        root.button.setOnClickListener {
            model.getAppMainData()
        }
        root.button1.setOnClickListener {
            model.getGithubData()
        }
    }
}