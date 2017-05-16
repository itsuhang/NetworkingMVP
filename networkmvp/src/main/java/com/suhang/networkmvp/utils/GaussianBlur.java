package com.suhang.networkmvp.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.renderscript.Allocation;
import android.renderscript.Element;
import android.renderscript.RenderScript;
import android.renderscript.ScriptIntrinsicBlur;
import android.widget.ImageView;

/**
 * Created by sh on 2016/4/29 14:48.
 */
public class GaussianBlur {
	public static void gaosi(Context context,Bitmap bitmap, ImageView iv) {
	}

	public static Bitmap get(Context context, Bitmap bitmap) {
			Bitmap small = small(bitmap);
			Bitmap copy = small.copy(small.getConfig(), true);
			RenderScript rs = RenderScript.create(context);
			Allocation input = Allocation.createFromBitmap(rs, small, Allocation.MipmapControl.MIPMAP_NONE,
					Allocation.USAGE_SCRIPT);
			Allocation output = Allocation.createTyped(rs, input.getType());
			ScriptIntrinsicBlur script = ScriptIntrinsicBlur.create(rs, Element.U8_4(rs));
			script.setRadius(3);
			script.setInput(input);
			script.forEach(output);
			output.copyTo(copy);
			return big(copy);
	}

	private static Bitmap big(Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(4f, 4f); //长和宽放大缩小的比例
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}

	private static Bitmap small(Bitmap bitmap) {
		Matrix matrix = new Matrix();
		matrix.postScale(0.25f, 0.25f); //长和宽放大缩小的比例
		return Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
	}
}
