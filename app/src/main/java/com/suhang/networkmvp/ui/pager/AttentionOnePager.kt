package com.suhang.networkmvp.ui.pager

import android.app.Activity
import com.suhang.networkmvp.R
import com.suhang.networkmvp.annotation.PagerScope
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.domain.AppMain
import com.suhang.networkmvp.function.FlowableWrap
import com.suhang.networkmvp.mvp.model.AttentionModel
import com.suhang.networkmvp.mvp.result.SuccessResult
import com.suhang.networkmvp.utils.LogUtil
import kotlinx.android.synthetic.main.pager_attention_one.view.*
import org.jetbrains.anko.info

/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */
@PagerScope
class AttentionOnePager(activity: Activity) : BasePager<AttentionModel>(activity) {
    override fun bindLayout(): Int {
        return R.layout.pager_attention_one
    }

    override fun subscribeEvent() {
        sm.subscribeResult(SuccessResult::class.java).subscribe(object : FlowableWrap.Next<SuccessResult> {
            override fun onNext(t: SuccessResult) {
                val result = t.getResult(AppMain::class.java)
                LogUtil.i("啊啊啊" + result)
            }
        })
        //        getSM().subscribeResult(SuccessResult.class).subscribe(successResult -> {
        //            if (successResult.getTag() == AttentionModel.Companion.getTAG_APP()) {
        //                mBinding.tv.setText(successResult.getResult(AppMain.class).toString());
        //            } else {
        //                mBinding.tv.setText(successResult.getResult(GithubBean.class).toString());
        //            }
        //        });
        //
        //        getSM().subscribeResult(ErrorResult.class).subscribe(errorResult -> {
        //            LogUtil.i("啊啊啊" + errorResult.getResult());
        //        });
        //        getSM().subscribeEvent(BindingEvent.class).subscribe(bindingEvent -> {
        //            switch (bindingEvent.getId()) {
        //                case R.id.button:
        //                    getModel().getAppMainData();
        //                    break;
        //                case R.id.button1:
        //                    getModel().getGithubData();
        //                    break;
        //            }
        //        });
    }

    override fun initEvent() {
        root.button.setOnClickListener {
            info("额鹅鹅鹅")
            model.getAppMainData()
        }
    }


    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }

    override fun initData() {
    }

}
