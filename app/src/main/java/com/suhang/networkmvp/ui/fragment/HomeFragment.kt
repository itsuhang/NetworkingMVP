package com.suhang.networkmvp.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.suhang.networkmvp.R
import com.suhang.networkmvp.adapter.HomeRvAdapter
import com.suhang.networkmvp.annotation.FragmentScope
import com.suhang.networkmvp.constants.subscribeGlobalProgress
import com.suhang.networkmvp.dagger.module.BlankModule
import com.suhang.networkmvp.function.rx.FlowableWrap
import com.suhang.networkmvp.mvp.model.HomeModel
import com.suhang.networkmvp.mvp.result.ProgressResult
import io.reactivex.functions.Consumer
import kotlinx.android.synthetic.main.fragment_home.*
import org.jetbrains.anko.info
import org.jetbrains.anko.warn
import javax.inject.Inject

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@FragmentScope
class HomeFragment : BaseFragment<HomeModel>() {
    @Inject
    lateinit var mAdapter: HomeRvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind(R.layout.fragment_home)
    }

    override fun subscribeEvent() {
        info(disposables)
        manager.subscribeGlobalProgress().subscribe(Consumer {
            warn(it.progress)
        })
//                getManager().subscribeEvent(BindingEvent.class).subscribe(bindingEvent -> {
//                    switch (bindingEvent.getId()) {
//                        case R.id.button:
//                            getModel().getLoadMore(mAdapter.getNextPage());
//                            break;
//                        case R.id.parent:
//                            LogUtil.i("啊啊啊"+ bindingEvent.getData());
//                            break;
//                    }
//                });
//                getManager().subscribeResult(ErrorResult.class).subscribe(errorResult -> {
//                    LogUtil.i("啊啊啊" + errorResult.getResult());
//                });
//                getManager().subscribeResult(SuccessResult.class).subscribe(successResult -> {
//                    if (successResult.getTag() == TAG_LOADMORE) {
//                        HistoryBean result = successResult.getResult(HistoryBean.class);
//                        mAdapter.setTotalCount(Integer.parseInt(result.getTotal()));
//                        mAdapter.loadMore(result.getList());
//                    } else if (successResult.getTag() == TAG) {
//                        HistoryBean result = successResult.getResult(HistoryBean.class);
//                        mAdapter.setTotalCount(Integer.parseInt(result.getTotal()));
//                        mAdapter.notifyDataSetChanged(result.getList());
//                    } else if (successResult.getTag() == TAG_DELETE) {
//                        DeleteHistoryBean result = successResult.getResult(DeleteHistoryBean.class);
//                        if (result.getFailedList() != null && "".equals(result.getFailedList())) {
//                            getModel().getHomeData(mAdapter.getCurrentPage() * mAdapter.getPageSize(), (List<Integer>) result.getAppendMessage());
//                        }
//                    } else if (successResult.getTag() == TAG_LOADMORE_NORMAL) {
//                        HistoryBean result = successResult.getResult(HistoryBean.class);
//                        mAdapter.setTotalCount(Integer.parseInt(result.getTotal()));
//                        mAdapter.notifyDelete((List<Integer>) result.getAppendMessage(), result.getList());
//                        mLs1.clear();
//                        mLs2.clear();
//                    }
//
//                });

    }

    override fun initEvent() {
        button.setOnClickListener {
            model.download()
        }
        button1.setOnClickListener {
            model.cancelDownload()
        }
    }


    override fun initData() {
        rv_home.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
        rv_home.adapter = mAdapter
        model.getHomeData()
    }

    override fun injectDagger() {
        baseComponent.providerBlankComponent(BlankModule()).inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        mAdapter.destory()
    }

    companion object {
        val TAG = 100
        val TAG_LOADMORE = 101
        val TAG_DELETE = 102
        val TAG_LOADMORE_NORMAL = 103
    }
}
