package com.kickthecanclient.utils;

import android.app.Dialog;
import android.app.DialogFragment;
import android.app.Fragment;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.WindowManager;

import com.kickthecanclient.dialogFlagments.ErrorDialogFragment;
import com.kickthecanclient.enums.MessageId;

/**
 * ダイアログ関連の処理用クラス.
 *
 * @author ebihara
 */
public class DialogUtil {

	public static void showErrorDialog(MessageId messageId) {
		DialogFragment dialog = new ErrorDialogFragment(messageId);
		dialog.show(ApplicationUtil.getInstance().getFragmentManager(), null);
	}

	public static void setDefaultOptions(Fragment fragment, Dialog dialog) {

		DisplayMetrics metrics = fragment.getResources().getDisplayMetrics();
		int dialogWidth = (int) (metrics.widthPixels * 0.65);
		int dialogHeight = (int) (metrics.heightPixels * 0.8);

		WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
		lp.width = dialogWidth;
		lp.height = dialogHeight;
		lp.gravity=Gravity.BOTTOM;
		lp.y = 50;

		dialog.getWindow().setAttributes(lp);
		dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
	}
}
