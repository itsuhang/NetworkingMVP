package com.suhang.networkmvp.widget.autolayout

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.graphics.Rect
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout

import com.suhang.networkmvp.R
import com.suhang.networkmvp.utils.KeyboardUtils


/**
 * Created by sh on 2016/6/28 19:35.
 */
abstract class AutoHeightLayout(protected var mContext: Context, attrs: AttributeSet) : SoftKeyboardSizeWatchLayout(mContext, attrs), SoftKeyboardSizeWatchLayout.OnResizeListener {
    protected var mMaxParentHeight: Int = 0
    protected var mSoftKeyboardHeight: Int = 0
    protected var mConfigurationChangedFlag = false

    init {
        mSoftKeyboardHeight = KeyboardUtils.getDefKeyboardHeight(mContext)
        addOnResizeListener(this)
    }

    override fun addView(child: View, index: Int, params: ViewGroup.LayoutParams) {
        val childSum = childCount
        if (childSum > 1) {
            throw IllegalStateException("can host only one direct child")
        }
        super.addView(child, index, params)
        if (childSum == 0) {
            if (child.id < 0) {
                child.id = ID_CHILD
            }
            val paramsChild = child.layoutParams as RelativeLayout.LayoutParams
            paramsChild.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM)
            child.layoutParams = paramsChild
        } else if (childSum == 1) {
            val paramsChild = child.layoutParams as RelativeLayout.LayoutParams
            paramsChild.addRule(RelativeLayout.ABOVE, ID_CHILD)
            child.layoutParams = paramsChild
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        onSoftKeyboardHeightChanged(mSoftKeyboardHeight)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        if (mMaxParentHeight == 0) {
            mMaxParentHeight = h
        }
    }

    fun updateMaxParentHeight(maxParentHeight: Int) {
        this.mMaxParentHeight = maxParentHeight
        if (maxParentHeightChangeListener != null) {
            maxParentHeightChangeListener!!.onMaxParentHeightChange(maxParentHeight)
        }
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        mConfigurationChangedFlag = true
        mScreenHeight = 0
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        if (mConfigurationChangedFlag) {
            mConfigurationChangedFlag = false
            val r = Rect()
            (mContext as Activity).window.decorView.getWindowVisibleDisplayFrame(r)
            if (mScreenHeight == 0) {
                mScreenHeight = r.bottom
            }
            val mNowh = mScreenHeight - r.bottom
            mMaxParentHeight = mNowh
        }

        if (mMaxParentHeight != 0) {
            val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
            val expandSpec = View.MeasureSpec.makeMeasureSpec(mMaxParentHeight, heightMode)
            super.onMeasure(widthMeasureSpec, expandSpec)
            return
        }
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
    }

    override fun OnSoftPop(height: Int) {
        if (mSoftKeyboardHeight != height) {
            mSoftKeyboardHeight = height
            KeyboardUtils.setDefKeyboardHeight(mContext, mSoftKeyboardHeight)
            onSoftKeyboardHeightChanged(mSoftKeyboardHeight)
        }
    }

    override fun OnSoftClose() {}

    abstract fun onSoftKeyboardHeightChanged(height: Int)

    private var maxParentHeightChangeListener: OnMaxParentHeightChangeListener? = null

    interface OnMaxParentHeightChangeListener {
        fun onMaxParentHeightChange(height: Int)
    }

    fun setOnMaxParentHeightChangeListener(listener: OnMaxParentHeightChangeListener) {
        this.maxParentHeightChangeListener = listener
    }

    companion object {

        private val ID_CHILD = R.id.id_autolayout
    }
}