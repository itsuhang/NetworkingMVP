package com.suhang.networkmvp.ui.activity;

import android.Manifest;
import android.os.Bundle;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.MainFragmentAdapter;
import com.suhang.networkmvp.annotation.ActivityScope;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.ActivityMainBinding;
import com.suhang.networkmvp.mvp.translator.BlankTranslator;
import com.suhang.networkmvp.ui.fragment.AttentionFragment;
import com.suhang.networkmvp.ui.fragment.BaseFragment;
import com.suhang.networkmvp.ui.fragment.HomeFragment;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

@ActivityScope
public class MainActivity extends BaseActivity<BlankTranslator>{
    @Binding(id = R.layout.activity_main)
    ActivityMainBinding mBinding;
    private static final String TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void subscribeEvent() {

    }

    @Override
    protected void initData() {
        List<BaseFragment> fragments = new ArrayList<>();
        fragments.add(new HomeFragment());
        fragments.add(new AttentionFragment());
        mBinding.vpMain.setAdapter(new MainFragmentAdapter(getSupportFragmentManager(),fragments));
        new RxPermissions(this).request(Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE).subscribe(aBoolean -> {
        });
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getBlankComponent(new BlankModule()).inject(this);
    }


}
