package com.suhang.sample.dagger.component;

import com.suhang.layoutfinderannotation.dagger.GenInject;
import com.suhang.sample.MainActivity;

/**
 * Created by 苏杭 on 2017/5/18 17:56.
 */

public interface Provider {
    @GenInject(component = "BaseComponent")
    void inject(MainActivity mainActivity);
}
