package com.suhang.networkmvp.mvp.model;

import android.util.ArrayMap;

import com.suhang.networkmvp.domain.DeleteHistoryBean;
import com.suhang.networkmvp.domain.HomeBean;
import com.suhang.networkmvp.ui.fragment.HomeFragment;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/5/2 16:33.
 */

public class HomeModel extends BaseModel {
    @Inject
    NetworkModel mModel;

    @Inject
    public HomeModel() {
    }

    public void getHomeData() {
        ArrayMap<String, String> ls = new ArrayMap<>();
        ls.put("uid", "2240");
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7");
        ls.put("size", "10");
        mModel.loadPostDataWrap(HomeBean.class, false, HomeFragment.TAG, ls);
    }

    public void getHomeData(int count, int position) {
        ArrayMap<String, String> ls = new ArrayMap<>();
        ls.put("uid", "2240");
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7");
        ls.put("size", String.valueOf(count));
        mModel.setAppendMessage(HomeBean.class, position);
        mModel.loadPostDataWrap(HomeBean.class, false, HomeFragment.TAG_LOADMORE_NORMAL, ls);
    }

    public void getHomeData(int count, List<Integer> position) {
        ArrayMap<String, String> ls = new ArrayMap<>();
        ls.put("uid", "2240");
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7");
        ls.put("size", String.valueOf(count));
        mModel.setAppendMessage(HomeBean.class, position);
        mModel.loadPostDataWrap(HomeBean.class, false, HomeFragment.TAG_LOADMORE_NORMAL, ls);
    }

    public void getLoadMore(int page) {
        ArrayMap<String, String> ls = new ArrayMap<>();
        ls.put("uid", "2240");
        ls.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7");
        ls.put("size", "10");
        ls.put("page", String.valueOf(page));
        mModel.loadPostDataWrap(HomeBean.class, false, HomeFragment.TAG_LOADMORE, ls);
    }

    public void deleteHistory(String luid, int position) {
        Map<String, String> params = new ArrayMap<>();
        params.put("uid", "2240");
        params.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7");
        params.put("history", luid);
        mModel.setAppendMessage(DeleteHistoryBean.class, position);
        mModel.loadPostDataWrap(DeleteHistoryBean.class, false, HomeFragment.TAG_DELETE,params);
    }

    public void deleteHistory(List<String> luids, List<Integer> positions) {
        Map<String, String> params = new ArrayMap<>();
        params.put("uid", "2240");
        params.put("encpass", "9db06bcff9248837f86d1a6bcf41c9e7");
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < luids.size(); i++) {
            if (i == 0) {
                sb.append(luids.get(i));
            } else {
                sb.append("," + luids.get(i));
            }
        }
        params.put("history", sb.toString());
        mModel.setAppendMessage(DeleteHistoryBean.class, positions);
        mModel.loadPostDataWrap(DeleteHistoryBean.class, false, HomeFragment.TAG_DELETE,params);
    }
}
