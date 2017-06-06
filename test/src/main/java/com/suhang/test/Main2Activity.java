package com.suhang.test;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import javax.inject.Inject;

public class Main2Activity extends BaseActivity {
	@Inject
	Context mContext;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main2);
		Log.i("啊啊啊",mContext+ "");
	}
}
