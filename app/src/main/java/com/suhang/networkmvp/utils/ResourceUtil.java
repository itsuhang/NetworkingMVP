package com.suhang.networkmvp.utils;

import android.support.annotation.ColorRes;
import android.support.annotation.StringRes;


import com.suhang.networkmvp.R;
import com.suhang.networkmvp.application.App;

import java.lang.reflect.Field;

/**
 * Created by sh on 2016/6/30 15:01.
 */

public class ResourceUtil {
	public static String s(@StringRes int id) {
		return App.getInstance().getResources().getString(id);
	}

	public static int c(@ColorRes int id) {
		return App.getInstance().getResources().getColor(id);
	}

	public static int getZBRank(String rank) {
		try {
			Class aClass = R.drawable.class;
			Field field = aClass.getField("rank_"+rank);
			field.setAccessible(true);
			return field.getInt(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}

	public static int getRank(String rank) {
		try {
			Class aClass = R.drawable.class;
			Field field = aClass.getField("zb_level_" + rank);
			field.setAccessible(true);
			return field.getInt(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return 0;
	}
}
