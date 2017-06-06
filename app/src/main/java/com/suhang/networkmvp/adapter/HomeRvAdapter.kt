package com.suhang.networkmvp.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup

import com.suhang.networkmvp.R
import com.suhang.networkmvp.constants.setAdapterTag
import com.suhang.networkmvp.domain.HistoryBean
import com.suhang.networkmvp.mvp.result.EventResult
import javax.inject.Inject
import kotlinx.android.synthetic.main.item_home.view.*

/**
 * Created by 苏杭 on 2017/1/24 16:51.
 */

class HomeRvAdapter @Inject
constructor(context: Context) : BaseRvAdapter<HomeRvAdapter.MyViewHolder, HistoryBean.ListEntity>(context) {
    override fun onBindHolder(holder: MyViewHolder, v: HistoryBean.ListEntity) {
        holder.itemView.tv.text = v.nick
        holder.itemView.setBackgroundColor(0xffffffff.toInt())
        holder.itemView.setOnClickListener {
            it.setAdapterTag(Message(holder.adapterPosition, v.uid))
            manager.post(EventResult(it))
        }
    }

    data class Message(var position: Int, var luid: String)


    override fun onCreateHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(bind(R.layout.item_home))
    }

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
