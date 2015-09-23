package com.kickthecanclient.utils;

import android.content.Context;
import android.content.Intent;

import com.kickthecanclient.activities.ErrorActivity;

/**
 * システム情報管理用クラス.
 *
 * @author ebihara
 */
public class ApplicationUtil {

	private static Context instance = null;

	public static void setInstance(Context context) {
		instance = context;
	}

	public static void exceptionHandler(Exception e) {
		e.printStackTrace();
		Intent intent = new Intent(instance, ErrorActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		instance.startActivity(intent);
	}
}
