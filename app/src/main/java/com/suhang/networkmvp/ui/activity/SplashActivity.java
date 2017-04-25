package com.suhang.networkmvp.ui.activity;

import android.os.Bundle;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.ActivityScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.ActivitySplashBinding;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.base.BlankPresent;
import com.suhang.networkmvp.mvp.base.IBlankView;
import com.suhang.networkmvp.utils.LogUtil;

@ActivityScope
public class SplashActivity extends BaseActivity<BlankPresent, ActivitySplashBinding> implements IBlankView {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_splash);
        // ARouter会自动对字段进行赋值，无需主动获取
//        LogUtil.i("啊啊啊",name + age + boy);
    }

    @Override
    protected void initData() {
        getBinding().getData().da.set(getIntent().getStringExtra("name")+"   "+getIntent().getStringExtra("age"));
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getBlankComponent(new BlankModule(this)).inject(this);
    }

    @Override
    public void showError(ErrorBean e, int tag) {
        LogUtil.i("啊啊啊" + e.getDesc());
    }

    @Override
    public void showLoading(int tag) {

    }

    @Override
    public void hideLoading(int tag) {

    }
}
