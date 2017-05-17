package com.suhang.networkmvp.utils;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

import com.suhang.networkmvp.R;


/**
 * 对话框辅助类
 * Created by sh on 2016/5/19.
 */
public class DialogHelp {

	/***
	 * 获取一个dialog
	 *
	 * @param context
	 * @return
	 */
	public static AlertDialog.Builder getDialog(Context context) {
		AlertDialog.Builder builder = new AlertDialog.Builder(context);
		return builder;
	}

	/***
	 * 获取一个耗时等待对话框
	 *
	 * @param context
	 * @return
	 */
	public static Dialog getWaitDialog(Context context) {
		View view = View.inflate(context, R.layout.dialog_wating_layout, null);
		final ImageView iv = (ImageView) view.findViewById(R.id.wating);
		Dialog dialog = new Dialog(context, R.style.MyDialog);
		final RotateAnimation ra = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		WindowManager.LayoutParams params = dialog.getWindow().getAttributes();
		params.width = DensityUtil.dip2px(80);
		params.height = DensityUtil.dip2px(80);
		dialog.getWindow().setAttributes(params);
		dialog.setCanceledOnTouchOutside(true);
		dialog.setContentView(view);
		dialog.setOnShowListener(dialog1 -> {
			ra.setInterpolator(new LinearInterpolator());
			ra.setFillAfter(true);
			ra.setRepeatCount(Integer.MAX_VALUE);
			ra.setDuration((long) (500));
			iv.startAnimation(ra);
		});
		dialog.setOnDismissListener(dialog12 -> ra.cancel());
		return dialog;
	}



	/***
	 * 获取一个信息对话框，注意需要自己手动调用show方法显示
	 *
	 * @param context
	 * @param message
	 * @param onClickListener
	 * @return
	 */
	public static AlertDialog.Builder getMessageDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = getDialog(context);
		builder.setMessage(message);
		builder.setPositiveButton("确定", onClickListener);
		return builder;
	}

	public static AlertDialog.Builder getMessageDialog(Context context, String message) {
		return getMessageDialog(context, message, null);
	}

	public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = getDialog(context);
		builder.setMessage(Html.fromHtml(message));
		builder.setPositiveButton("确定", onClickListener);
		builder.setNegativeButton("取消", null);
		return builder;
	}

	public static AlertDialog.Builder getConfirmDialog(Context context, String message, DialogInterface.OnClickListener onOkClickListener, DialogInterface.OnClickListener onCancleClickListener) {
		AlertDialog.Builder builder = getDialog(context);
		builder.setMessage(message);
		builder.setPositiveButton("确定", onOkClickListener);
		builder.setNegativeButton("取消", onCancleClickListener);
		return builder;
	}

	public static AlertDialog.Builder getSelectDialog(Context context, String title, String[] arrays, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = getDialog(context);
		builder.setItems(arrays, onClickListener);
		if (!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		builder.setPositiveButton("取消", null);
		return builder;
	}

	public static AlertDialog.Builder getSelectDialog(Context context, String[] arrays, DialogInterface.OnClickListener onClickListener) {
		return getSelectDialog(context, "", arrays, onClickListener);
	}

	public static AlertDialog.Builder getSingleChoiceDialog(Context context, String title, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
		AlertDialog.Builder builder = getDialog(context);
		builder.setSingleChoiceItems(arrays, selectIndex, onClickListener);
		if (!TextUtils.isEmpty(title)) {
			builder.setTitle(title);
		}
		builder.setNegativeButton("取消", null);
		return builder;
	}

	public static AlertDialog.Builder getSingleChoiceDialog(Context context, String[] arrays, int selectIndex, DialogInterface.OnClickListener onClickListener) {
		return getSingleChoiceDialog(context, "", arrays, selectIndex, onClickListener);
	}
}
