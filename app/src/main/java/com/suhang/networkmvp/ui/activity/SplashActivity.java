package com.suhang.networkmvp.ui.activity;

import android.os.Bundle;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.ActivityScope;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.ActivitySplashBinding;

@ActivityScope
public class SplashActivity extends BaseActivity{
    @Binding(id = R.layout.activity_main)
    ActivitySplashBinding mBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void subscribeEvent() {

    }

    @Override
    protected void initData() {
        mBinding.getData().da.set(getIntent().getStringExtra("name")+"   "+getIntent().getStringExtra("age"));
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getBlankComponent(new BlankModule()).inject(this);
    }

}
