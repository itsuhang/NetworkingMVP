package com.suhang.networkmvp.ui.pager;

import android.app.Activity;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.annotation.PagerScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.PagerAttentionTwoBinding;
import com.suhang.networkmvp.function.RxBus;
import com.suhang.networkmvp.mvp.event.ClickEvent;
import com.suhang.networkmvp.mvp.translator.AttentionTranslator;
import com.suhang.networkmvp.utils.LogUtil;


/**
 * Created by 苏杭 on 2017/1/24 16:28.
 */

@PagerScope
public class AttentionTwoPager extends BasePager<AttentionTranslator> {
    @Binding(id = R.layout.pager_attention_two)
    PagerAttentionTwoBinding mBinding;
    private RxBus mRxBus1;
    private RxBus mRxBus2;

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
        mRxBus1 = new RxBus();
        mRxBus2 = new RxBus();
        mRxBus1.toFlowable(ClickEvent.class).subscribe(clickEvent -> {
            LogUtil.i("啊啊啊"+clickEvent);
        });
        mRxBus2.toFlowable(ClickEvent.class).subscribe(clickEvent -> {
            LogUtil.i("啊啊啊"+clickEvent);
        });
    }

    @Override
    protected void initEvent() {
        mBinding.b1.setOnClickListener(v -> {
            mRxBus1.post(new ClickEvent(1));
        });
        mBinding.b2.setOnClickListener(v -> {
            mRxBus2.post(new ClickEvent(2));

        });
    }
}
