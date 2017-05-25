package com.suhang.networkmvp.adapter

import android.app.Activity
import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.suhang.networkmvp.constants.ERROR_TAG
import com.suhang.networkmvp.constants.ErrorCode
import com.suhang.networkmvp.constants.errorMessage
import com.suhang.networkmvp.domain.ErrorBean
import com.suhang.networkmvp.function.SubstribeManager
import com.suhang.networkmvp.interfaces.ErrorLogger
import com.suhang.networkmvp.interfaces.IAdapterHelper
import com.suhang.networkmvp.mvp.result.ErrorResult
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import java.util.*
import javax.inject.Inject
/**
 * Created by 苏杭 on 2016/11/9 21:50.
 */

abstract class BaseRvAdapter<T : RecyclerView.ViewHolder, V> : RecyclerView.Adapter<T>(), IAdapterHelper,ErrorLogger,AnkoLogger {
    @Inject
    lateinit var context: Context
    @Inject
    lateinit var activity: Activity

    //Rxjava事件集合，用于退出时取消事件
    @Inject
    lateinit var mDisposables: CompositeDisposable

    var mList: MutableList<V> = ArrayList()

    @Inject
    lateinit var sm: SubstribeManager

    private var mTotalCount = 0

    private var mTotalPage = 0

    fun notifyDataSetChanged(v: List<V>?) {
        mList.clear()
        if (v != null && v.isNotEmpty()) {
            mList.addAll(v)
        }
        notifyDataSetChanged()
    }

    fun bind(layout: Int):View = View.inflate(context, layout, null)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): T {
        return onCreateHolder(parent, viewType)
    }

    abstract fun onBindHolder(holder: T, v: V)

    abstract fun onCreateHolder(parent: ViewGroup, viewType: Int): T

    override fun onBindViewHolder(holder: T, position: Int) {
        try {
            onBindHolder(holder, mList[position])
        } catch (e: Exception) {
            val errorBean = ErrorBean(ErrorCode.ERROR_CODE_RVADAPTER_BIND, ErrorCode.ERROR_DESC_RVADAPTER_BIND + "\n" ,errorMessage(e))
            sm.post(ErrorResult(errorBean, ERROR_TAG))
        }

    }

    /**
     * 加载更多(合并数据)
     */
    fun loadMore(beans: List<V>) {
        if (nextPage > mTotalPage) {
            Toast.makeText(context, "没有更多了", Toast.LENGTH_SHORT).show()
        } else {
            if (mList.size > 0) {
                mList.addAll(beans)
            }
        }
        notifyDataSetChanged()
    }

    /**
     * 单个删除

     * @param position 要删除的位置
     * *
     * @param beans 删除后从网络重新获取的数据集合
     */
    fun notifyDelete(position: Int, beans: List<V>) {
        mList.clear()
        mList.addAll(beans)
        notifyItemRemoved(position)
        notifyItemChanged(beans.size - 1)
    }

    /**
     * 多个删除

     * @param positions 要删除的位置
     * *
     * @param beans 删除后从网络重新获取的数据集合
     */
    fun notifyDelete(positions: List<Int>, beans: List<V>) {
        val start = mList.size - positions.size - 1
        mList.clear()
        mList.addAll(beans)
        Collections.sort(positions) { o1, o2 ->
            if (o1 > o2) {
                return@sort -1
            } else if (o1 == o2) {
                return@sort 0
            } else {
                return@sort 1
            }
        }
        for (position in positions) {
            notifyItemRemoved(position)
        }
        notifyItemRangeChanged(start, positions.size)
    }

    /**
     * 得到每页数据大小
     */
    abstract val pageSize: Int

    override fun setTotalCount(count: Int) {
        try {
            if (mTotalCount % pageSize == 0) {
                mTotalPage = mTotalCount / pageSize
            } else {
                mTotalPage = mTotalCount / pageSize + 1
            }
        } catch (e: Exception) {
        }

        mTotalCount = count
    }

    fun getTotalCount(): Int {
        return mTotalCount
    }

    override fun getCurrentCount(): Int {
        return itemCount
    }


    /**
     * 获取下一页页数
     */
    val nextPage: Int
        get() = currentPage + 1

    /**
     * 获取当前所在页数
     */
    val currentPage: Int
        get() {
            var page: Int
            try {
                if (itemCount % pageSize == 0) {
                    page = itemCount / pageSize
                } else {
                    page = itemCount / pageSize + 1
                }
            } catch (e: Exception) {
                page = 0
            }

            return page
        }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun getMaxCount(): Int {
        return pageSize
    }

    fun destory() {
        mDisposables.dispose()
    }
}
