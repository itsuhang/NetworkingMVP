package com.suhang.networkmvp.ui.pager;

import android.app.Activity;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.PagerAttentionOneBinding;
import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.GithubBean;
import com.suhang.networkmvp.event.result.ErrorResult;
import com.suhang.networkmvp.event.result.SuccessResult;
import com.suhang.networkmvp.mvp.model.AttentionModel;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */
@PagerScope
public class AttentionOnePager extends BasePager {
    @Binding(id = R.layout.pager_attention_one)
    PagerAttentionOneBinding mBinding;
    @Inject
    AttentionModel mModel;

    public AttentionOnePager(Activity activity) {
        super(activity);
    }

    @Override
    protected void subscribeEvent() {
        getSm().subscribe(SuccessResult.class).subscribe(successResult -> {
            if (successResult.getTag() == AttentionModel.TAG_APP) {
                mBinding.tv.setText(successResult.getResult(AppMain.class).toString());
            } else {
                mBinding.tv.setText(successResult.getResult(GithubBean.class).toString());
            }
        });

        getSm().subscribe(ErrorResult.class).subscribe(errorResult -> {
            LogUtil.i("啊啊啊" + errorResult.getResult());
        });
    }


    @Override
    protected void initEvent() {
        mBinding.button1.setOnClickListener(v -> {
            mModel.getGithubData();
        });
        mBinding.button.setOnClickListener(v -> {
            mModel.getAppMainData();
        });
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getBlankComponent(new BlankModule()).inject(this);
    }

    @Override
    public void initData() {
    }

}
