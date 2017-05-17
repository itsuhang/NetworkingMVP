package com.suhang.networkmvp.ui.activity;

import android.os.Bundle;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.ActivityScope;
import com.suhang.networkmvp.binding.data.BaseData;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.ActivitySplashBinding;
import com.suhang.networkmvp.mvp.model.BlankModel;

@ActivityScope
public class SplashActivity extends BaseActivity<BlankModel,ActivitySplashBinding>{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_splash;
    }

    @Override
    protected void subscribeEvent() {

    }

    @Override
    protected void initData() {
    }

    @Override
    protected BaseData getBindingData() {
        return null;
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getBlankComponent(new BlankModule()).inject(this);
    }
}
