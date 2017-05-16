package com.suhang.networkmvp.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by 苏杭 on 2016/11/30 10:09.
 */

public class InputLeakUtil {
    /**
     * 反射将InputMethodManager持有的所有View引用置空，并调用windowDismissed（）方法
     */
    public static void fixInputMethodManager(Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager manager = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        Field field;
        try {
            field = manager.getClass().getDeclaredField("mLastSrvView");
            field.setAccessible(true);
            field.set(manager, null);
        } catch (NoSuchFieldException | IllegalAccessException e) {
        }
//        for (Field field : manager.getClass().getDeclaredFields()) {
//            field.setAccessible(true);
//            if (View.class.equals(field.getGenericType())) {
//                try {
//                    if (field.get(manager) != null && field.get(manager) instanceof EditText) {
//                        LogUtil.i("啊啊啊" + field.getName() + "   " + field.get(manager));
//                        field.set(manager, null);
//                    }
//                } catch (IllegalAccessException e) {
//                    e.printStackTrace();
//                }
//            }
//        }
        final Object imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE);

        final Reflector.TypedObject windowToken
                = new Reflector.TypedObject(activity.getWindow().getDecorView().getWindowToken(), IBinder.class);

        Reflector.invokeMethodExceptionSafe(imm, "windowDismissed", windowToken);

//        final Reflector.TypedObject view
//                = new Reflector.TypedObject(null, View.class);
//
//        Reflector.invokeMethodExceptionSafe(imm, "startGettingWindowFocus", view);
    }
}
