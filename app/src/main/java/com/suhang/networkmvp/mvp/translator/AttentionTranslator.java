package com.suhang.networkmvp.mvp.translator;

import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.mvp.event.ClickEvent;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/5/2 11:18.
 */
@BaseScope
public class AttentionTranslator extends BaseTranslator {
    @Inject
    public AttentionTranslator() {
    }

    @Override
    public void init() {
        subscribeEvent(ClickEvent.class).subscribe(clickEvent -> {
            mModel.getGithubData();
        });
    }
}
