package com.suhang.networkmvp.widget.autolayout;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by sh on 2016/6/28 20:51.
 */

public class ChatLayout extends AutoHeightLayout {
	private int softKeyHeight;
	private View controller;
	public ChatLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public void setController(View controller) {
		this.controller = controller;
	}


	@Override
	public void onSoftKeyboardHeightChanged(int height) {
		softKeyHeight = height;
	}

	public int getSoftKeyHeight() {
		return softKeyHeight;
	}

	@Override
	public void OnSoftPop(int height) {
		super.OnSoftPop(height);
		if (onSoftKeyStatuListener != null) {
			onSoftKeyStatuListener.onSoftKeyOpen(height);
		}
	}

	@Override
	public void OnSoftClose() {
		super.OnSoftClose();
		if (onSoftKeyStatuListener != null) {
			onSoftKeyStatuListener.onSoftKeyClose();
		}
	}

	private OnSoftKeyStatuListener onSoftKeyStatuListener;

	public void setOnSoftKeyHeightListener(OnSoftKeyStatuListener onSoftKeyStatuListener) {
		this.onSoftKeyStatuListener = onSoftKeyStatuListener;
	}
	public interface OnSoftKeyStatuListener {
		void onSoftKeyOpen(int height);
		void onSoftKeyClose();
	}
}
