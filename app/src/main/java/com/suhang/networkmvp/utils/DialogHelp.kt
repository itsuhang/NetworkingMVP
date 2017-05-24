package com.suhang.networkmvp.utils

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.text.Html
import android.text.TextUtils
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.LinearInterpolator
import android.view.animation.RotateAnimation
import android.widget.ImageView

import com.suhang.networkmvp.R
import com.suhang.networkmvp.constants.dip2px


/**
 * 对话框辅助类
 * Created by sh on 2016/5/19.
 */
object DialogHelp {

    /***
     * 获取一个dialog

     * @param context
     * *
     * @return
     */
    fun getDialog(context: Context): AlertDialog.Builder {
        val builder = AlertDialog.Builder(context)
        return builder
    }

    /***
     * 获取一个耗时等待对话框

     * @param context
     * *
     * @return
     */
    fun getWaitDialog(context: Context): Dialog {
        val view = View.inflate(context, R.layout.dialog_wating_layout, null)
        val iv = view.findViewById(R.id.wating) as ImageView
        val dialog = Dialog(context, R.style.MyDialog)
        val ra = RotateAnimation(0f, 360f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f)
        val params = dialog.window!!.attributes
        params.width = dip2px(80f)
        params.height = dip2px(80f)
        dialog.window!!.attributes = params
        dialog.setCanceledOnTouchOutside(true)
        dialog.setContentView(view)
        dialog.setOnShowListener { dialog1 ->
            ra.interpolator = LinearInterpolator()
            ra.fillAfter = true
            ra.repeatCount = Integer.MAX_VALUE
            ra.duration = 500.toLong()
            iv.startAnimation(ra)
        }
        dialog.setOnDismissListener { dialog12 -> ra.cancel() }
        return dialog
    }


    /***
     * 获取一个信息对话框，注意需要自己手动调用show方法显示

     * @param context
     * *
     * @param message
     * *
     * @param onClickListener
     * *
     * @return
     */
    @JvmOverloads fun getMessageDialog(context: Context, message: String, onClickListener: DialogInterface.OnClickListener? = null): AlertDialog.Builder {
        val builder = getDialog(context)
        builder.setMessage(message)
        builder.setPositiveButton("确定", onClickListener)
        return builder
    }

    fun getConfirmDialog(context: Context, message: String, onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {
        val builder = getDialog(context)
        builder.setMessage(Html.fromHtml(message))
        builder.setPositiveButton("确定", onClickListener)
        builder.setNegativeButton("取消", null)
        return builder
    }

    fun getConfirmDialog(context: Context, message: String, onOkClickListener: DialogInterface.OnClickListener, onCancleClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {
        val builder = getDialog(context)
        builder.setMessage(message)
        builder.setPositiveButton("确定", onOkClickListener)
        builder.setNegativeButton("取消", onCancleClickListener)
        return builder
    }

    fun getSelectDialog(context: Context, title: String, arrays: Array<String>, onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {
        val builder = getDialog(context)
        builder.setItems(arrays, onClickListener)
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title)
        }
        builder.setPositiveButton("取消", null)
        return builder
    }

    fun getSelectDialog(context: Context, arrays: Array<String>, onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {
        return getSelectDialog(context, "", arrays, onClickListener)
    }

    fun getSingleChoiceDialog(context: Context, title: String, arrays: Array<String>, selectIndex: Int, onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {
        val builder = getDialog(context)
        builder.setSingleChoiceItems(arrays, selectIndex, onClickListener)
        if (!TextUtils.isEmpty(title)) {
            builder.setTitle(title)
        }
        builder.setNegativeButton("取消", null)
        return builder
    }

    fun getSingleChoiceDialog(context: Context, arrays: Array<String>, selectIndex: Int, onClickListener: DialogInterface.OnClickListener): AlertDialog.Builder {
        return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener)
    }
}
