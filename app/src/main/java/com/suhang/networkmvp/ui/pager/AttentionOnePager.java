package com.suhang.networkmvp.ui.pager;

import android.app.Activity;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.PagerAttentionOneBinding;
import com.suhang.networkmvp.domain.AppMain;
import com.suhang.networkmvp.event.SuccessResult;
import com.suhang.networkmvp.interfaces.INetworkService;
import com.suhang.networkmvp.mvp.model.NetworkModel;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

@PagerScope
public class AttentionOnePager extends BasePager<PagerAttentionOneBinding> {
    @Inject
    NetworkModel<INetworkService> mModel2;

    public AttentionOnePager(Activity activity) {
        super(activity);
        bind(R.layout.pager_attention_one);
        LogUtil.i("啊啊啊"+mDisposables);
    }

    @Override
    protected void subscribeEvent() {
        subscribe(SuccessResult.class).subscribe(successResult -> {
            LogUtil.i("啊啊啊"+successResult.getResult(AppMain.class));
        });
    }


    @Override
    protected void initEvent() {
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getBlankComponent(new BlankModule()).inject(this);
    }

    @Override
    public void initData() {
//        mModel2.loadPostDataWrap(AppMain.class, new ArrayMap<>(), false, 100);
    }

}
