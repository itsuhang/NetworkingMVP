package com.suhang.networkmvp.ui.pager;

import android.app.Activity;
import android.view.View;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.PagerAttentionOneBinding;
import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.domain.GithubBean;
import com.suhang.networkmvp.mvp.event.ClickEvent;
import com.suhang.networkmvp.mvp.model.AttentionModel;
import com.suhang.networkmvp.mvp.result.ErrorResult;
import com.suhang.networkmvp.mvp.result.SuccessResult;
import com.suhang.networkmvp.utils.LogUtil;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */
@PagerScope
public class AttentionOnePager extends BasePager<AttentionModel> {
    @Binding(id = R.layout.pager_attention_one)
    PagerAttentionOneBinding mBinding;

    public AttentionOnePager(Activity activity) {
        super(activity);
    }

    @Override
    protected void subscribeEvent() {
        getSM().subscribeResult(SuccessResult.class).subscribe(successResult -> {
            if (successResult.getTag() == AttentionModel.TAG_APP) {
                mBinding.tv.setText(successResult.getResult(AppMain.class).toString());
            } else {
                mBinding.tv.setText(successResult.getResult(GithubBean.class).toString());
            }
        });

        getSM().subscribeResult(ErrorResult.class).subscribe(errorResult -> {
            LogUtil.i("啊啊啊" + errorResult.getResult());
        });
        getSM().subscribeEvent(ClickEvent.class).subscribe(clickEvent -> {
            switch (clickEvent.getId()) {
                case R.id.button:
                    getModel().getAppMainData();
                    break;
                case R.id.button1:
                    getModel().getGithubData();
                    break;
            }
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
