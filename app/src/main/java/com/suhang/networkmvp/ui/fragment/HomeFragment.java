package com.suhang.networkmvp.ui.fragment;

import android.support.v7.widget.LinearLayoutManager;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.adapter.HomeRvAdapter;
import com.suhang.networkmvp.annotation.FragmentScope;
import com.suhang.networkmvp.dagger.module.BlankModule;
import com.suhang.networkmvp.databinding.FragmentHomeBinding;
import com.suhang.networkmvp.domain.DeleteHistoryBean;
import com.suhang.networkmvp.domain.HomeBean;
import com.suhang.networkmvp.mvp.event.ClickEvent;
import com.suhang.networkmvp.mvp.event.ItemClickEvent;
import com.suhang.networkmvp.mvp.model.HomeModel;
import com.suhang.networkmvp.mvp.result.ErrorResult;
import com.suhang.networkmvp.mvp.result.SuccessResult;
import com.suhang.networkmvp.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/1/24 15:31.
 */
@FragmentScope
public class HomeFragment extends BaseFragment<HomeModel,FragmentHomeBinding> {
    @Inject
    HomeRvAdapter mAdapter;
    public static final int TAG = 100;
    public static final int TAG_LOADMORE = 101;
    public static final int TAG_DELETE = 102;
    public static final int TAG_LOADMORE_NORMAL = 103;
    private List<Integer> mLs1 = new ArrayList<>();
    private List<String> mLs2 = new ArrayList<>();

    @Override
    protected int bindLayout() {
        return R.layout.fragment_home;
    }

    @Override
    protected void subscribeEvent() {
        getSm().subscribeEvent(ItemClickEvent.class).subscribe(itemClickEvent -> {

        });
        getSm().subscribeEvent(ClickEvent.class).subscribe(clickEvent -> {
            switch (clickEvent.getId()) {
                case R.id.button:
                    getModel().getLoadMore(mAdapter.getNextPage());
                    break;
                case R.id.button1:
                    LogUtil.i("啊啊啊"+mLs1+"   "+mLs2);
                    getModel().deleteHistory(mLs2, mLs1);
                    break;
            }
        });
        getSm().subscribeResult(ErrorResult.class).subscribe(errorResult -> {
            LogUtil.i("啊啊啊" + errorResult.getResult());
        });
        getSm().subscribeResult(SuccessResult.class).subscribe(successResult -> {
            if (successResult.getTag() == TAG_LOADMORE) {
                HomeBean result = successResult.getResult(HomeBean.class);
                mAdapter.setTotalCount(Integer.parseInt(result.getTotal()));
                mAdapter.loadMore(result.getList());
            } else if (successResult.getTag() == TAG) {
                HomeBean result = successResult.getResult(HomeBean.class);
                mAdapter.setTotalCount(Integer.parseInt(result.getTotal()));
                mAdapter.notifyDataSetChanged(result.getList());
            } else if (successResult.getTag() == TAG_DELETE) {
                DeleteHistoryBean result = successResult.getResult(DeleteHistoryBean.class);
                if (result.getFailedList() != null && "".equals(result.getFailedList())) {
                    getModel().getHomeData(mAdapter.getCurrentPage() * mAdapter.getPageSize(), (List<Integer>) result.getAppendMessage());
                }
            } else if (successResult.getTag() == TAG_LOADMORE_NORMAL) {
                HomeBean result = successResult.getResult(HomeBean.class);
                mAdapter.setTotalCount(Integer.parseInt(result.getTotal()));
                mAdapter.notifyDelete((List<Integer>) result.getAppendMessage(), result.getList());
                mLs1.clear();
                mLs2.clear();
            }

        });

    }

    @Override
    protected void initData() {
        mBinding.rvHome.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        mBinding.rvHome.setAdapter(mAdapter);
        mModel.getHomeData();
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
