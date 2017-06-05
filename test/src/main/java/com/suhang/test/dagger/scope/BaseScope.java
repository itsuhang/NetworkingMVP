package com.suhang.test.dagger.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by 苏杭 on 2017/6/5 22:57.
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface BaseScope {
}
