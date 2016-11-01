package com.suhang.networkmvp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.suhang.networkmvp.bean.ErrorBean;
import com.suhang.networkmvp.bean.HotListBean;
import com.suhang.networkmvp.constants.Constant;
import com.suhang.networkmvp.interfaces.INetworkService;
import com.suhang.networkmvp.presenter.INetworkPresenter;
import com.suhang.networkmvp.presenter.NetworkPresenter;
import com.suhang.networkmvp.view.INetworkView;

import java.io.File;

public class MainActivity extends AppCompatActivity implements INetworkView{
	private INetworkPresenter mPresenter;
	private static final int HOT_TAG = 100;
	private static final String TAG = "MainActivity";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		mPresenter = new NetworkPresenter(this);
		mPresenter.getData(HotListBean.class,null,true,HOT_TAG);
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public void showLoading() {

	}

	@Override
	public void hideLoading() {

	}

	@Override
	public void showError(ErrorBean errorBean, int tag) {

	}

	@Override
	public void setData(Object o, int tag) {
		if (o instanceof HotListBean) {
			Log.i(TAG, "setData: "+o.toString());
		}
	}

	@Override
	public void progress(int precent, int tag) {

	}
}
