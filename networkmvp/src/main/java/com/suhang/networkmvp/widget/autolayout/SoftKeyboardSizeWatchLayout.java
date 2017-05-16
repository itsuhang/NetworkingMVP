package com.suhang.networkmvp.widget.autolayout;

import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.widget.RelativeLayout;

import com.suhang.networkmvp.utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;

public class SoftKeyboardSizeWatchLayout extends RelativeLayout {

	private Context mContext;
	private int mOldh = -1;
	private int mNowh = -1;
	protected int mScreenHeight = 0;
	protected boolean mIsSoftKeyboardPop = false;

	public SoftKeyboardSizeWatchLayout(final Context context, AttributeSet attrs) {
		super(context, attrs);
		this.mContext = context;
		getViewTreeObserver().addOnGlobalLayoutListener(() -> {
			Rect r = new Rect();
			((Activity) mContext).getWindow().getDecorView().getWindowVisibleDisplayFrame(r);
			if (mScreenHeight == 0) {
				mScreenHeight = r.bottom;
			}
			if (r.bottom > mScreenHeight) {
				mScreenHeight = r.bottom;
			}
			mNowh = mScreenHeight - r.bottom;
			if (mOldh != -1 && mNowh != mOldh) {
				if (mNowh > ScreenUtils.getNavigationBarHeight(context)) {
					mIsSoftKeyboardPop = true;
					if (mListenerList != null) {
						for (OnResizeListener l : mListenerList) {
							l.OnSoftPop(mNowh);
						}
					}
				} else {
					if (mListenerList != null) {
						if (mIsSoftKeyboardPop) {
							for (OnResizeListener l : mListenerList) {
								l.OnSoftClose();
							}
							mIsSoftKeyboardPop = false;
						}
					}
				}
			}
			mOldh = mNowh;
		});
	}

	public boolean isSoftKeyboardPop() {
		return mIsSoftKeyboardPop;
	}

	private List<OnResizeListener> mListenerList;

	public void addOnResizeListener(OnResizeListener l) {
		if (mListenerList == null) {
			mListenerList = new ArrayList<>();
		}
		mListenerList.add(l);
	}

	public interface OnResizeListener {
		/**
		 * 软键盘弹起
		 */
		void OnSoftPop(int height);

		/**
		 * 软键盘关闭
		 */
		void OnSoftClose();
	}
}