package com.suhang.networkmvp.adapter;

import android.view.ViewGroup;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.databinding.ItemHomeBinding;
import com.suhang.networkmvp.mvp.presenter.HomePresenter;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 16:51.
 */

public class HomeRvAdapter extends BaseRvAdapter<HomePresenter,ItemHomeBinding>  {
    @Inject
    public HomeRvAdapter() {
    }

    @Override
    protected MyViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return bind(R.layout.item_home);
    }

    @Override
    protected void onBindHolder(MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    @Override
    public int setNetItemCount() {
        return 0;
    }

    @Override
    public int setMaxCount() {
        return 0;
    }
}
