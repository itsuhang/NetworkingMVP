package com.suhang.networkmvp.ui.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.alibaba.android.arouter.launcher.ARouter;
import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.MainFragmentAdapter;
import com.suhang.networkmvp.annotation.ActivityScope;
import com.suhang.networkmvp.dagger.module.MainModule;
import com.suhang.networkmvp.databinding.ActivityMainBinding;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.contract.IMainContract;
import com.suhang.networkmvp.mvp.presenter.MainPresenter;
import com.suhang.networkmvp.ui.fragment.AttentionFragment;
import com.suhang.networkmvp.ui.fragment.BaseFragment;
import com.suhang.networkmvp.ui.fragment.HomeFragment;
import com.suhang.networkmvp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

@ActivityScope
public class MainActivity extends BaseActivity<MainPresenter, ActivityMainBinding> implements IMainContract.IMainView {
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_main);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = getIntent().getData();
        LogUtil.i("啊啊啊"+uri);
        ARouter.getInstance().build(uri).navigation();
    }

    @Override
    protected void initData() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AttentionFragment());
        getBinding().vpMain.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(),fragments));
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getMainComponent(new MainModule(this)).inject(this);
    }

    @Override
    public void log() {
    }

    @Override
    public void setData(ErrorBean e, int tag) {
    }

    @Override
    public void progress(int precent, int tag) {

    }

    @Override
    public void showError(ErrorBean e, int tag) {
        LogUtil.i("啊啊啊"+e.getCode()+"   "+e.getDesc());
    }

    @Override
    public void showLoading(int tag) {

    }

    @Override
    public void hideLoading(int tag) {

    }
}
