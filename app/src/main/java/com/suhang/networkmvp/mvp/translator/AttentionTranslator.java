package com.suhang.networkmvp.mvp.translator;

import com.suhang.networkmvp.R;
import com.suhang.networkmvp.annotation.BaseScope;
import com.suhang.networkmvp.mvp.event.ClickEvent;
import com.suhang.networkmvp.mvp.model.AttentionModel;

import javax.inject.Inject;

/**
 * Created by 苏杭 on 2017/5/2 11:18.
 */
@BaseScope
public class AttentionTranslator extends BaseTranslator<AttentionModel> {
    @Inject
    public AttentionTranslator() {
    }

    @Override
    public void substribe() {
        subscribeEvent(ClickEvent.class).subscribe(clickEvent -> {
            switch (clickEvent.getId()) {
                case R.id.button:
                    mModel.getAppMainData();
                    break;
                case R.id.button1:
                    mModel.getGithubData();
                    break;
            }
        });
    }
}
