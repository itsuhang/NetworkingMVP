package com.suhang.networkmvp.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.HomeRvAdapter;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.FragmentHomeBinding;
import com.suhang.networkmvp.mvp.translator.HomeTranslator;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@FragmentScope
public class HomeFragment extends BaseFragment<HomeTranslator> {
	@Binding(id = R.layout.fragment_home)
	FragmentHomeBinding mBinding;
	@Inject
	HomeRvAdapter mAdapter;

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
		mBinding.rvHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
		mBinding.rvHome.setAdapter(mAdapter);
	}

	@Override
	protected void injectDagger() {
		getBaseComponent().getBlankComponent(new BlankModule()).inject(this);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		mAdapter.destory();
	}
}
