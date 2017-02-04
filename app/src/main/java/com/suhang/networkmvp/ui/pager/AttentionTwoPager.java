package com.suhang.networkmvp.ui.pager;

import android.app.Activity;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.databinding.PagerAttentionTwoBinding;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.base.BlankPresent;
import com.suhang.networkmvp.mvp.base.IBlankView;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

public class AttentionTwoPager extends BasePager<BlankPresent,PagerAttentionTwoBinding> implements IBlankView {
    public AttentionTwoPager(Activity activity) {
        super(activity);
        bind(R.layout.pager_attention_two);
    }

    @Override
    protected void injectDagger() {

    }

    @Override
    public void initData() {

    }

    @Override
    public void showError(ErrorBean e, int tag) {

    }

    @Override
    public void showLoading(int tag) {

    }

    @Override
    public void hideLoading(int tag) {

    }
}
