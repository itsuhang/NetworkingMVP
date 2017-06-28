package com.suhang.networkmvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.suhang.layoutfinderannotation.GenSubComponent
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.HomeRvAdapter
import com.suhang.networkmvp.application.DaggerHelper
import com.suhang.networkmvp.constants.*
import com.suhang.networkmvp.dagger.module.ChildModule
import com.suhang.networkmvp.dagger.module.HomeModule
import com.suhang.networkmvp.domain.DeleteHistoryBean
import com.suhang.networkmvp.domain.HistoryBean
import com.suhang.networkmvp.mvp.model.HomeModel
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.warn
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@GenSubComponent(tag = Constant.BASE_CHILD_DAGGER_TAG, modules = arrayOf(HomeModule::class))
class HomeFragment : BaseFragment<HomeModel>() {
    override fun initDagger() {
        DaggerHelper.getInstance().getHomeFragmentComponent(this, activity)
    }

    @Inject
    lateinit var mAdapter: HomeRvAdapter
    val luids: ArrayList<String> = ArrayList()
    val positions: ArrayList<Int> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.fragment_home)
    }

    override fun subscribeEvent() {
        manager.subscribeError().subscribe(Consumer {
            warn(it.result)
        })

        manager.subscribeSuccess().subscribe(Consumer {
            if (it.tag == TAG_LOADMORE) {
                (it.result as HistoryBean).run {
                    mAdapter.totalCount = total.toInt()
                    mAdapter.loadMore(list)
                }
            } else if (it.tag == TAG) {
                (it.result as HistoryBean).run {
                    mAdapter.totalCount = total.toInt()
                    mAdapter.notifyDataSetChanged(list)
                }
            } else if (it.tag == TAG_DELETE) {
                (it.result as DeleteHistoryBean).run {
                    if (failedList != null && "" == failedList) {
                        model.getHomeData(mAdapter.currentPage * mAdapter.pageSize, it.append as List<Int>)
                    }
                }
            } else if (it.tag == TAG_LOADMORE_NORMAL) {
                (it.result as HistoryBean).run {
                    mAdapter.totalCount = total.toInt()
                    mAdapter.notifyDelete(it.append as List<Int>, list)
                }
            }
        })

        manager.subscribeEvent().subscribe(Consumer {
            (it.view).run {
                setBackgroundColor(0xffff00ff.toInt())
            }
            (it.view.getAdapterTag() as HomeRvAdapter.Message).run {
                luids.add(luid)
                positions.add(position)
            }
        })

    }

    override fun initEvent() {
        button.setOnClickListener {
            model.getLoadMore(mAdapter.nextPage)
        }
        button1.setOnClickListener {
            model.deleteHistory(luids, positions)
        }
    }


    override fun initData() {
        rv_home.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_home.adapter = mAdapter
        model.getHomeData()
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.destory()
    }

    companion object {
        val TAG = 101
        val TAG_LOADMORE = 102
        val TAG_DELETE = 103
        val TAG_LOADMORE_NORMAL = 104
    }
}
