package com.suhang.networkmvp.ui.pager;

import android.app.Activity;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.PagerAttentionTwoBinding;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

@PagerScope
public class AttentionTwoPager extends BasePager {
    @Binding(id = R.layout.pager_attention_two)
    PagerAttentionTwoBinding mBinding;

    public AttentionTwoPager(Activity activity) {
        super(activity);
    }

    @Override
    protected void subscribeEvent() {
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getBlankComponent(new BlankModule()).inject(this);
    }

    @Override
    public void initData() {
    }

}
