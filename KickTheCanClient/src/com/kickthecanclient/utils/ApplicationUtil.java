package com.kickthecanclient.utils;

import android.app.Activity;

/**
 * システム情報管理用クラス.
 *
 * @author ebihara
 */
public class ApplicationUtil {

	private static Activity instance = null;

	public static void setInstance(Activity context) {
		instance = context;
	}

	public static Activity getInstance() {
		return instance;
	}
}
