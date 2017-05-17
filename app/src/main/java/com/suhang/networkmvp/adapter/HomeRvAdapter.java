package com.suhang.networkmvp.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder;
import com.suhang.networkmvp.binding.data.AdapterHomeRvData;
import com.suhang.networkmvp.binding.data.BaseData;
import com.suhang.networkmvp.databinding.ItemHomeBinding;
import com.suhang.networkmvp.domain.HomeBean;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 16:51.
 */

public class HomeRvAdapter extends BaseRvAdapter<HomeRvAdapter.MyViewHolder,HomeBean.ListEntity>  {

    @Inject
    public HomeRvAdapter() {
    }

    @Override
    protected BaseData getBindingData() {
        return new AdapterHomeRvData();
    }


    @Override
    public void onBindHolder(MyViewHolder holder, HomeBean.ListEntity listEntity) {
        holder.mBinding.getData();
        holder.mBinding.tv.setText(listEntity.getNick());
        holder.mBinding.getRoot().setBackgroundColor(0xffffffff);
    }


    @Override
    public MyViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(getContext(),R.layout.item_home,null));
    }

    @Override
    public int getPageSize() {
        return 10;
    }

    public class MyViewHolder extends BaseViewHolder<ItemHomeBinding> {
        public MyViewHolder(View view) {
            super(view);
        }
    }
}
