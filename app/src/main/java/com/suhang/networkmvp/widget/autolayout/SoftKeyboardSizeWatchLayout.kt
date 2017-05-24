package com.suhang.networkmvp.widget.autolayout

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.util.AttributeSet
import android.widget.RelativeLayout


import com.suhang.networkmvp.utils.ScreenUtils

import java.util.ArrayList

open class SoftKeyboardSizeWatchLayout(private val mContext: Context, attrs: AttributeSet) : RelativeLayout(mContext, attrs) {
    private var mOldh = -1
    private var mNowh = -1
    protected var mScreenHeight = 0
    var isSoftKeyboardPop = false
        protected set

    init {
        viewTreeObserver.addOnGlobalLayoutListener {
            val r = Rect()
            (mContext as Activity).window.decorView.getWindowVisibleDisplayFrame(r)
            if (mScreenHeight == 0) {
                mScreenHeight = r.bottom
            }
            if (r.bottom > mScreenHeight) {
                mScreenHeight = r.bottom
            }
            mNowh = mScreenHeight - r.bottom
            if (mOldh != -1 && mNowh != mOldh) {
                if (mNowh > ScreenUtils.getNavigationBarHeight(mContext)) {
                    isSoftKeyboardPop = true
                    if (mListenerList != null) {
                        for (l in mListenerList!!) {
                            l.OnSoftPop(mNowh)
                        }
                    }
                } else {
                    if (mListenerList != null) {
                        if (isSoftKeyboardPop) {
                            for (l in mListenerList!!) {
                                l.OnSoftClose()
                            }
                            isSoftKeyboardPop = false
                        }
                    }
                }
            }
            mOldh = mNowh
        }
    }

    private var mListenerList: MutableList<OnResizeListener>? = null

    fun addOnResizeListener(l: OnResizeListener) {
        if (mListenerList == null) {
            mListenerList = ArrayList<OnResizeListener>()
        }
        mListenerList!!.add(l)
    }

    interface OnResizeListener {
        /**
         * 软键盘弹起
         */
        fun OnSoftPop(height: Int)

        /**
         * 软键盘关闭
         */
        fun OnSoftClose()
    }
}