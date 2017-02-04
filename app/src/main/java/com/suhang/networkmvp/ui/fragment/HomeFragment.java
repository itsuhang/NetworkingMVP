package com.suhang.networkmvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.HomeRvAdapter;
import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.dagger.module.HomeModule;
import com.suhang.networkmvp.databinding.FragmentHomeBinding;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.contract.IHomeContract;
import com.suhang.networkmvp.mvp.presenter.HomePresenter;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@FragmentScope
public class HomeFragment extends BaseFragment<HomePresenter,FragmentHomeBinding> implements IHomeContract.IHomeView{
    @Inject
    HomeRvAdapter mAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return bind(inflater, container, R.layout.fragment_home);
    }

    @Override
    protected void initData() {
        getBinding().rvHome.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false));
        getBinding().rvHome.setAdapter(mAdapter);
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getHomeComponent(new HomeModule(this)).inject(this);
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

    @Override
    public void log() {

    }

    @Override
    public void setData(ErrorBean e, int tag) {

    }

    @Override
    public void progress(int precent, int tag) {

    }
}
