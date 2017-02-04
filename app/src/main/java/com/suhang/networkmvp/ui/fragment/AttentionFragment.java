package com.suhang.networkmvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.AttentionPagerAdapter;
import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.dagger.module.AttentionModule;
import com.suhang.networkmvp.databinding.FragmentAttentionBinding;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.base.BlankPresent;
import com.suhang.networkmvp.mvp.base.IBlankView;
import com.suhang.networkmvp.ui.pager.AttentionOnePager;
import com.suhang.networkmvp.ui.pager.AttentionTwoPager;
import com.suhang.networkmvp.ui.pager.BasePager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@FragmentScope
public class AttentionFragment extends BaseFragment<BlankPresent,FragmentAttentionBinding> implements IBlankView {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return bind(inflater, container, R.layout.fragment_attention);
    }

    @Override
    protected void initData() {
        List<BasePager> pagers = new ArrayList<>();
        pagers.add(new AttentionOnePager(getActivity()));
        pagers.add(new AttentionTwoPager(getActivity()));
        getBinding().vpAttention.setAdapter(new AttentionPagerAdapter(pagers));
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getAttentionComponent(new AttentionModule(this)).inject(this);
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
