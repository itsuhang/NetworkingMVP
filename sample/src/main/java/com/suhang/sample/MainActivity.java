package com.suhang.sample;

import android.os.Bundle;

import com.suhang.networkmvp.binding.data.BaseData;
import com.suhang.networkmvp.mvp.model.BlankModel;
import com.suhang.networkmvp.ui.activity.BaseActivity;
import com.suhang.networkmvp.utils.LogUtil;
import com.suhang.sample.databinding.ActivityMainBinding;

import javax.inject.Inject;
import javax.inject.Named;

import okhttp3.OkHttpClient;

public class MainActivity extends BaseActivity<BlankModel,ActivityMainBinding> {
    @Inject
    OkHttpClient mOkHttpClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.i("啊啊啊"+mOkHttpClient);

    }

    @Override
    protected int bindLayout() {
        return R.layout.activity_main;
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
        getBaseComponent().providerBC(new com.suhang.networkmvp.dagger.module.BlankModule()).inject(this);
    }
}
