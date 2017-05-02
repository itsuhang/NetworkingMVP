package com.suhang.networkmvp.mvp.translator;

import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.mvp.event.ItemClickEvent;
import com.suhang.networkmvp.mvp.model.HomeModel;
import com.suhang.networkmvp.utils.LogUtil;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/5/2 16:36.
 */
@BaseScope
public class HomeTranslator extends BaseTranslator<HomeModel>{
    @Inject
    public HomeTranslator() {
    }

    @Override
    public void substribe() {
        subscribeEvent(ItemClickEvent.class).subscribe(itemClickEvent -> {
            LogUtil.i("啊啊啊" + itemClickEvent.getPosition() + "被点了");
        });
    }
}
