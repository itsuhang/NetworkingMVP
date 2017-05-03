package com.suhang.networkmvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.AttentionPagerAdapter;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.FragmentAttentionBinding;
import com.suhang.networkmvp.mvp.model.BlankModel;
import com.suhang.networkmvp.ui.pager.AttentionOnePager;
import com.suhang.networkmvp.ui.pager.AttentionTwoPager;
import com.suhang.networkmvp.ui.pager.BasePager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@FragmentScope
public class AttentionFragment extends BaseFragment<BlankModel> {
    @Binding(id = R.layout.fragment_attention)
    FragmentAttentionBinding mBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return getRootView();
    }

    @Override
    protected void subscribeEvent() {

    }

    @Override
    protected void initData() {
        List<BasePager> pagers = new ArrayList<>();
        pagers.add(new AttentionOnePager(getActivity()));
        pagers.add(new AttentionTwoPager(getActivity()));
        mBinding.vpAttention.setAdapter(new AttentionPagerAdapter(pagers));
    }

    @Override
    protected void injectDagger() {
        getBaseComponent().getBlankComponent(new BlankModule()).inject(this);
    }
}