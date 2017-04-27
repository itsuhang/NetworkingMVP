package com.suhang.networkmvp.adapter;

import android.databinding.ViewDataBinding;
import android.view.ViewGroup;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.Binding;
import com.suhang.networkmvp.databinding.ItemHomeBinding;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 16:51.
 */

public class HomeRvAdapter extends BaseRvAdapter  {
    @Binding(id = R.layout.item_home)
    ItemHomeBinding mBinding;

    @Inject
    public HomeRvAdapter() {
    }


    @Override
    protected MyViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder<>(mBinding);
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
