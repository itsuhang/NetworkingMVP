package com.suhang.test;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;

import javax.inject.Inject;

public class MainActivity extends BaseActivity {
	@Inject
	Dog mDog;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Log.i("啊啊啊", mDog + "");
	}
}
