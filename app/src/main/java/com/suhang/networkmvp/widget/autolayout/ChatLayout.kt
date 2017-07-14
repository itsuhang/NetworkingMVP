package com.suhang.networkmvp.widget.autolayout

import android.content.Context
import android.util.AttributeSet
import android.view.View

/**
 * Created by sh on 2016/6/28 20:51.
 */

class ChatLayout(context: Context, attrs: AttributeSet) : AutoHeightLayout(context, attrs) {
    var softKeyHeight: Int = 0
        private set
    private var controller: View? = null

    fun setController(controller: View) {
        this.controller = controller
    }


    override fun onSoftKeyboardHeightChanged(height: Int) {
        softKeyHeight = height
    }

    override fun OnSoftPop(height: Int) {
        super.OnSoftPop(height)
        if (onSoftKeyStatuListener != null) {
            onSoftKeyStatuListener!!.onSoftKeyOpen(height)
        }
    }

    override fun OnSoftClose() {
        super.OnSoftClose()
        if (onSoftKeyStatuListener != null) {
            onSoftKeyStatuListener!!.onSoftKeyClose()
        }
    }

    private var onSoftKeyStatuListener: OnSoftKeyStatuListener? = null

    fun setOnSoftKeyHeightListener(onSoftKeyStatuListener: OnSoftKeyStatuListener) {
        this.onSoftKeyStatuListener = onSoftKeyStatuListener
    }

    interface OnSoftKeyStatuListener {
        fun onSoftKeyOpen(height: Int)
        fun onSoftKeyClose()
    }
}
