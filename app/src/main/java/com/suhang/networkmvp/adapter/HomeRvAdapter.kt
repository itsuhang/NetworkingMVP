package com.suhang.networkmvp.adapter

import android.view.View
import android.view.ViewGroup

import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.viewholder.BaseViewHolder
import com.suhang.networkmvp.domain.HomeBean
import javax.inject.Inject
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * Created by 苏杭 on 2017/1/24 16:51.
 */

class HomeRvAdapter @Inject
constructor() : BaseRvAdapter<HomeRvAdapter.MyViewHolder, HomeBean.ListEntity>() {

    override fun onBindHolder(holder: MyViewHolder, listEntity: HomeBean.ListEntity) {
        holder.itemView.tv.text = listEntity.nick
        holder.itemView.setBackgroundColor(0xffffffff.toInt())
    }


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(bind(R.layout.item_home))
    }

    override val pageSize: Int
        get() = 10

    inner class MyViewHolder(view: View) : BaseViewHolder(view)
}
