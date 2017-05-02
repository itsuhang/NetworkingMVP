package com.suhang.networkmvp.adapter;

import android.view.View;
import android.view.ViewGroup;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder;
import com.suhang.networkmvp.databinding.ItemHomeBinding;
import com.suhang.networkmvp.domain.ErrorBean;
import com.suhang.networkmvp.mvp.result.ErrorResult;
import com.suhang.networkmvp.mvp.translator.HomeTranslator;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 16:51.
 */

public class HomeRvAdapter extends BaseRvAdapter<HomeRvAdapter.MyViewHolder,HomeTranslator>  {

    @Inject
    public HomeRvAdapter() {
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public void onBindHolder(MyViewHolder holder, int position) {
        if (position == 5) {
            getBm().post(new ErrorResult(new ErrorBean("100","我日哟"),100));
        }
    }

    @Override
    public MyViewHolder onCreateHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(View.inflate(getContext(),R.layout.item_home,null));
    }


    @Override
    public int getItemCount() {
        return 20;
    }

    @Override
    public int setNetItemCount() {
        return 0;
    }

    @Override
    public int setMaxCount() {
        return 0;
    }

    public class MyViewHolder extends BaseViewHolder<ItemHomeBinding> {
        public MyViewHolder(View view) {
            super(view);
        }
    }
}
