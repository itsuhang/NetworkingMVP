package com.suhang.networkmvp.ui.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.github.mzule.activityrouter.annotation.Router;
import com.suhang.networkmvp.R;
import com.suhang.networkmvp.constants.Constants;
import com.suhang.networkmvp.databinding.ActivitySplashBinding;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.base.BlankPresent;
import com.suhang.networkmvp.mvp.base.IBlankView;
import com.suhang.networkmvp.utils.LogUtil;

@Router(Constants.SPLASH)
public class SplashActivity extends BaseActivity<BlankPresent, ActivitySplashBinding> implements IBlankView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bind(R.layout.activity_splash);
    }

    @Override
    protected void initData() {
        getBinding().getData().da.set(getIntent().getStringExtra("name")+"   "+getIntent().getStringExtra("age"));
    }

    @Override
    protected void injectDagger() {
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
