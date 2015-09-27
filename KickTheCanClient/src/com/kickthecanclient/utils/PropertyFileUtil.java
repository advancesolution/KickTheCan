package com.kickthecanclient.utils;

import java.util.ResourceBundle;

/**
 * プロパティファイル関連の処理クラス.
 *
 * @author ebihara
 */
public class PropertyFileUtil {

	public static String getProperty(String fileName, String itemName) {
		ResourceBundle bundle = null;
		try {
			bundle = ResourceBundle.getBundle(fileName);
		} catch (Exception e) {
			new RuntimeException(e);
		}
		return bundle.getString(itemName);
	}
}
